package de.allround.ssr.page.htmx.components.media;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;

@RequiredArgsConstructor(staticName = "create")
public class Area extends Component<Area> {

    private final String shape;
    private final String coords;
    private final String alt;
    private final String href;

    @Override
    public RenderFunction preRender() {
        return data -> new Element("area").attr("shape", shape).attr("coords", coords).attr("alt", alt).attr("href", href);
    }
}
