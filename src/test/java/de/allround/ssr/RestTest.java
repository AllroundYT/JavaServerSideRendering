package de.allround.ssr;

import de.allround.ssr.annotations.Injected;
import de.allround.ssr.annotations.Method;
import de.allround.ssr.annotations.Route;
import de.allround.ssr.rest.RestAPI;
import de.allround.ssr.util.HttpMethod;
import io.vertx.core.http.HttpServerResponse;

public class RestTest {
    public static void main(String[] args) {
        WebApplication application = WebApplication.build();

        application.add(new TestRest());
        application.port(8080);
        application.logRequests(true);
        application.launch();
    }

    static class TestRest implements RestAPI {


        @Injected
        HttpServerResponse response;

        @Route("/test")
        @Method(HttpMethod.GET)
        public void test() {
            response.end("<h1>Test</h1>");
        }
    }
}
