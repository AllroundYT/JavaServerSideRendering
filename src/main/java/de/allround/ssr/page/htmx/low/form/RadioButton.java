package de.allround.ssr.page.htmx.low.form;

public class RadioButton extends Form.FormInputComponent<RadioButton> {


    protected RadioButton() {
        super("radio");
    }

    public static RadioButton create() {
        return new RadioButton();
    }
}
