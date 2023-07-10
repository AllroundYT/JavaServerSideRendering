package de.allround.ssr.page.css;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.dynamiccomponents.low.ServerButton;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StyleLibrary {
    private final Map<Class<? extends Component<?>>, Style> styles;

    public static final StyleLibrary DefaultBlue = new StyleLibrary(Map.of(
            ServerButton.class, new Style()
                    .backgroundColor(new Color(0, 126, 255))
                    .color(Color.BLACK)
                    .padding(new Style.CSSSize(1, Style.SizeType.EM))
                    .margin(new Style.CSSSize(1, Style.SizeType.EM))
                    .width(new Style.CSSSize(10, Style.SizeType.EM))
                    .font(Font.getFont("Arial"))
                    .borderRadius(new Style.CSSSize(5, Style.SizeType.PIXEL))
                    .borderLeft(new Style.CSSSize(2, Style.SizeType.PIXEL))
                    .borderRight(new Style.CSSSize(2, Style.SizeType.PIXEL))
                    .borderTop(new Style.CSSSize(2, Style.SizeType.PIXEL))
                    .borderBottom(new Style.CSSSize(2, Style.SizeType.PIXEL))
                    .borderStyle(Style.BorderStyle.SOLID)
    ));

    public static final StyleLibrary DefaultGreen = new StyleLibrary(Map.of(
            ServerButton.class, new Style()
                    .backgroundColor(new Color(126, 255, 0))
                    .color(Color.BLACK)
                    .padding(new Style.CSSSize(1, Style.SizeType.EM))
                    .margin(new Style.CSSSize(1, Style.SizeType.EM))
                    .width(new Style.CSSSize(10, Style.SizeType.EM))
                    .font(Font.getFont("Arial"))
                    .borderRadius(new Style.CSSSize(5, Style.SizeType.PIXEL))
                    .borderLeft(new Style.CSSSize(2, Style.SizeType.PIXEL))
                    .borderRight(new Style.CSSSize(2, Style.SizeType.PIXEL))
                    .borderTop(new Style.CSSSize(2, Style.SizeType.PIXEL))
                    .borderBottom(new Style.CSSSize(2, Style.SizeType.PIXEL))
                    .borderStyle(Style.BorderStyle.SOLID)
    ));

    public <T extends Component<?>> Style getStyle(T component) {
        return styles.getOrDefault(component.getClass(), new Style()).selector(new Selector().id(component.id()));
    }
}
