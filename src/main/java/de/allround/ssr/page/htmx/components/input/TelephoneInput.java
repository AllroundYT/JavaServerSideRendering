package de.allround.ssr.page.htmx.components.input;

public class TelephoneInput extends InputComponent<TelephoneInput> {
    protected TelephoneInput() {
        super("tel");
    }

    public static TelephoneInput create() {
        return new TelephoneInput();
    }
}
