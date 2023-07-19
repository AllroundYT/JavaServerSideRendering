package de.allround.ssr.page.htmx.components.meta;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;

@RequiredArgsConstructor(staticName = "create")
public class Link extends Component<Link> {

    private final String rel;
    private final String href;

    @Override
    public RenderFunction preRender() {
        return data -> new Element("link").attr("rel", rel).attr("href", href);
    }
}
