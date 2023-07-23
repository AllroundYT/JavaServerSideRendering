package de.allround.ssr.page.htmx.components.custom;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "create")
public class Script extends Component<Script> {

    private final Function<Data, String> scriptContent;

    public static Script create(String scriptContent) {
        return new Script(data -> scriptContent);
    }

    @Override
    public RenderFunction preRender() {
        return data -> new Element("script").text(scriptContent.apply(data));
    }
}
