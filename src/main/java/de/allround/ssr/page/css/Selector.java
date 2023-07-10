package de.allround.ssr.page.css;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(fluent = true)
public class Selector {

    private final List<String> clazzes = new ArrayList<>();
    private final List<String> tags = new ArrayList<>();
    private String id;

    public Selector clazz(String clazz) {
        this.clazzes.add(clazz);
        return this;
    }

    public Selector tag(String tag) {
        this.tags.add(tag);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (id != null) stringBuilder.append("#").append(id).append(" ");
        if (clazzes.size() > 0) clazzes.forEach(s -> stringBuilder.append(".").append(s).append(" "));
        if (tags.size() > 0) tags.forEach(s -> stringBuilder.append(s).append(" "));
        if (stringBuilder.isEmpty()) stringBuilder.append("* ");
        return stringBuilder.toString();
    }
}
