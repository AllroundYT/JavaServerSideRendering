package de.allround.ssr.page.htmx.low.form;

public class SearchInput extends Form.FormInputComponent<SearchInput> {
    protected SearchInput() {
        super("search");
    }

    public static SearchInput create() {
        return new SearchInput();
    }
}
