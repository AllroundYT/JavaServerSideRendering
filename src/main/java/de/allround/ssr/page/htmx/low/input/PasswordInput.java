package de.allround.ssr.page.htmx.low.input;

import de.allround.ssr.page.htmx.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;


@NoArgsConstructor(staticName = "create")
@AllArgsConstructor(staticName = "create")
@Getter
@Accessors(fluent = true)
@Setter
public class PasswordInput extends Component<PasswordInput> {

    private String placeholder;
    private String name;

    @Override
    public @NotNull Element rawRender() {
        Element element = new Element("input");
        element.attr("type", "password");
        clazz("password-input");
        if (name != null) element.attr("name", name);
        if (placeholder != null) element.attr("placeholder", placeholder);
        return element;
    }
}
