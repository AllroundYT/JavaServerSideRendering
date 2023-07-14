package de.allround.ssr.page.htmx.low.form;

public class DateInput extends Form.FormInputComponent<DateInput> {


    protected DateInput() {
        super("date");
    }

    public static DateInput create() {
        return new DateInput();
    }
}
