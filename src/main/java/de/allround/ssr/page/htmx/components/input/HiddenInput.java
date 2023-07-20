package de.allround.ssr.page.htmx.components.input;

public class HiddenInput extends InputComponent<HiddenInput> {
    protected HiddenInput() {
        super("hidden");
    }

    public static HiddenInput create() {
        return new HiddenInput();
    }
}
