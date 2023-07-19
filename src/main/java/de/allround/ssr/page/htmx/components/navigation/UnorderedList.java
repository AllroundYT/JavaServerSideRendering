package de.allround.ssr.page.htmx.components.navigation;

import de.allround.ssr.page.htmx.Container;


public class UnorderedList extends Container<UnorderedList> {
    protected UnorderedList() {
        super("ul");
    }

    public static UnorderedList create() {
        return new UnorderedList();
    }

    public static UnorderedList create(ListItem... listItems) {
        return new UnorderedList().add(listItems);
    }
}
