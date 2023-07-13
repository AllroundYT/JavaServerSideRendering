package de.allround.ssr.page.htmx.low.form;

public class RadioButton extends Form.FormInputComponent<RadioButton> {


    private RadioButton() {
        super("radio");
    }

    public static RadioButton create() {
        return new RadioButton();
    }
}
