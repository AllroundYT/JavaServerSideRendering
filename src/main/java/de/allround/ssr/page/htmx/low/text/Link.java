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
public class Link extends Component<Link> {

    private final String uri, text;

    @Override
    public @NotNull Element rawRender() {
        return new Element("a").text(text).attr("href", uri);
    }
}
