package test;

import de.allround.ssr.WebApplication;
import lombok.Getter;
import lombok.experimental.Accessors;
import test.pages.ConsolePage;
import test.rest.ConsoleApi;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Getter
@Accessors(fluent = true)
public class ApplicationMain {
    private final Logger logger = Logger.getLogger("WebApplication");
    private final ConsoleApi consoleApi = new ConsoleApi(this);
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
        webApplication.add(consoleApi.initLoggerHandler()).add(new ConsolePage());
        webApplication.port(8080);
        webApplication.launch();

        CompletableFuture.runAsync(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    logger.info("[Logger] " + UUID.randomUUID());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void stop() {
        running = false;
    }
}