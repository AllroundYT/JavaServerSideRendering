package de.allround.ssr.page.css;

import de.allround.ssr.page.css.attributes.SizeType;
import de.allround.ssr.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.awt.*;

@Getter
@Setter
@Accessors(fluent = true)
public class Style {
    private final StringBuilder styleAttributes = new StringBuilder();
    private final StringBuilder selectors = new StringBuilder();

    private Color color;
    private Pair<Double, SizeType> border_top, border_bottom, border_left, border_right, border_radius, border_size;

    public String compile() {
        return selectors + " {" +
               (color != null ? "\ncolor: " + String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()) + ";" : "") +
               (border_top != null ? "\nborder-top: " + border_top.first() + border_top.second().symbol + ";" : "") +
               (border_bottom != null ? "\nborder-bottom: " + border_bottom.first() + border_bottom.second().symbol + ";" : "") +
               (border_left != null ? "\nborder-left: " + border_left.first() + border_left.second().symbol + ";" : "") +
               (border_right != null ? "\nborder-right: " + border_right.first() + border_right.second().symbol + ";" : "") +
               (border_radius != null ? "\nborder-radius: " + border_radius.first() + border_radius.second().symbol + ";" : "") +
               (border_size != null ? "\nborder-size: " + border_size.first() + border_size.second().symbol + ";" : "") +
               "\n}";
    }

    @Override
    public String toString() {
        return compile();
    }


    public Style addSelectors(StyleSelector @NotNull ... selectors) {
        boolean somethingBefore = false;
        for (StyleSelector selector : selectors) {
            this.selectors.append(somethingBefore ? " ," : "").append(selector.toSelectorString());
            somethingBefore = true;
        }
        return this;
    }

    public Style addSelectors(Element @NotNull ... elements) {
        boolean somethingBefore = false;
        for (Element element : elements) {
            this.selectors.append(somethingBefore ? " ," : "").append(element.cssSelector());
            somethingBefore = true;
        }
        return this;
    }


    public record StyleSelector(String id, String[] classes, String[] tags) {

        public @NotNull String toSelectorString() {
            final StringBuilder stringBuilder = new StringBuilder();
            for (String aClass : classes) {
                stringBuilder.append(".").append(aClass).append(" ");
            }
            if (id != null) stringBuilder.append("#").append(id).append(" ");
            for (String tag : tags) {
                stringBuilder.append(tag).append(" ");
            }
            return stringBuilder.toString().trim();
        }
    }
}
