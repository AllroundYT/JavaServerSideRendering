package de.allround.ssr.page.htmx.low.form;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class WeekInput extends Form.FormInputComponent<WeekInput> {
    protected WeekInput() {
        super("week");
    }

    @Contract(" -> new")
    public static @NotNull WeekInput create() {
        return new WeekInput();
    }
}
