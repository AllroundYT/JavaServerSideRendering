package de.allround.ssr;

import de.allround.ssr.annotations.Method;
import de.allround.ssr.annotations.Route;
import de.allround.ssr.page.WebPage;
import de.allround.ssr.page.htmx.dynamiccomponents.ServerButton;
import de.allround.ssr.page.htmx.dynamiccomponents.TextInput;
import de.allround.ssr.page.htmx.staticcomponents.Div;
import de.allround.ssr.page.htmx.staticcomponents.LineBreak;
import de.allround.ssr.page.htmx.staticcomponents.Paragraph;
import de.allround.ssr.page.htmx.staticcomponents.UnorderedList;
import de.allround.ssr.rest.RestAPI;
import de.allround.ssr.util.HttpMethod;
import de.allround.ssr.util.Pair;

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
            add(de.allround.ssr.page.htmx.dynamiccomponents.Paragraph.builder().content("Loading...").updateRequestURI("/api/message/newest").updateDelay(5 * 1000).build().id("newest-msg"));
            add(Paragraph.builder().content("Sende eine Nachricht:").build());
            add(TextInput.builder().placeholder("Tippe deine Nachricht hier...").build().id("msg-input").addAttributes(new Pair<>("name", "msg-input")));
            add(ServerButton.builder().requestUri("/api/message/add").httpMethod(HttpMethod.POST).text("Absenden").resultDisplaySelector("#newest-msg").build().addAttributes(new Pair<>("hx-include", "#msg-input")));
            add(ServerButton.builder().requestUri("/api/message/all").httpMethod(HttpMethod.GET).resultDisplaySelector("#all-msg").text("Alle Nachrichten anzeigen").build());
            add(LineBreak.use());
            add(Div.builder().build().id("all-msg"));
        }
    }
}
