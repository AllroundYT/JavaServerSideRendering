package de.allround.ssr.page.htmx.low.container;

import de.allround.ssr.page.htmx.Component;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.List;

@RequiredArgsConstructor(staticName = "create")
@Getter
@Accessors(fluent = true)
public class Div extends Component<Div> {
    private final List<? extends Component<?>> components;

    @Override
    public @NotNull Element rawRender() {
        Element element = new Element("div");
        element.appendChildren(components.stream().map(Component::fullRender).toList());
        return element;
    }

}
