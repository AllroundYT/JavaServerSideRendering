package de.allround.ssr.page.htmx.components.tables;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "of")
public class TableHead extends Component<TableHead> {

    private final Function<Data, String> content;

    @Override
    public RenderFunction preRender() {
        return data -> new Element("th").text(content.apply(data));
    }

    @Contract("_ -> new")
    public static @NotNull TableHead of(String content) {
        return new TableHead(data -> content);
    }
}
