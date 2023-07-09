package de.allround.ssr.page.htmx.dynamiccomponents.low;

import de.allround.ssr.page.css.Stylesheet;
import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.util.HttpMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@Builder
@Getter
@Accessors(fluent = true)
public class ServerButton extends Component<ServerButton> {

    private final String text, requestUri, resultDisplaySelector, confirmMsg;
    private final HttpMethod httpMethod;

    @Override
    public @NotNull Element rawRender() {
        Element element = new Element("button");
        element.text(text).attr("hx-trigger", "click").attr("hx-" + httpMethod.getHttpMethod().name().toLowerCase(), requestUri).attr("hx-target", resultDisplaySelector);
        if (confirmMsg != null) element.attr("hx-confirm", confirmMsg);
        return element;
    }

    @Override
    public Stylesheet renderStyles() {
        return null;
    }
}
