package de.allround.ssr.util;

import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;


public class StaticFileProvider implements Handler<RoutingContext> {

    private final Path localFilePath;

    private StaticFileProvider(Path localFilePath) {
        this.localFilePath = localFilePath;
    }

    public static void init(Path filePath, String route, HttpMethod method, Router router) {
        Path workingDir = Path.of("");
        if (Files.isDirectory(filePath)) {
            try (Stream<Path> pathStream = Files.list(filePath)) {
                pathStream.forEach(path -> {
                    StaticFileProvider provider = new StaticFileProvider(path);
                    router.route(method.getHttpMethod(), route + "/" + workingDir.relativize(path).toString().replace("\\", "/")).handler(provider);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (Files.isRegularFile(filePath)) {
            StaticFileProvider provider = new StaticFileProvider(filePath);
            router.route(method.getHttpMethod(), route + "/" + workingDir.relativize(filePath).toString().replace("\\", "/")).handler(provider);
        }
    }

    public static void init(Path filePath, HttpMethod method, Router router) {
        Path workingDir = Path.of("");
        if (Files.isDirectory(filePath)) {
            try (Stream<Path> pathStream = Files.list(filePath)) {
                pathStream.forEach(path -> {
                    StaticFileProvider provider = new StaticFileProvider(path);
                    router.route(method.getHttpMethod(), "/" + workingDir.relativize(path).toString().replace("\\", "/")).handler(provider);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (Files.isRegularFile(filePath)) {
            StaticFileProvider provider = new StaticFileProvider(filePath);
            router.route(method.getHttpMethod(), "/" + workingDir.relativize(filePath).toString().replace("\\", "/")).handler(provider);
        }
    }


    @Override
    public void handle(@NotNull RoutingContext context) {
        context.response().sendFile(String.valueOf(localFilePath));
    }


}
