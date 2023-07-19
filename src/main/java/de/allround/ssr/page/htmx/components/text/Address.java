package de.allround.ssr.page.htmx.components.text;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "create")
public class Address extends Component<Address> {

    protected final Function<Data, String> content;

    @Override
    public RenderFunction preRender() {
        return data -> new Element("address").text(content.apply(data));
    }

    public static Address create(String content) {
        return new Address(data -> content);
    }
}
