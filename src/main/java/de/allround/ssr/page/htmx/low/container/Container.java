package de.allround.ssr.page.htmx.low.container;

import de.allround.ssr.page.WebPage;
import de.allround.ssr.page.css.Stylesheet;
import de.allround.ssr.page.htmx.Component;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.List;

@RequiredArgsConstructor
public abstract class Container<T extends Container<?, ?>, I extends Component<?>> extends Component<T> {

    protected final List<I> components;
    private final String type;

    @Override
    public @NotNull Element rawRender() {
        Element element = new Element(type);
        element.appendChildren(components.stream().map(component -> component.fullRender()).toList());
        return element;
    }

    @Override
    public T page(WebPage webPage) {
        components.forEach(i -> i.page(webPage));
        return super.page(webPage);
    }

    @Override
    public T vertx(Vertx vertx) {
        components.forEach(i -> i.vertx(vertx));
        return super.vertx(vertx);
    }

    @Override
    public T context(@NotNull RoutingContext context) {
        components.forEach(i -> i.context(context));
        return super.context(context);
    }

    @Override
    public Stylesheet stylesheet() {
        Stylesheet stylesheet = super.stylesheet();
        components.forEach(i -> stylesheet.add(i.stylesheet().styles()));
        return stylesheet;
    }
}
