package de.allround.ssr.rest;

import de.allround.ssr.annotations.Injected;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.*;

/**
 * Simple interface which has to be imported by REST apis just to clarify them as such.
 */
public abstract class RestAPI {
    @Injected
    protected HttpServerRequest request;

    @Injected
    protected HttpServerResponse response;
    @Injected
    protected RoutingContext context;
    @Injected
    protected User user;
    @Injected
    protected Route route;
    @Injected
    protected Session session;
    @Injected
    protected RequestBody body;
    @Injected
    protected ParsedHeaderValues parsedHeaderValues;
    @Injected
    protected Vertx vertx;
}