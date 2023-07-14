package de.allround.ssr.page.htmx.low.form;

public class DatetimeLocalInput extends Form.FormInputComponent<DatetimeLocalInput> {
    protected DatetimeLocalInput() {
        super("datetime-local");
    }

    public static DatetimeLocalInput create() {
        return new DatetimeLocalInput();
    }
}
