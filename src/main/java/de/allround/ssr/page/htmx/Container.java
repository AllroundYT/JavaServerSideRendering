package de.allround.ssr.page.htmx;

import de.allround.ssr.page.htmx.css.Style;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@SuppressWarnings("ALL")
@Accessors(fluent = true)
public abstract class Container<T extends Component<?>> extends Component<T> {
    protected final String type;
    protected final List<Component<?>> components = new ArrayList<>();

    public T add(Component<?>... components) {
        this.components.addAll(List.of(components));
        return (T) this;
    }

    public T add(List<? extends Component<?>> components) {
        this.components.addAll(components);
        return (T) this;
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
            components.forEach(component -> element.appendChild(component.render(data)));
            return element;
        };
    }
}
