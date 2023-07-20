package de.allround.ssr;

import de.allround.ssr.annotations.Authentication;
import de.allround.ssr.annotations.Authorization;
import de.allround.ssr.annotations.Method;
import de.allround.ssr.annotations.Route;
import de.allround.ssr.page.WebPage;
import de.allround.ssr.rest.RestAPI;
import de.allround.ssr.util.HtmxSourceUpdater;
import de.allround.ssr.util.HttpMethod;
import de.allround.ssr.util.StaticFileProvider;
import de.allround.ssr.util.Triple;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.authorization.PermissionBasedAuthorization;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@Accessors(fluent = true)
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor(staticName = "build")
@SuppressWarnings("ALL")
public final class WebApplication {
    private final List<WebPage> webPages = new ArrayList<>();
    private final List<RestAPI> restAPIS = new ArrayList<>();
    private final List<Triple<String, HttpMethod, Handler<RoutingContext>>> customHandlers = new ArrayList<>();
    private final Map<Integer, Handler<RoutingContext>> errorHandlers = new HashMap<>();
    private final List<AuthorizationProvider> authorizationProviders = new ArrayList<>();
    private final AtomicReference<AuthenticationHandler> authenticationHandler = new AtomicReference<>(context -> {
        if (context.user() != null) context.next();
        else context.response().setStatusCode(403).setStatusMessage("Not authenticated").end();
    });
    private final AtomicReference<HttpServer> httpServer = new AtomicReference<>();
    private final AtomicReference<Router> router = new AtomicReference<>();
    private final Map<Path, String> staticResources = new HashMap<>();
    private Vertx vertx = Vertx.vertx();
    private SessionStore sessionStore = LocalSessionStore.create(vertx);
    private final List<Handler<RoutingContext>> requiredHandlers = List.of(
            BodyHandler.create(),
            SessionHandler.create(sessionStore),
            HSTSHandler.create(),
            XFrameHandler.create(XFrameHandler.DENY)
    );
    private AtomicBoolean logRequests = new AtomicBoolean(false);
    private int port = 80;

    public WebApplication logRequests(boolean logRequests) {
        this.logRequests.set(logRequests);
        return this;
    }

    public WebApplication addAuthZProvider(AuthorizationProvider... providers) {
        this.authorizationProviders.addAll(List.of(providers));
        return this;
    }

    public WebApplication authNHandler(AuthenticationHandler handler) {
        this.authenticationHandler.set(handler);
        return this;
    }

    public boolean authZEnabled() {
        return !authorizationProviders.isEmpty();
    }


    public void setupStaticResources(Path path, String baseRoute) {
        staticResources.put(path, baseRoute);
    }


    public void setupStaticResources(Path path) {
        staticResources.put(path, null);
    }

    public WebApplication add(WebPage page) {
        webPages.add(page);
        return this;
    }

    public WebApplication add(String route, HttpMethod method, Handler<RoutingContext> handler) {
        this.customHandlers.add(Triple.of(route, method, handler));
        return this;
    }

    public WebApplication add(RestAPI restAPI) {
        this.restAPIS.add(restAPI);
        return this;
    }


    public void launch() {
        HttpServer httpServer;
        if (this.httpServer.get() != null) {
            httpServer = this.httpServer.get();
            httpServer.close();
        }
        httpServer = vertx.createHttpServer();

        this.router.set(Router.router(vertx));
        Router router = this.router.get();

        //Register Custom Handlers (and default Handlers/ error Handlers)

        if (logRequests.get()) {
            router.route("/*").handler(event -> {
                System.out.println("Request -> URI: " + event.request().absoluteURI() + ", Host: " + event.request().remoteAddress().host() + ":" + event.request().remoteAddress().port() + ", Method: " + event.request().method().name() + ", Timestamp: " + System.currentTimeMillis());
                event.next();
            });
        }

        requiredHandlers.forEach(handler -> router.route("/*").handler(handler));


        errorHandlers.forEach(router::errorHandler);
        customHandlers.forEach(triple -> router.route(triple.second().getHttpMethod(), triple.first()).handler(triple.third()));


        //Register REST API

        this.restAPIS.forEach(restAPI -> {
            String parentRoute = "/";
            HttpMethod httpMethod = HttpMethod.GET;

            if (restAPI.getClass().isAnnotationPresent(Route.class)) {
                parentRoute = restAPI.getClass().getAnnotation(Route.class).value();
            }
            StringBuilder route = new StringBuilder(parentRoute);
            for (java.lang.reflect.Method method : restAPI.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Method.class)) {
                    httpMethod = method.getAnnotation(Method.class).value();
                }
                if (method.isAnnotationPresent(Route.class)) {
                    route.append(method.getAnnotation(Route.class).value());
                }

                io.vertx.ext.web.Route routing = router.route(httpMethod.getHttpMethod(), route.toString().replace("//", "/"));
                if (method.isAnnotationPresent(Authentication.class) || restAPI.getClass().isAnnotationPresent(Authentication.class)) {
                    routing.handler(authenticationHandler.get());
                }
                if (method.isAnnotationPresent(Authorization.class) && authZEnabled()) {
                    String permissions = method.getAnnotation(Authorization.class).value();
                    AuthorizationHandler authorizationHandler = AuthorizationHandler.create(PermissionBasedAuthorization.create(permissions));
                    authorizationProviders.forEach(authorizationHandler::addAuthorizationProvider);
                    routing.handler(authorizationHandler);
                } else if (restAPI.getClass().isAnnotationPresent(Authorization.class) && authZEnabled()) {
                    String permissions = restAPI.getClass().getAnnotation(Authorization.class).value();
                    AuthorizationHandler authorizationHandler = AuthorizationHandler.create(PermissionBasedAuthorization.create(permissions));
                    authorizationProviders.forEach(authorizationHandler::addAuthorizationProvider);
                    routing.handler(authorizationHandler);
                }

                routing.handler(context -> {
                    try {
                        restAPI.data().load(context, vertx, null, restAPI, this);
                        method.invoke(restAPI);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });


                route = new StringBuilder(parentRoute);
            }
        });

        //Register Web Pages

        this.webPages.forEach(webPage -> {
            String route = "/";
            HttpMethod method = HttpMethod.GET;
            if (webPage.getClass().isAnnotationPresent(Route.class)) {
                route = webPage.getClass().getAnnotation(Route.class).value();
            }
            if (webPage.getClass().isAnnotationPresent(Method.class)) {
                method = webPage.getClass().getAnnotation(Method.class).value();
            }

            io.vertx.ext.web.Route routing = router.route(method.getHttpMethod(), route);
            if (webPage.getClass().isAnnotationPresent(Authentication.class)) {
                routing.handler(authenticationHandler.get());
            }
            if (webPage.getClass().isAnnotationPresent(Authorization.class) && authZEnabled()) {
                String permissions = webPage.getClass().getAnnotation(Authorization.class).value();
                AuthorizationHandler authorizationHandler = AuthorizationHandler.create(PermissionBasedAuthorization.create(permissions));
                authorizationProviders.forEach(authorizationHandler::addAuthorizationProvider);
                routing.handler(authorizationHandler);
            }
            routing.handler(context -> {
                webPage.data().load(context, vertx, webPage, null, this);
                context.end(webPage.render());
            });
        });

        staticResources.forEach((path, route) -> {
            if (route == null) StaticFileProvider.init(path, HttpMethod.GET, router);
            else StaticFileProvider.init(path, route, HttpMethod.GET, router);
        });

        copyResourceFiles();

        StaticFileProvider.init(Path.of("htmx"), HttpMethod.GET, router);

        httpServer.requestHandler(router);

        httpServer.listen(port);

        CompletableFuture.runAsync(new HtmxSourceUpdater());
    }

    public void copyResourceFiles() {
        //TODO: copy files for htmx and extensions from resource folder into htmx folder
    }
}