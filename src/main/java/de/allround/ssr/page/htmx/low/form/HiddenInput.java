package de.allround.ssr.page.htmx.low.form;

public class HiddenInput extends Form.FormInputComponent<HiddenInput> {

    private HiddenInput() {
        super("hidden");
    }

    public static HiddenInput create() {
        return new HiddenInput();
    }
}
