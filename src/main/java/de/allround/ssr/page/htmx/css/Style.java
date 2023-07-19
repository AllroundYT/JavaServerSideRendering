package de.allround.ssr.page.htmx.css;

import de.allround.ssr.page.htmx.util.CSSSize;
import de.allround.ssr.page.htmx.util.DisplayType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@Accessors(fluent = true)
@SuppressWarnings("all")
@RequiredArgsConstructor(staticName = "selector")
public class Style {
    private final String selector;
    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, String> values = new HashMap<>();

    public Style custom(String key, String value) {
        values.put(key, value);
        return this;
    }

    public Style display(@NotNull DisplayType displayType) {
        values.put("display", displayType.value());
        return this;
    }

    public Style variable(String key, Object value) {
        variables.put(key, value);
        return this;
    }

    public Object variable(String key) {
        return variables.getOrDefault(key, null);
    }

    public String compile() {
        StringBuilder outputBuilder = new StringBuilder();

        outputBuilder.append(selector != null ? selector : "*").append(" {").append("\n");
        values.forEach((key, value) -> outputBuilder.append(key).append(":").append(value).append(";\n"));
        outputBuilder.append("}");

        return replaceVariables(outputBuilder.toString());
    }

    public String replaceVariables(String string) {
        AtomicReference<String> atomicReference = new AtomicReference<>(string);
        variables.forEach((key, value) -> atomicReference.set(atomicReference.get().replace(key, value.toString())));
        return atomicReference.get();
    }

    // Hilfsmethode, um die Farbwerte als hexadezimalen String zu formatieren
    @Contract("_ -> new")
    private @NotNull String toHexString(@NotNull Color color) {
        return Integer.toHexString(color.getRGB());
    }

    public String clamp(CSSSize min, CSSSize ideal, CSSSize max) {
        return "clamp(" + min + ", " + ideal + ", " + max + ")";
    }


}
