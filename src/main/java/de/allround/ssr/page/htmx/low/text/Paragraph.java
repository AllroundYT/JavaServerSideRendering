package de.allround.ssr.page.htmx.low.text;

import de.allround.ssr.page.htmx.Component;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@RequiredArgsConstructor(staticName = "create")
@Getter
@Accessors(fluent = true)
@Setter
public class Paragraph extends Component<Paragraph> {

    private final String content;

    @Override
    public @NotNull Element rawRender() {
        Element element = new Element("p");
        element.text(content);
        return element;
    }

}
