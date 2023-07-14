package de.allround.ssr.page.htmx.low.form;

public class CheckBox extends Form.FormInputComponent<CheckBox> {

    protected CheckBox() {
        super("checkbox");
    }

    public static CheckBox create() {
        return new CheckBox();
    }
}
