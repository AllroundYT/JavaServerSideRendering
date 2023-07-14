package de.allround.ssr.page.htmx.low.form;

public class RangeInput extends Form.FormInputComponent<RangeInput> {
    protected RangeInput() {
        super("range");
    }

    public static RangeInput create() {
        return new RangeInput();
    }
}
