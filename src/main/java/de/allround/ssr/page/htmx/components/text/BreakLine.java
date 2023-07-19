package de.allround.ssr.page.htmx.components.text;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;

@NoArgsConstructor(staticName = "create")
public class BreakLine extends Component<BreakLine> {
    @Override
    public RenderFunction preRender() {
        return data -> new Element("br");
    }
}
