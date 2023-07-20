package de.allround.ssr.page.htmx.components.input;

public class MonthInput extends InputComponent<MonthInput> {
    protected MonthInput() {
        super("month");
    }

    public static MonthInput create() {
        return new MonthInput();
    }
}
