package de.allround.ssr.page.htmx.low.form;

public class NumberInput extends Form.FormInputComponent<NumberInput> {

    private NumberInput() {
        super("number");
    }

    public static NumberInput create() {
        return new NumberInput();
    }
}
