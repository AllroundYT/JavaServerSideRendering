package de.allround.ssr.page.htmx.components.input;

public class TimeInput extends InputComponent<TimeInput> {
    protected TimeInput() {
        super("time");
    }

    public static TimeInput create() {
        return new TimeInput();
    }
}
