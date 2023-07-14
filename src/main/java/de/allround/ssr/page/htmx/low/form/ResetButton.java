package de.allround.ssr.page.htmx.low.form;

public class ResetButton extends Form.FormInputComponent<ResetButton> {


    protected ResetButton() {
        super("reset");
    }

    public static ResetButton create() {
        return new ResetButton();
    }
}
