package de.allround.ssr.page.htmx.low.form;

public class EmailInput extends Form.FormInputComponent<EmailInput> {
    protected EmailInput() {
        super("email");
    }

    public static EmailInput create() {
        return new EmailInput();
    }
}
