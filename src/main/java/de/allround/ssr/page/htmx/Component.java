package de.allround.ssr.page.htmx;

import de.allround.ssr.page.css.Style;
import de.allround.ssr.page.css.StyleLibrary;
import de.allround.ssr.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Accessors(fluent = true)
@Setter
public abstract class Component<T extends Component<?>> {
    private final List<Style> styles = new ArrayList<>();
    private final List<Pair<String, String>> attributes = new ArrayList<>();
    private final List<String> classes = new ArrayList<>();
    private String id = "ID" + UUID.randomUUID().toString().replace("-", ""), hxPost, hxGet, hxConfirm, hxDelete;

    public abstract @NotNull Element rawRender();

    @SuppressWarnings("unchecked")
    public T style(Style style) {
        this.styles.add(style);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T loadStyle(@NotNull StyleLibrary styleLibrary) {
        this.styles.add(styleLibrary.getStyle(this));
        return (T) this;
    }

    private final StringBuilder hxTarget = new StringBuilder(),
            hxBoost = new StringBuilder(),
            hxOn = new StringBuilder(),
            hxPushUrl = new StringBuilder(),
            hxSelect = new StringBuilder(),
            hxSelectOOB = new StringBuilder(),
            hxSwap = new StringBuilder(),
            hxSwapOOB = new StringBuilder(),
            hxTrigger = new StringBuilder(),
            hxVals = new StringBuilder();

    @SuppressWarnings("unchecked")
    public T hxTrigger(String target) {
        hxTrigger.append(hxTrigger.length() > 0 ? ", " : "").append(target);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T hxBoost(String target) {
        hxBoost.append(hxBoost.length() > 0 ? ", " : "").append(target);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T hxOn(String target) {
        hxOn.append(hxOn.length() > 0 ? ", " : "").append(target);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T hxPushUrl(String target) {
        hxPushUrl.append(hxPushUrl.length() > 0 ? ", " : "").append(target);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T hxSelect(String target) {
        hxSelect.append(hxSelect.length() > 0 ? ", " : "").append(target);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T hxSelectOOB(String target) {
        hxSelectOOB.append(hxSelectOOB.length() > 0 ? ", " : "").append(target);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T hxSwap(String target) {
        hxSwap.append(hxSwap.length() > 0 ? ", " : "").append(target);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T hxSwapOOB(String target) {
        hxSwapOOB.append(hxSwapOOB.length() > 0 ? ", " : "").append(target);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T hxVals(String target) {
        hxVals.append(hxVals.length() > 0 ? ", " : "").append(target);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T hxTarget(String target) {
        hxTrigger.append(hxTrigger.length() > 0 ? ", " : "").append(target);
        return (T) this;
    }


    public T styles(Style style, Style... styles) {
        this.styles.add(style);
        this.styles.addAll(List.of(styles));
        return (T) this;
    }

    //Add Websocket Listener/Writer

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final T addAttributes(Pair<String, String>... pairs) {
        this.attributes.addAll(List.of(pairs));
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T id(@NotNull String id) {
        this.id = "ID" + id.replace("-", "");
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
