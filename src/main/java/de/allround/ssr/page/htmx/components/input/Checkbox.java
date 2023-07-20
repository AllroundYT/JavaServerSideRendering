package de.allround.ssr.page.htmx.components.input;

public class Checkbox extends InputComponent<Checkbox> {
    protected Checkbox() {
        super("checkbox");
    }

    public static Checkbox create() {
        return new Checkbox();
    }
}
