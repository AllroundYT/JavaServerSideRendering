package de.allround.ssr.page.htmx.low.form;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TimeInput extends Form.FormInputComponent<TimeInput> {
    protected TimeInput() {
        super("time");
    }

    @Contract(" -> new")
    public static @NotNull TimeInput create() {
        return new TimeInput();
    }
}
