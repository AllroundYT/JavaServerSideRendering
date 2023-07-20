package de.allround.ssr.page.htmx.components.input;

public class TextInput extends InputComponent<TextInput> {
    protected TextInput() {
        super("text");
    }

    public static TextInput create() {
        return new TextInput();
    }
}
