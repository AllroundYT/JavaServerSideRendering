package de.allround.ssr.annotations;

import de.allround.ssr.injection.InjectionUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the Framework to inject a specific object at this place.
 * <br />
 * The following objects can be injected:
 * <ul>
 *     <li>{@link io.vertx.ext.web.RoutingContext}</li>
 *     <li>{@link io.vertx.ext.auth.User}</li>
 *     <li>{@link io.vertx.ext.web.Route}</li>
 *     <li>{@link io.vertx.ext.web.Session}</li>
 *     <li>{@link io.vertx.core.http.HttpServerResponse}</li>
 *     <li>{@link io.vertx.core.http.HttpServerRequest}</li>
 *     <li>{@link io.vertx.ext.web.RequestBody}</li>
 *     <li>{@link io.vertx.ext.web.ParsedHeaderValues}</li>
 * </ul>
 * <p>
 * In some cases there are additional objects which could be injected. <br />
 * Injection is internally performed by {@link InjectionUtil}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Injected {
}
