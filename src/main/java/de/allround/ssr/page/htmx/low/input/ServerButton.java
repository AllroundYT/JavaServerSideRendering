package de.allround.ssr.page.htmx.low.input;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.util.HtmxMethod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;


/**
 * Der ServerButton ist ein Component um mit Hilfe eines clicks Server seitigen Code auszuführen.
 */
@RequiredArgsConstructor(staticName = "create")
@Getter
@Accessors(fluent = true)
@Setter
public class ServerButton extends Component<ServerButton> {

    private final String text, requestUri, target;
    private String confirmMsg;
    private HtmxMethod httpMethod = HtmxMethod.GET;

    @Override
    public @NotNull Element rawRender() {
        clazz("server-button");
        Element element = new Element("button");
        htmx().trigger(HTMXUtil.HTMXTrigger.CLICK).target(target);
        if (httpMethod != null) {
            if (httpMethod.equals(HtmxMethod.GET)) {
                htmx().get(requestUri);
            } else if (httpMethod.equals(HtmxMethod.POST)) {
                htmx().post(requestUri);
            }
        }
        element.text(text);
        if (confirmMsg != null) htmx().confirm(confirmMsg);
        return element;
    }
}
