package de.allround.ssr.page.htmx.staticcomponents.low;

import de.allround.ssr.page.css.Stylesheet;
import de.allround.ssr.page.htmx.Component;
import lombok.Builder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@Builder
public class LineBreak extends Component<LineBreak> {
    @Contract(" -> new")
    public static @NotNull LineBreak use() {
        return new LineBreak();
    }

    @Override
    public @NotNull Element rawRender() {
        return new Element("br").text("");
    }

    @Override
    public Stylesheet renderStyles() {
        return null;
    }
}
