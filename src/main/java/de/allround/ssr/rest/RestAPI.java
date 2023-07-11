package de.allround.ssr.rest;

import de.allround.ssr.WebApplication;
import de.allround.ssr.annotations.Injected;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.*;
import lombok.AccessLevel;
import lombok.Setter;

/**
 * Simple interface which has to be imported by REST apis just to clarify them as such.
 */
public abstract class RestAPI {
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected WebApplication webApplication;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected HttpServerRequest request;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected HttpServerResponse response;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected RoutingContext context;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected User user;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected Route route;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected Session session;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected RequestBody body;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected ParsedHeaderValues parsedHeaderValues;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected Vertx vertx;
}