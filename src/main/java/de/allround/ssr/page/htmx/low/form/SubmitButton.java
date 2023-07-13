package de.allround.ssr.page.htmx.low.form;

public class SubmitButton extends Form.FormInputComponent<SubmitButton> {
    private SubmitButton() {
        super("submit");
    }

    public static SubmitButton create() {
        return new SubmitButton();
    }
}
