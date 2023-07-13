package de.allround.ssr.rest;

import de.allround.ssr.page.htmx.Component;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Setter
@Accessors(fluent = true)

/**
 * Simple interface which has to be imported by REST apis just to clarify them as such.
 */
public abstract class RestAPI {

    protected RoutingContext context;
    @Setter(AccessLevel.PRIVATE)
    protected User user;
    @Setter(AccessLevel.PRIVATE)
    protected Session session;
    @Setter(AccessLevel.PRIVATE)
    protected HttpServerResponse response;
    @Setter(AccessLevel.PRIVATE)
    protected HttpServerRequest request;
    @Setter(AccessLevel.PRIVATE)
    protected RequestBody requestBody;
    @Setter(AccessLevel.PRIVATE)
    protected Route currentRoute;
    protected Vertx vertx;

    protected void sendResponse(Component<?> component) {
        if (response == null || component == null) return;
        response.send(component.fullRender().outerHtml());
    }

    protected void sendResponse(Object component) {
        if (response == null || component == null) return;
        response.send(Json.encode(component));
    }

    public RestAPI context(@NotNull RoutingContext context) {
        this.context = context;
        this.user = context.user();
        this.session = context.session();
        this.response = context.response();
        this.request = context.request();
        this.requestBody = context.body();
        this.currentRoute = context.currentRoute();
        return this;
    }
}