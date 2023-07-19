package de.allround.ssr.page.htmx.components.media;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "create")
public class FigureCaption extends Component<FigureCaption> {

    private final Function<Data, String> content;

    @Override
    public RenderFunction preRender() {
        return data -> new Element("figcaption").text(content.apply(data));
    }
}
