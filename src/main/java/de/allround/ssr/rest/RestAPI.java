package de.allround.ssr.rest;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.util.Data;
import io.vertx.core.json.Json;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
@Accessors(fluent = true)

public abstract class RestAPI {

    protected final Data data = new Data();

    protected void sendResponse(Component<?> component) {
        if (data.response() == null || component == null) return;
        data.response().putHeader("Content-Type", "text/html");
        data.response().setStatusCode(200);
        data.response().send(component.render(data).outerHtml());
    }

    protected void sendResponse(Component<?> @NotNull ... components) {
        StringBuilder responseBodyBuilder = new StringBuilder();
        for (Component<?> component : components) {
            responseBodyBuilder.append(component.render(data).outerHtml());
        }
        if (responseBodyBuilder.isEmpty()) {
            data.response().setStatusCode(204);
            data.response().send();
            return;
        }
        data.response().setStatusCode(200);
        data.response().putHeader("Content-Type", "text/html");
        data.response().send(responseBodyBuilder.toString());
    }


    protected void sendResponse(Object component) {
        if (component == null) {
            data.response().setStatusCode(204);
            data.response().send();
            return;
        }
        data.response().setStatusCode(200);
        data.response().send(Json.encode(component));
    }

    public void redirect(String url) {
        if (data.request().headers().contains("HX-Request")) {
            data.response().putHeader("HX-Redirect", url);
            data.response().send();
        } else data.context().redirect(url);
    }
}