package de.allround.ssr.page.css;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.low.text.Headline;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
public abstract class StyleLibrary {

    public abstract void init();

    protected final Map<Class<? extends Component<?>>, Stylesheet> styles = new HashMap<>();
    protected final Color primaryColor, secondaryColor, accentColor, complimentaryColor;
    protected final Font font;

    public StyleLibrary style(Class<? extends Component<?>> componentClass, Style... styles) {
        this.styles.putIfAbsent(componentClass, new Stylesheet());
        this.styles.get(componentClass).add(styles);
        return this;
    }

    public static final StyleLibrary DefaultBlue = new StyleLibrary(
            new Color(0, 0, 255),
            new Color(2, 21, 115),
            new Color(0, 255, 255),
            new Color(255, 150, 0),
            Font.getFont("Arial")
    ) {
        @Override
        public void init() {
            style(Headline.class, new Style().selector(Selector.byClass("headline")).font(this.font).color(this.primaryColor));
        }
    };


    public <T extends Component<?>> Stylesheet getStyle(@NotNull T component) {
        return styles.getOrDefault(component.getClass(), new Stylesheet());
    }
}
