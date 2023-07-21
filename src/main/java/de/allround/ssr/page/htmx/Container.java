package de.allround.ssr.page.htmx;

import de.allround.ssr.page.htmx.css.Style;
import de.allround.ssr.util.Data;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;


@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@SuppressWarnings("ALL")
@Accessors(fluent = true)
public abstract class Container<T extends Component<?>> extends Component<T> {
    protected final String type;
    protected final List<Component<?>> components = new ArrayList<>();
    protected Function<Data, List<Component<?>>> initFunction;

    public T init(Function<Data, List<Component<?>>> function) {
        this.initFunction = function;
        return (T) this;
    }

    public T add(Component<?>... components) {
        this.components.addAll(List.of(components));
        return (T) this;
    }

    public T add(List<? extends Component<?>> components) {
        this.components.addAll(components);
        return (T) this;
    }

    @Override
    public Function<Data, String> script() {
        return data -> {
            String script = super.script().apply(data);
            StringBuilder builder = new StringBuilder().append(script);
            components.forEach(component -> builder.append("\n").append(component.script()));
            return builder.toString();
        };
    }

    @Override
    public StyleRenderFunction styles() {
        return data -> {
            Set<Style> styles = new HashSet<>();
            components.stream().map(component -> component.styles().renderStyles(data)).forEach(styleSet -> styles.addAll(styleSet));
            return styles;
        };
    }

    @Override
    public RenderFunction preRender() {
        return data -> {
            Element element = new Element(type);
            if (initFunction != null) components.addAll(initFunction.apply(data));
            components.forEach(component -> element.appendChild(component.render(data)));
            return element;
        };
    }
}
