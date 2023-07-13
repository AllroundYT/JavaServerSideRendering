package de.allround.ssr.page.htmx.low.list;

import de.allround.ssr.page.htmx.low.container.Container;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrderedList extends Container<OrderedList, ListItem> {


    public static OrderedList create(List<ListItem> contents) {
        return new OrderedList().addItems(contents);
    }

    @Contract(" -> new")
    public static @NotNull OrderedList create() {
        return new OrderedList();
    }

    private OrderedList() {
        super(new ArrayList<>(), "ol");
    }


    public OrderedList addItems(ListItem... items) {
        this.components.addAll(List.of(items));
        return this;
    }

    public OrderedList addItems(List<ListItem> items) {
        this.components.addAll(items);
        return this;
    }
}
