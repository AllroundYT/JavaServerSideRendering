package de.allround.ssr.page.htmx.low.form;

public class ColorInput extends Form.FormInputComponent<ColorInput> {

    private ColorInput() {
        super("color");
    }

    public static ColorInput create() {
        return new ColorInput();
    }
}
