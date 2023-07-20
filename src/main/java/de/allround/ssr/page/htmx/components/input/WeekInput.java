package de.allround.ssr.page.htmx.components.input;

public class WeekInput extends InputComponent<WeekInput> {
    protected WeekInput() {
        super("week");
    }

    public static WeekInput create() {
        return new WeekInput();
    }
}
