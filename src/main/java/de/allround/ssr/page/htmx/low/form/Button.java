package de.allround.ssr.page.htmx.low.form;

public class Button extends Form.FormInputComponent<Button> {

    private Button() {
        super("button");
    }

    public static Button create() {
        return new Button();
    }
}
