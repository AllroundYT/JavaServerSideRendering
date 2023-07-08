package de.allround.ssr.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HttpMethod {
    OPTIONS(io.vertx.core.http.HttpMethod.OPTIONS),
    GET(io.vertx.core.http.HttpMethod.GET),
    HEAD(io.vertx.core.http.HttpMethod.HEAD),
    POST(io.vertx.core.http.HttpMethod.POST),
    PUT(io.vertx.core.http.HttpMethod.PUT),
    DELETE(io.vertx.core.http.HttpMethod.DELETE),
    TRACE(io.vertx.core.http.HttpMethod.TRACE),
    CONNECT(io.vertx.core.http.HttpMethod.CONNECT),
    PATCH(io.vertx.core.http.HttpMethod.PATCH),
    PROPFIND(io.vertx.core.http.HttpMethod.PROPFIND),
    PROPPATCH(io.vertx.core.http.HttpMethod.PROPPATCH),
    MKCOL(io.vertx.core.http.HttpMethod.MKCOL),
    COPY(io.vertx.core.http.HttpMethod.COPY),
    MOVE(io.vertx.core.http.HttpMethod.MOVE),
    LOCK(io.vertx.core.http.HttpMethod.LOCK),
    UNLOCK(io.vertx.core.http.HttpMethod.UNLOCK),
    MKCALENDAR(io.vertx.core.http.HttpMethod.MKCALENDAR),
    VERSION_CONTROL(io.vertx.core.http.HttpMethod.VERSION_CONTROL),
    REPORT(io.vertx.core.http.HttpMethod.REPORT),
    CHECKOUT(io.vertx.core.http.HttpMethod.CHECKOUT),
    CHECKIN(io.vertx.core.http.HttpMethod.CHECKIN),
    UNCHECKOUT(io.vertx.core.http.HttpMethod.UNCHECKOUT),
    MKWORKSPACE(io.vertx.core.http.HttpMethod.MKWORKSPACE),
    UPDATE(io.vertx.core.http.HttpMethod.UPDATE),
    LABEL(io.vertx.core.http.HttpMethod.LABEL),
    MERGE(io.vertx.core.http.HttpMethod.MERGE),
    BASELINE_CONTROL(io.vertx.core.http.HttpMethod.BASELINE_CONTROL),
    MKACTIVITY(io.vertx.core.http.HttpMethod.MKACTIVITY),
    ORDERPATCH(io.vertx.core.http.HttpMethod.ORDERPATCH),
    ACL(io.vertx.core.http.HttpMethod.ACL),
    SEARCH(io.vertx.core.http.HttpMethod.SEARCH);
    private final io.vertx.core.http.HttpMethod httpMethod;
}
