package de.allround.ssr.page.htmx.low.text;

import de.allround.ssr.page.htmx.Component;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@RequiredArgsConstructor(staticName = "create")
public class LineBreak extends Component<LineBreak> {

    @Override
    public @NotNull Element rawRender() {
        return new Element("br").text("");
    }

}
