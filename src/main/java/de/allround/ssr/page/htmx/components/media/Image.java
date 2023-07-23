package de.allround.ssr.page.htmx.components.media;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import de.allround.ssr.util.Pair;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends Component<Image> {

    private final Function<Data, Pair<String, String>> content;

    @Contract("_, _ -> new")
    public static @NotNull Image create(String path, String alt) {
        return new Image(data -> Pair.of(path, alt));
    }

    @Override
    public RenderFunction preRender() {
        return data -> {
            Pair<String, String> pair = this.content.apply(data);
            String src = pair.first();
            String alt = pair.second();
            return new Element("img").attr("src", src).attr("alt", alt);
        };
    }
}
