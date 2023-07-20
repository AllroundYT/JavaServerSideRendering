package de.allround.ssr.page.htmx.components.input;

public class RangeInput extends InputComponent<RangeInput> {
    protected RangeInput() {
        super("range");
    }

    public static RangeInput create() {
        return new RangeInput();
    }
}
