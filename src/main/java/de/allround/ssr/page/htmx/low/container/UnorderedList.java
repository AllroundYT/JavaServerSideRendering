package de.allround.ssr.page.htmx.low.container;

import java.util.ArrayList;
import java.util.List;

public class UnorderedList extends Container<UnorderedList, ListItem> {


    public UnorderedList() {
        super(new ArrayList<>(), "ul");
    }


    public UnorderedList addItems(ListItem... items) {
        this.components.addAll(List.of(items));
        return this;
    }

    public UnorderedList addItems(List<ListItem> items) {
        this.components.addAll(items);
        return this;
    }
}
