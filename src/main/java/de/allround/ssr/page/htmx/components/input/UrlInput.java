package de.allround.ssr.page.htmx.components.input;

public class UrlInput extends InputComponent<UrlInput> {
    protected UrlInput() {
        super("url");
    }

    public static UrlInput create() {
        return new UrlInput();
    }
}
