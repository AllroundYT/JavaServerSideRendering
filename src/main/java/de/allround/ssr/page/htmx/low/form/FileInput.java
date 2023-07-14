package de.allround.ssr.page.htmx.low.form;

public class FileInput extends Form.FormInputComponent<FileInput> {
    protected FileInput() {
        super("file");
    }

    public static FileInput create() {
        return new FileInput();
    }
}
