package de.allround.ssr.page.htmx.components.container;

import de.allround.ssr.page.htmx.Container;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import de.allround.ssr.util.HttpMethod;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.nio.charset.Charset;
import java.util.function.Function;

@SuppressWarnings("ALL")
public class Form extends Container<Form> {

    private final Function<Data, String> action;
    private final HttpMethod method;

    protected Form(Function<Data, String> action, HttpMethod method) {
        super("form");
        this.action = action;
        this.method = method;
    }

    public static Form create(Function<Data, String> action, HttpMethod method) {
        return new Form(action, method);
    }

    public static Form create(String action, HttpMethod method) {
        return new Form(data -> action, method);
    }

    public Form acceptCharset(@NotNull Charset charset) {
        addAttribute("accept-charset", charset.name());
        return this;
    }

    @Override
    public RenderFunction preRender() {
        return data -> {
            Element element = super.preRender().render(data);
            element.attr("action", action.apply(data)).attr("method", method.name().toLowerCase());
            return element;
        };
    }
}
