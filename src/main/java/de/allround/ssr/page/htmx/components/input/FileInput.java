package de.allround.ssr.page.htmx.components.input;

public class FileInput extends InputComponent<FileInput> {
    protected FileInput() {
        super("file");
    }

    public static FileInput create() {
        return new FileInput();
    }
}
