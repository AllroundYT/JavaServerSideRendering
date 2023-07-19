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
public class UnderlinedText extends Component<UnderlinedText> {

    private final Function<Data, String> content;

    @Override
    public RenderFunction preRender() {
        return data -> new Element("u").text(content.apply(data));
    }

    @Contract("_ -> new")
    public static @NotNull UnderlinedText create(String content) {
        return new UnderlinedText(data -> content);
    }
}
