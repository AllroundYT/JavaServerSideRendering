package de.allround.ssr.page.htmx.components.input;

public class ResetButton extends InputComponent<ResetButton> {
    protected ResetButton() {
        super("reset");
    }

    public static ResetButton create() {
        return new ResetButton();
    }
}
