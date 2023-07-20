package de.allround.ssr.page.htmx.components.input;

import org.jetbrains.annotations.NotNull;

public class SubmitButton extends InputComponent<SubmitButton> {


    protected SubmitButton() {
        super("submit");
    }


    public static @NotNull SubmitButton create() {
        return new SubmitButton();
    }
}
