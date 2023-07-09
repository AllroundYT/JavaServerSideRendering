package de.allround.ssr;

import de.allround.ssr.annotations.Method;
import de.allround.ssr.annotations.Route;
import de.allround.ssr.page.WebPage;
import de.allround.ssr.page.htmx.staticcomponents.low.Paragraph;
import de.allround.ssr.page.htmx.staticcomponents.low.UnorderedList;
import de.allround.ssr.rest.RestAPI;
import de.allround.ssr.util.HttpMethod;

import java.util.Stack;

public class RestTest {
    public static void main(String[] args) {
        WebApplication application = WebApplication.build();

        application.add(new TestRest());
        application.add(new TestPage());

        application.port(8080);
        application.logRequests(true);
        application.launch();
    }

    @Route("/api")
    static class TestRest extends RestAPI {
        private final Stack<String> messages = new Stack<>();

        @Route("/message/newest")
        @Method(HttpMethod.GET)
        public void getNewestMessage() {
            if (messages.isEmpty()) {
                response.send("No message yet!");
                return;
            }
            String message = messages.peek();
            response.send(message != null ? message : "No message Yet!");
        }

        @Route("/message/all")
        @Method(HttpMethod.GET)
        public void allMessages() {
            response.send(new UnorderedList().addStrings(messages).fullRender().outerHtml());
        }

        @Route("/message/add")
        @Method(HttpMethod.POST)
        public void addMessage() {
            String message = request.formAttributes().get("msg-input");
            messages.add(message);
            System.out.println("Added: " + message);
            response.end(message);
        }
    }

    @Route("/page")
    static class TestPage extends WebPage {

        @Override
        public void init() {
            add(Paragraph.builder().content("Latest Message:").build());
        }
    }
}
