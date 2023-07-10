package de.allround.ssr;

import de.allround.ssr.annotations.Method;
import de.allround.ssr.annotations.Route;
import de.allround.ssr.page.WebPage;
import de.allround.ssr.page.css.StyleLibrary;
import de.allround.ssr.page.htmx.dynamiccomponents.low.ServerButton;
import de.allround.ssr.page.htmx.staticcomponents.low.UnorderedList;
import de.allround.ssr.rest.RestAPI;
import de.allround.ssr.util.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

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

        private final List<Consumer<String>> messageUpdateConsumers = new ArrayList<>();

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

        @Route("/message/newest/updater")
        @Method(HttpMethod.GET)
        public void getNewestMethodUpdateSSE() {
            Consumer<String> updateConsumer = msg -> {
                response.write("""
                        event: newmessage
                        data: %MSG%
                        """.replace("%MSG%", msg));
            };
            messageUpdateConsumers.add(updateConsumer);
            response.closeHandler(unused -> messageUpdateConsumers.remove(updateConsumer));
        }

        @Route("/message/all")
        @Method(HttpMethod.GET)
        public void getAllMessages() {
            response.send(new UnorderedList().addStrings(messages).fullRender().outerHtml());
        }

        @Route("/message/add")
        @Method(HttpMethod.POST)
        public void postAddMessage() {
            String message = request.formAttributes().get("msg-input");
            messages.add(message);
            System.out.println("Added: " + message);
            response.end(message);
        }
    }

    @Route("/")
    static class TestPage extends WebPage {

        @Override
        public void init() {
            add(ServerButton.create("Test Button", "/api/message/add", "#msg-result").loadStyle(StyleLibrary.DefaultGreen));
        }
    }
}
