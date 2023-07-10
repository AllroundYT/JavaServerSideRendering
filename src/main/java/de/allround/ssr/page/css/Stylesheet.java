package de.allround.ssr.page.css;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class Stylesheet {
    private final List<Style> styles = new ArrayList<>();

    public List<Style> styles() {
        return styles;
    }

    public Stylesheet add(Style... styles) {
        this.styles.addAll(List.of(styles));
        return this;
    }

    public Stylesheet add(List<Style> styles) {
        this.styles.addAll(styles);
        return this;
    }

    public Element toStyleTag() {
        Element element = new Element("style");
        styles.forEach(style -> {
            element.appendText(style.compile()).appendText(" ");
        });
        element.text(element.text().trim());
        return element;
    }
}
