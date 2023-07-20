package de.allround.ssr.page.htmx.components.input;

public class SearchInput extends InputComponent<SearchInput> {
    protected SearchInput() {
        super("search");
    }

    public static SearchInput create() {
        return new SearchInput();
    }
}
