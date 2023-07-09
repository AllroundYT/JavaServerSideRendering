package de.allround.ssr.page.htmx.staticcomponents;

import de.allround.ssr.page.css.Stylesheet;
import de.allround.ssr.page.htmx.Component;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Accessors(fluent = true)
public class Div extends Component<Div> {
    private final List<Element> elements = new ArrayList<>();

    @Override
    public @NotNull Element rawRender() {
        Element element = new Element("div");
        element.appendChildren(elements);
        return element;
    }

    @Override
    public Stylesheet renderStyles() {
        return null;
    }
}
