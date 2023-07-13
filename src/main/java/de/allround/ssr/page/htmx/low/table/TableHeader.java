package de.allround.ssr.page.htmx.low.table;

import de.allround.ssr.page.htmx.Component;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@RequiredArgsConstructor(staticName = "of")
public class TableHeader extends Component<TableHeader> {
    private final String content;

    @Override
    public @NotNull Element rawRender() {
        return new Element("th").text(content);
    }
}