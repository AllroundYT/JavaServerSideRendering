package de.allround.ssr.page.htmx.components.text;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jsoup.nodes.Element;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RequiredArgsConstructor(staticName = "create")
@Getter
@Setter
@Accessors(fluent = true)
public class Headline extends Component<Headline> {


    @Max(6)
    @Min(1)
    private int size = 1;

    @Override
    public RenderFunction preRender() {
        return data -> new Element("h" + size);
    }

}
