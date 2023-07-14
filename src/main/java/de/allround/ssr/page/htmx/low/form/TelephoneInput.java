package de.allround.ssr.page.htmx.low.form;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TelephoneInput extends Form.FormInputComponent<TelephoneInput> {
    protected TelephoneInput() {
        super("tel");
    }

    @Contract(" -> new")
    public static @NotNull TelephoneInput create() {
        return new TelephoneInput();
    }
}
