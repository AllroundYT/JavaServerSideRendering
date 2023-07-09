package de.allround.ssr.page.htmx;

import de.allround.ssr.page.css.Style;
import de.allround.ssr.page.css.Stylesheet;
import de.allround.ssr.util.Pair;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

@Getter
@Accessors(fluent = true)
public abstract class Component<T extends Component<?>> {
    private final List<Style> styles = new ArrayList<>();
    private final List<Pair<String, String>> attributes = new ArrayList<>();
    private final List<String> classes = new ArrayList<>();
    private String id;

    public abstract @NotNull Element rawRender();

    public abstract Stylesheet renderStyles();

    public T styles(Style style, Style... styles) {
        this.styles.add(style);
        this.styles.addAll(List.of(styles));
        return (T) this;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final T addAttributes(Pair<String, String>... pairs) {
        this.attributes.addAll(List.of(pairs));
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T id(String id) {
        this.id = id;
        return (T) this;
    }

    public Element fullRender() {
        Element element = rawRender();
        classes.forEach(element::addClass);
        if (id != null) element.id(id);
        attributes.forEach(stringStringPair -> element.attr(stringStringPair.first(), stringStringPair.second()));
        return element;
    }

    public List<String> classes() {
        return classes;
    }

    public String id() {
        return id;
    }

    @SuppressWarnings("unchecked")
    public T clazz(String clazz) {
        this.classes.add(clazz);
        return (T) this;
    }
}
