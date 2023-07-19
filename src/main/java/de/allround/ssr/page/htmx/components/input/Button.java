package de.allround.ssr.page.htmx.components.input;

import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.function.Function;

@Getter
@Setter
@Accessors(fluent = true)
public class Button extends InputComponent<Button> {
    protected Button(Function<Data, String> content) {
        super("button");
        this.content = content;
    }

    private final Function<Data, String> content;

    @Contract("_ -> new")
    public static @NotNull Button create(String content) {
        return new Button(data -> content);
    }

    @Contract("_ -> new")
    public static @NotNull Button create(Function<Data, String> content) {
        return new Button(content);
    }

    @Override
    public RenderFunction preRender() {
        return data -> {
            Element element = super.preRender().render(data);
            element.text(content.apply(data));
            return element;
        };
    }
}
