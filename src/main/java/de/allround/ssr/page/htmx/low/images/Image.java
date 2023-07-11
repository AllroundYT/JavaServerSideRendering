package de.allround.ssr.page.htmx.low.images;

import de.allround.ssr.page.htmx.Component;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.nio.file.Path;

@RequiredArgsConstructor(staticName = "create")
@Getter
@Accessors(fluent = true)
@Setter
public class Image extends Component<Image> {

    private final Path imagePath;
    private final String imageRoute;

    @Override
    public @NotNull Element rawRender() {
        Element element = new Element("img");
        element.attr("src", host() + "/" + imageRoute);
        return null;
    }
}
