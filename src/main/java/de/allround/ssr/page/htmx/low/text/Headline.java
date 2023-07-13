package de.allround.ssr.page.htmx.low.text;

import de.allround.ssr.page.htmx.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RequiredArgsConstructor(staticName = "create")
@Getter
@Accessors(fluent = true)
@Setter
@AllArgsConstructor
public class Headline extends Component<Headline> {
    private final String content;
    @Min(1)
    @Max(6)
    private int type = 1;

    @Override
    public @NotNull Element rawRender() {
        clazz("headline");
        return new Element("h" + type).text(content);
    }
}
