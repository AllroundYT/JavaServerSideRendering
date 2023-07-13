package de.allround.ssr.page.htmx.low.text;

import de.allround.ssr.page.htmx.Component;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@RequiredArgsConstructor(staticName = "create")
public class Label extends Component<Label> {

    private final String forId, text;

    @Override
    public @NotNull Element rawRender() {
        return new Element("label").attr("for", forId).text(text);
    }
}
