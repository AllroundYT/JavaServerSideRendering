package de.allround.ssr.page.htmx;

import de.allround.ssr.page.css.Stylesheet;
import de.allround.ssr.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public abstract class Component<T extends Component<?>> {
    public abstract @NotNull Element rawRender();

    public abstract Stylesheet renderStyles();

    private final List<Pair<String, String>> attributes = new ArrayList<>();

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final T addAttributes(Pair<String, String>... pairs) {
        this.attributes.addAll(List.of(pairs));
        return (T) this;
    }

    private final List<String> classes = new ArrayList<>();
    private String id;

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
