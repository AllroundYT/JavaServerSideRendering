package de.allround.ssr.page.htmx.components.input;

public class ColorInput extends InputComponent<ColorInput> {
    protected ColorInput() {
        super("color");
    }

    public static ColorInput create() {
        return new ColorInput();
    }
}
