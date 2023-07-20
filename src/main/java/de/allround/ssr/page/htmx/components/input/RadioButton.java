package de.allround.ssr.page.htmx.components.input;

public class RadioButton extends InputComponent<RadioButton> {
    protected RadioButton() {
        super("radio");
    }

    public static RadioButton create() {
        return new RadioButton();
    }
}
