package de.allround.ssr.sse;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SSEModel {
    private final String event, data, id;
    private final Integer retry;

    @Override
    public String toString() {
        List<String> sseStrings = new ArrayList<>();
        if (event != null) sseStrings.add("event: " + event);
        sseStrings.add("data: " + data);
        if (id != null) sseStrings.add("id: " + id);
        if (retry != null) sseStrings.add("retry: " + retry);
        sseStrings.add("\n");
        return String.join("\n", sseStrings);
    }
}
