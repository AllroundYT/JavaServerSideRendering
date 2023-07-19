package test;

import de.allround.ssr.WebApplication;
import lombok.Getter;
import lombok.experimental.Accessors;
import test.pages.TestPage;

import java.util.logging.Logger;

@Getter
@Accessors(fluent = true)
public class ApplicationMain {
    private final Logger logger = Logger.getLogger("WebApplication");
    private boolean running;

    public static void main(String[] args) {
        ApplicationMain main = new ApplicationMain();
        main.start();
        Runtime.getRuntime().addShutdownHook(new Thread(main::stop));
    }

    private final WebApplication webApplication = WebApplication.build();

    public void start() {
        if (running) return;
        running = true;
        webApplication.add(new TestPage());
        webApplication.port(8080);
        webApplication.launch();
    }

    public void stop() {
        running = false;
    }
}