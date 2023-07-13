package de.allround.ssr.page.htmx.low.form;

public class MonthInput extends Form.FormInputComponent<MonthInput> {

    private MonthInput() {
        super("month");
    }

    public static MonthInput create() {
        return new MonthInput();
    }
}
