package de.allround.ssr;

import de.allround.ssr.annotations.Authentication;
import de.allround.ssr.annotations.Authorization;
import de.allround.ssr.annotations.Method;
import de.allround.ssr.annotations.Route;
import de.allround.ssr.injection.InjectionUtil;
import de.allround.ssr.page.WebPage;
import de.allround.ssr.rest.RestAPI;
import de.allround.ssr.util.HttpMethod;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@Accessors(fluent = true)
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor(staticName = "build")
public final class WebApplication {
    private final List<WebPage> webPages = new ArrayList<>();
    private final List<RestAPI> restAPIS = new ArrayList<>();
    private final List<Triple<String, HttpMethod, Handler<RoutingContext>>> customHandlers = new ArrayList<>();
    private final Map<Integer, Handler<RoutingContext>> errorHandlers = new HashMap<>();
    private final List<AuthorizationProvider> authorizationProviders = new ArrayList<>();
    private final AtomicReference<AuthenticationHandler> authenticationHandler = new AtomicReference<>();
    private final AtomicReference<HttpServer> httpServer = new AtomicReference<>();
    private final AtomicReference<Router> router = new AtomicReference<>();
    private final InjectionUtil injectionUtil = new InjectionUtil();
    private Vertx vertx = Vertx.vertx();
    private SessionStore sessionStore = LocalSessionStore.create(vertx);
    private final List<Handler<RoutingContext>> defaultHandlers = List.of(
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

    public WebApplication addAuthZProvider(List<AuthorizationProvider> providers) {
        this.authorizationProviders.addAll(providers);
        return this;
    }

    public WebApplication authNHandler(AuthenticationHandler handler) {
        this.authenticationHandler.set(handler);
        return this;
    }

    public boolean authZEnabled() {
        return authorizationProviders.size() > 0;
    }

    public boolean authNEnabled() {
        return authenticationHandler.get() != null;
    }


    public WebApplication add(WebPage page) {
        webPages.add(page);
        return this;
    }

    public WebApplication add(String route, HttpMethod method, Handler<RoutingContext> handler) {
        this.customHandlers.add(new Triple<>(route, method, handler));
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

        defaultHandlers.forEach(handler -> router.route("/*").handler(handler));
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
            for (java.lang.reflect.Method method : restAPI.getClass().getMethods()) {

                if (method.isAnnotationPresent(Method.class)) {
                    httpMethod = method.getAnnotation(Method.class).value();
                }
                if (method.isAnnotationPresent(Route.class)) {
                    route.append(method.getAnnotation(Route.class).value());
                }

                io.vertx.ext.web.Route routing = router.route(httpMethod.getHttpMethod(), route.toString().replace("//", "/"));
                if (method.isAnnotationPresent(Authentication.class) && authNEnabled() || restAPI.getClass().isAnnotationPresent(Authentication.class) && authNEnabled()) {
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
                    injectionUtil.inject(restAPI, injectionUtil.contextToInjectionObjectList(context), vertx);
                    try {
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
            if (webPage.getClass().isAnnotationPresent(Authentication.class) && authNEnabled()) {
                routing.handler(authenticationHandler.get());
            }
            if (webPage.getClass().isAnnotationPresent(Authorization.class) && authZEnabled()) {
                String permissions = webPage.getClass().getAnnotation(Authorization.class).value();
                AuthorizationHandler authorizationHandler = AuthorizationHandler.create(PermissionBasedAuthorization.create(permissions));
                authorizationProviders.forEach(authorizationHandler::addAuthorizationProvider);
                routing.handler(authorizationHandler);
            }
            routing.handler(context -> {
                injectionUtil.inject(webPage, injectionUtil.contextToInjectionObjectList(context), vertx);
                webPage.build();
            });
        });

        httpServer.requestHandler(router);

        httpServer.listen(port);
    }
}