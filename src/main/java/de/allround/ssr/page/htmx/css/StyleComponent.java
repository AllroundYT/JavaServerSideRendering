package de.allround.ssr.page.htmx.css;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.page.htmx.StyleRenderFunction;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor(staticName = "create")
public class StyleComponent extends Component<StyleComponent> {

    private final List<Style> styles;

    public static StyleComponent create(Style... styles) {
        return new StyleComponent(List.of(styles));
    }

    @Override
    public StyleRenderFunction styles() {
        return data -> new HashSet<>(styles);
    }

    @Override
    public RenderFunction preRender() {
        return data -> {
            Element element = new Element("style");
            styles().renderStyles(data).forEach(style -> element.appendText(style.compile()));
            return element;
        };
    }
}
