package de.allround.ssr.page.htmx.low.form;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;


public class ImageSubmitButton extends Form.FormInputComponent<ImageSubmitButton> {

    private final String src;
    private final String alt;
    private final int width, height;

    @Contract("_, _, _, _ -> new")
    public static @NotNull ImageSubmitButton create(String src, String alt, int width, int height) {
        return new ImageSubmitButton(src, alt, width, height);
    }

    public ImageSubmitButton(String src, String alt, int width, int height) {
        super("image");
        this.src = src;
        this.alt = alt;
        this.width = width;
        this.height = height;
    }

    @Override
    public @NotNull Element render(@NotNull Element element) {
        return element
                .attr("src", src)
                .attr("alt", alt)
                .attr("height", String.valueOf(height))
                .attr("width", String.valueOf(width));
    }
}
