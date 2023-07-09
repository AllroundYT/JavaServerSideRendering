package de.allround.ssr.page.css;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class Stylesheet {
    private final List<Style> styles = new ArrayList<>();

    public Stylesheet add(Style... styles) {
        this.styles.addAll(List.of(styles));
        for (Style style : styles) {
            System.out.println("TEST");
            System.out.println(style.compile());
        }
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
