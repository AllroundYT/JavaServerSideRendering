package de.allround.ssr.page.htmx.components.input;

public class NumberInput extends InputComponent<NumberInput> {
    protected NumberInput() {
        super("number");
    }

    public static NumberInput create() {
        return new NumberInput();
    }
}
