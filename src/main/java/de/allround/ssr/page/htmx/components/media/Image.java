package de.allround.ssr.page.htmx.components.media;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.util.Data;
import de.allround.ssr.util.Triple;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.nio.file.Path;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends Component<Image> {

    private final Function<Data, Triple<Path, String, String>> content;

    @Override
    public RenderFunction preRender() {
        return data -> {
            Triple<Path, String, String> appliedContent = this.content.apply(data);
            Path filePath = appliedContent.first();
            data.webApplication().setupStaticResources(filePath, appliedContent.second());
            String route = appliedContent.second() + "/" + Path.of("").relativize(filePath);
            return new Element("img").attr("src", route).attr("alt", appliedContent.third());
        };
    }

    @Contract("_, _, _ -> new")
    public static @NotNull Image create(Path path, String route, String alt) {
        return new Image(data -> Triple.of(path, route, alt));
    }
}
