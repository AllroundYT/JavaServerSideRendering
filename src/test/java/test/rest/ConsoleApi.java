package test.rest;

import de.allround.ssr.annotations.Method;
import de.allround.ssr.annotations.Route;
import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.low.list.ListItem;
import de.allround.ssr.page.htmx.low.list.UnorderedList;
import de.allround.ssr.rest.RestAPI;
import de.allround.ssr.sse.SSEManager;
import de.allround.ssr.sse.SSEModel;
import de.allround.ssr.util.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import test.ApplicationMain;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.CompletableFuture;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

@RequiredArgsConstructor
@Route("/api/console")
public class ConsoleApi extends RestAPI {
    private final ApplicationMain applicationMain;
    private final List<String> consoleLines = new Stack<>();
    private final SSEManager sseManager = new SSEManager();

    @Route("/update")
    @Method(HttpMethod.GET)
    public void getConsoleUpdate() {
        sseManager.addListener(response);
    }

    public void newConsoleLine(String line) {
        System.out.println("NEW LINE: " + line);
        consoleLines.add(line);
        SSEModel sseModel = new SSEModel("new_line", ListItem.of(line).clazz("console-line-list-item").fullRender().outerHtml(), null, null);
        sseManager.send(sseModel);
    }

    public ConsoleApi initLoggerHandler() {
        applicationMain.logger().addHandler(new ConsoleLoggerHandler(this));
        CompletableFuture.runAsync(() -> {
            Scanner scanner = new Scanner(System.in);
            while (applicationMain.running()) {
                if (scanner.hasNextLine()) {
                    newConsoleLine(scanner.nextLine());
                }
            }
        });
        return this;
    }

    @Route("/lines/all")
    @Method(HttpMethod.GET)
    public void getAllConsoleLines() {
        UnorderedList ul = UnorderedList.create().clazz("console-line-list").scroll(Component.ScrollDestination.BOTTOM);
        ul.addItems(consoleLines.stream().map(s -> ListItem.of(s).clazz("console-line-list-item")).toList());
        response.headers().add("Content-Type", "text/html");
        response.setStatusCode(200).send(ul.fullRender().outerHtml());
    }

    @Route("/lines/add")
    @Method(HttpMethod.POST)
    public void postAddLine() {
        request.formAttributes().forEach((s, s2) -> System.out.println("ATTR: " + s + " - " + s2));
        String line = request.getFormAttribute("input");
        applicationMain.logger().info("[WebConsole] " + line);
        response.setStatusCode(200).send();
    }

    @RequiredArgsConstructor
    public static class ConsoleLoggerHandler extends ConsoleHandler {

        private final ConsoleApi consoleApi;

        @Override
        public void publish(@NotNull LogRecord record) {
            consoleApi.newConsoleLine(record.getMessage());
            super.publish(record);
        }

        @Override
        public void flush() {
            super.flush();
        }

        @Override
        public void close() throws SecurityException {
            super.close();
        }
    }
}
