package de.allround.ssr.page.htmx.low.custom;

import de.allround.ssr.page.htmx.Component;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@RequiredArgsConstructor(staticName = "create")
public class Script extends Component<Script> {
    private final String javaScript;

    @Override
    public @NotNull Element rawRender() {
        return new Element("script").text(javaScript);
    }
}
