package de.allround.ssr.page.htmx.low.container;

import de.allround.ssr.page.htmx.Component;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@RequiredArgsConstructor(staticName = "of")
public class ListItem extends Component<ListItem> {
    private final String content;

    @Override
    public @NotNull Element rawRender() {
        return new Element("li").text(content);
    }

}
