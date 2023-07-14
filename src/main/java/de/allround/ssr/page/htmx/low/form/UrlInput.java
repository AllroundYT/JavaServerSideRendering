package de.allround.ssr.page.htmx.low.form;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class UrlInput extends Form.FormInputComponent<UrlInput> {
    protected UrlInput() {
        super("url");
    }

    @Contract(" -> new")
    public static @NotNull UrlInput create() {
        return new UrlInput();
    }
}
