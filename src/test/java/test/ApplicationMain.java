package test;

import de.allround.ssr.WebApplication;
import lombok.Getter;
import lombok.experimental.Accessors;
import test.pages.TodoPage;
import test.rest.TodoApi;

import java.util.logging.Logger;

@Getter
@Accessors(fluent = true)
public class ApplicationMain {
    private final Logger logger = Logger.getLogger("WebApplication");
    private final WebApplication webApplication = WebApplication.build();
    private boolean running;

    public static void main(String[] args) {
        ApplicationMain main = new ApplicationMain();
        main.start();
        Runtime.getRuntime().addShutdownHook(new Thread(main::stop));
    }

    public void start() {
        if (running) return;
        running = true;
        webApplication.add(new TodoApi()).add(new TodoPage());
        webApplication.port(8080);
        webApplication.launch();
    }

    public void stop() {
        running = false;
    }
}