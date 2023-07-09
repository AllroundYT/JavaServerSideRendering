package de.allround.ssr.page.htmx.staticcomponents;

import de.allround.ssr.page.css.Stylesheet;
import de.allround.ssr.page.htmx.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@Builder
@Getter
@Accessors(fluent = true)
@Setter
@AllArgsConstructor
public class Paragraph extends Component<Paragraph> {

    private final String content;

    @Override
    public @NotNull Element rawRender() {
        Element element = new Element("p");
        element.text(content);
        return element;
    }

    @Override
    public Stylesheet renderStyles() {
        return null;
    }
}
