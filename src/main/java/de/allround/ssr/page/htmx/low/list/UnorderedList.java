package de.allround.ssr.page.htmx.low.list;

import de.allround.ssr.page.htmx.low.container.Container;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UnorderedList extends Container<UnorderedList, ListItem> {


    public static UnorderedList create(List<ListItem> contents) {
        return new UnorderedList().addItems(contents);
    }

    @Contract(" -> new")
    public static @NotNull UnorderedList create() {
        return new UnorderedList();
    }

    private UnorderedList() {
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
