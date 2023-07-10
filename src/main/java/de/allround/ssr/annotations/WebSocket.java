package de.allround.ssr.annotations;

import de.allround.ssr.util.HttpMethod;

public @interface WebSocket {
    String route();

    HttpMethod method();
}
