package de.allround.ssr.page.htmx.components.input;

public class DateInput extends InputComponent<DateInput> {
    protected DateInput() {
        super("date");
    }

    public static DateInput create() {
        return new DateInput();
    }
}
