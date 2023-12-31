package de.allround.ssr.page.htmx.components.navigation;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import de.allround.ssr.util.Pair;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.util.function.Function;

@RequiredArgsConstructor(staticName = "create")
public class Hyperlink extends Component<Hyperlink> {

    private final Function<Data, Pair<URI, String>> content;

    @Override
    public RenderFunction preRender() {
        return data -> {
            Pair<URI, String> contentPair = content.apply(data);
            return new Element("a").attr("href", contentPair.first().toString()).text(contentPair.second());
        };
    }
}
