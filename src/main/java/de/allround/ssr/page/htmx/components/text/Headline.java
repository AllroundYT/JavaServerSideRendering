package de.allround.ssr.page.htmx.components.text;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.function.Function;

@RequiredArgsConstructor(staticName = "create")
@Getter
@Setter
@Accessors(fluent = true)
public class Headline extends Component<Headline> {

    private final Function<Data, String> content;

    @Max(6)
    @Min(1)
    private int size = 1;

    @Override
    public RenderFunction preRender() {
        return data -> new Element("h" + size).text(content.apply(data));
    }


    @Contract("_ -> new")
    public static @NotNull Headline create(String content) {
        return new Headline(data -> content);
    }
}
