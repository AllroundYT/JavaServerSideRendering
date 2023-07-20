package de.allround.ssr.page.htmx.components.input;

public class EmailInput extends InputComponent<EmailInput> {
    protected EmailInput() {
        super("email");
    }

    public static EmailInput create() {
        return new EmailInput();
    }
}
