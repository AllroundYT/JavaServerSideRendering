package de.allround.ssr.page.htmx.dynamiccomponents.low;

import de.allround.ssr.page.htmx.Component;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@Builder
@Getter
@Accessors(fluent = true)
public class TextInput extends Component<TextInput> {

    private final String placeholder;

    @Override
    public @NotNull Element rawRender() {
        Element element = new Element("input");
        element.attr("type", "text").attr("placeholder", placeholder);
        return element;
    }

}
