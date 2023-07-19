package de.allround.ssr.rest;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.util.Data;
import io.vertx.core.json.Json;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true)

/**
 * Simple interface which has to be imported by REST apis just to clarify them as such.
 */
public abstract class RestAPI {

    protected final Data data = new Data();

    protected void sendResponse(Component<?> oldComponent) {
        if (data.response() == null || oldComponent == null) return;
        data.response().send(oldComponent.render(data).outerHtml());
    }

    protected void sendResponse(Object component) {
        if (data.response() == null || component == null) return;
        data.response().send(Json.encode(component));
    }
}