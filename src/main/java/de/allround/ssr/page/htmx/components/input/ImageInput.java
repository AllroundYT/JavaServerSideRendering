package de.allround.ssr.page.htmx.components.input;

public class ImageInput extends InputComponent<ImageInput> {
    protected ImageInput() {
        super("image");
    }

    public static ImageInput create() {
        return new ImageInput();
    }
}
