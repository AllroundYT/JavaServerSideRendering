package de.allround.ssr.page.htmx.components.text;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "create")
public class BoldText extends Component<BoldText> {

    private final Function<Data, String> content;

    @Contract("_ -> new")
    public static @NotNull BoldText create(String content) {
        return new BoldText(data -> content);
    }

    @Override
    public RenderFunction preRender() {
        return data -> new Element("b").text(content.apply(data));
    }
}
