package de.allround.ssr.page.htmx.components.meta;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "create")
public class Meta extends Component<Meta> {

    private final String name;
    private final Function<Data, String> content;

    @Contract("_, _ -> new")
    public static @NotNull Meta create(String name, String content) {
        return new Meta(name, data -> content);
    }

    @Override
    public RenderFunction preRender() {
        return data -> new Element("meta").attr("name", name).attr("content", content.apply(data));
    }
}
