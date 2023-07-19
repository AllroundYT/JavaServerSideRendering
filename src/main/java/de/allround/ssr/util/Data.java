package de.allround.ssr.util;

import de.allround.ssr.WebApplication;
import de.allround.ssr.page.WebPage;
import de.allround.ssr.rest.RestAPI;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class Data {
    private RoutingContext context;
    private User user;
    private Session session;
    private HttpServerResponse response;
    private HttpServerRequest request;
    private RequestBody requestBody;
    private Route currentRoute;
    private Vertx vertx;
    private RestAPI restAPI;
    private WebPage webPage;
    private WebApplication webApplication;

    @Override
    public String toString() {
        return "Data{" +
               "context=" + context +
               ", user=" + user +
               ", session=" + session +
               ", response=" + response +
               ", request=" + request +
               ", requestBody=" + requestBody +
               ", currentRoute=" + currentRoute +
               ", vertx=" + vertx +
               ", restAPI=" + restAPI +
               ", webPage=" + webPage +
               '}';
    }

    public void load(RoutingContext context, Vertx vertx, WebPage webPage, RestAPI restAPI, WebApplication webApplication) {
        this.context = context;
        if (context != null) user = context.user();
        if (context != null) session = context.session();
        if (context != null) response = context.response();
        if (context != null) request = context.request();
        if (context != null) requestBody = context.body();
        if (context != null) currentRoute = context.currentRoute();
        this.webApplication = webApplication;
        this.vertx = vertx;
        this.webPage = webPage;
        this.restAPI = restAPI;
    }
}
