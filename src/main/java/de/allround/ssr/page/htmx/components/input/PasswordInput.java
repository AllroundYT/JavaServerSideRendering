package de.allround.ssr.page.htmx.components.input;

public class PasswordInput extends InputComponent<PasswordInput> {
    protected PasswordInput() {
        super("password");
    }

    public static PasswordInput create() {
        return new PasswordInput();
    }
}
