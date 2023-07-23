package de.allround.ssr.page.htmx.components.tables;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "create")
public class Caption extends Component<Caption> {

    private final Function<Data, String> content;

    @Contract("_ -> new")
    public static @NotNull Caption of(String content) {
        return new Caption(data -> content);
    }

    @Override
    public RenderFunction preRender() {
        return data -> new Element("caption").text(content.apply(data));
    }
}
