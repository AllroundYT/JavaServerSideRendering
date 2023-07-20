package de.allround.ssr.page.htmx.components.input;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jsoup.nodes.Element;

@Getter
@Setter
@Accessors(fluent = true)
@RequiredArgsConstructor(staticName = "create")
public class Button extends Component<Button> {
    @Override
    public RenderFunction preRender() {
        return data -> new Element("button");
    }
}
