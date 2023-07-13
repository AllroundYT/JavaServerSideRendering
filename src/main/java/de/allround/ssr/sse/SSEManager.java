package de.allround.ssr.sse;

import io.vertx.core.http.HttpServerResponse;

import java.util.ArrayList;
import java.util.List;

public class SSEManager {

    private final List<HttpServerResponse> responses = new ArrayList<>();

    public void addListener(HttpServerResponse response) {
        responses.add(response);
        response.setChunked(true).setStatusCode(200).headers().add("Content-Type", "text/event-stream").add("Connection", "keep-alive").add("Cache-Control", "no-cache");
        response.closeHandler(unused -> responses.remove(response));
    }

    public void send(SSEModel sseModel) {
        responses.forEach(response -> {
            response.write(sseModel.toString());
        });
    }
}
