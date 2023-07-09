package de.allround.ssr.page.htmx.dynamiccomponents.low;

import de.allround.ssr.page.css.Stylesheet;
import de.allround.ssr.page.htmx.Component;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@Builder
@Getter
@Accessors(fluent = true)
public class Paragraph extends Component<Paragraph> {
    private final String content;
    private final long updateDelay;
    private final String updateRequestURI;

    @Override
    public @NotNull Element rawRender() {
        Element element = new Element("p");
        element.text(content).attr("hx-swap", "innerHtml").attr("hx-trigger", "every " + updateDelay + "ms").attr("hx-get", updateRequestURI);
        return element;
    }

    @Override
    public Stylesheet renderStyles() {
        return null;
    }
}
