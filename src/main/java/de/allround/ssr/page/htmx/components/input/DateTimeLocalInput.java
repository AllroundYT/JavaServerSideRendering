package de.allround.ssr.page.htmx.components.input;

public class DateTimeLocalInput extends InputComponent<DateTimeLocalInput> {
    protected DateTimeLocalInput() {
        super("datetime-local");
    }

    public static DateTimeLocalInput create() {
        return new DateTimeLocalInput();
    }
}
