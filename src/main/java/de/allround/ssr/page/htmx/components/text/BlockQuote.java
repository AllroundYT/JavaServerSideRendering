package de.allround.ssr.page.htmx.components.text;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import de.allround.ssr.util.Pair;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.function.Function;

@RequiredArgsConstructor(staticName = "create")
public class BlockQuote extends Component<BlockQuote> {
    private final Function<Data, Pair<URL, String>> content;

    @Override
    public RenderFunction preRender() {
        return data -> {
            Pair<URL, String> pair = content.apply(data);
            return new Element("blockquote").attr("cite", pair.first().toString()).text(pair.second());
        };
    }
}
