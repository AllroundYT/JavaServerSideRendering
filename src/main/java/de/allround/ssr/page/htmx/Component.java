package de.allround.ssr.page.htmx;


import de.allround.ssr.page.htmx.util.ScrollDestination;
import de.allround.ssr.util.Data;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@SuppressWarnings("ALL")
@Getter
@Accessors(fluent = true)
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Component<T extends Component<?>> {

    private final Map<String, Set<String>> attributes = new HashMap<>();
    private final Map<String, Set<Function<Data, Object>>> attributeFunctions = new HashMap<>();
    private final Set<String> classes = new HashSet<>();
    protected String content;
    private Function<Data, String> id = generateId();

    public T clazz(String... classes) {
        this.classes.addAll(Set.of(classes));
        return (T) this;
    }

    public T id(String id) {
        this.id = data -> id;
        return (T) this;
    }

    public T id(Function<Data, String> id) {
        this.id = id;
        return (T) this;
    }

    @Contract(pure = true)
    private @NotNull Function<Data, String> generateId() {
        Character[] chars = new Character[]{
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };
        List<Character> characterList = new ArrayList<>(List.of(chars));
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < ThreadLocalRandom.current().nextInt(16, 32); i++) {
            Collections.shuffle(characterList);
            stringBuilder.append(characterList.get(i));
        }

        return data -> stringBuilder.toString();
    }

    public T addAttribute(String key, Object value) {
        attributes.compute(key, (k, values) -> {
            if (values == null) values = new HashSet<>();
            values.add(value.toString());
            return values;
        });
        return (T) this;
    }

    public T addAttribute(String key, Object... value) {
        attributes.compute(key, (k, values) -> {
            if (values == null) values = new HashSet<>();
            values.addAll(List.of(value).stream().map(Object::toString).toList());
            return values;
        });
        return (T) this;
    }

    public T addAttribute(String key, Function<Data, Object> value) {
        attributeFunctions.compute(key, (k, values) -> {
            if (values == null) values = new HashSet<>();
            values.add(value);
            return values;
        });
        return (T) this;
    }

    public T addAttribute(String key) {
        addAttribute(key, "");
        return (T) this;
    }

    public T addAttribute(String key, Function<Data, Object>... value) {
        attributeFunctions.compute(key, (k, values) -> {
            if (values == null) values = new HashSet<>();
            values.addAll(List.of(value));
            return values;
        });
        return (T) this;
    }

    public Function<Data, String> script() {
        return data -> "";
    }

    public T content(String content) {
        this.content = content;
        return (T) this;
    }


    public abstract RenderFunction preRender();

    public StyleRenderFunction styles() {
        return data -> Set.of();
    }

    public Element render(Data data) {
        Element element = preRender().render(data);
        classes.forEach(element::addClass);
        element.id(id.apply(data));
        if (content != null) element.text(content);
        attributes.forEach((key, values) -> values.forEach(value -> element.attr(key, value)));
        attributeFunctions.forEach((key, functions) -> functions.forEach(dataObjectFunction -> element.attr(key, dataObjectFunction.apply(data).toString())));
        return element;
    }


    // METHODS FOR HTMX
    public T boost(boolean boost) {
        addAttribute("hx-boost", boost);
        return (T) this;
    }

    public T trigger(String value) {
        addAttribute("hx-trigger", value);
        return (T) this;
    }

    public T target(String value) {
        addAttribute("hx-target", value);
        return (T) this;
    }

    public T get(URI value) {
        addAttribute("hx-get", value);
        return (T) this;
    }

    public T post(URI value) {
        addAttribute("hx-post", value);
        return (T) this;
    }

    public T delete(URI value) {
        addAttribute("hx-trigger", value);
        return (T) this;
    }

    public T put(String value) {
        addAttribute("hx-put", value);
        return (T) this;
    }

    public T swap(String value) {
        addAttribute("hx-swap", value);
        return (T) this;
    }


    public T indicator(String value) {
        addAttribute("hx-indicator", value);
        return (T) this;
    }

    public T sync(String value) {
        addAttribute("hx-sync", value);
        return (T) this;
    }

    public T swapOOB(String value) {
        addAttribute("hx-swap-oob", value);
        return (T) this;
    }

    public T select(String value) {
        addAttribute("hx-select", value);
        return (T) this;
    }

    public T selectOOB(String value) {
        addAttribute("hx-select-oob", value);
        return (T) this;
    }

    public T preserve(String value) {
        addAttribute("hx-preserve", value);
        return (T) this;
    }

    public T include(String value) {
        addAttribute("hx-include", value);
        return (T) this;
    }

    public T params(String value) {
        addAttribute("hx-params", value);
        return (T) this;
    }

    public T encoding(String value) {
        addAttribute("hx-encoding", value);
        return (T) this;
    }

    public T vals(String value) {
        addAttribute("hx-vals", value);
        return (T) this;
    }

    public T vars(String value) {
        addAttribute("hx-vars", value);
        return (T) this;
    }

    public T confirm(String value) {
        addAttribute("hx-confirm", value);
        return (T) this;
    }

    public T confirmUnset() {
        addAttribute("hx-confirm", "unset");
        return (T) this;
    }

    public T pushURL(boolean value) {
        addAttribute("hx-push-url", value);
        return (T) this;
    }

    public T historySnapshotElement() {
        addAttribute("hx-history-elt");
        return (T) this;
    }

    public T history(boolean value) {
        addAttribute("hx-history", value);
        return (T) this;
    }

    public T promt(String value) {
        addAttribute("hx-promt", value);
        return (T) this;
    }

    public T on(String value) {
        addAttribute("hx-on", value);
        return (T) this;
    }

    public T htmxDisabled() {
        addAttribute("hx-disabled");
        return (T) this;
    }

    public T disinherit() {
        addAttribute("hx-disinherit");
        return (T) this;
    }

    public T extension(String value) {
        addAttribute("hx-ext", value);
        return (T) this;
    }

    public T headers(String value) {
        addAttribute("hx-headers", value);
        return (T) this;
    }

    public T patch(URI value) {
        addAttribute("hx-patch", value);
        return (T) this;
    }

    public T replaceUrl(URI value) {
        addAttribute("hx-replace-url", value);
        return (T) this;
    }

    public T request(String value) {
        addAttribute("hx-request", value);
        return (T) this;
    }

    public T validate(String value) {
        addAttribute("hx-validate", value);
        return (T) this;
    }

    //<SSR UTILS EXTENSION>
    public T tabTarget(String value) {
        extension("ssr-utils");
        addAttribute("ssr-tab-target", value);
        return (T) this;
    }

    public T scroll(@NotNull ScrollDestination destination) {
        extension("ssr-utils");
        addAttribute("ssr-scroll", destination.name());
        return (T) this;
    }

    public T toggleClasses(String classes) {
        extension("ssr-utils");
        addAttribute("ssr-toggle-class", classes);
        return (T) this;
    }

    //</SSR UTILS EXTENSION>
    //<REMOVE ME EXTENSION>
    public T removeMe(String timing) {
        extension("remove-me");
        addAttribute("remove-me", timing);
        return (T) this;
    }
    //</REMOVE ME EXTENSION>
}