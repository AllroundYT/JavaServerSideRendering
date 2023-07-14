package de.allround.ssr.page.htmx.low.list;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.low.container.Container;
import de.allround.ssr.page.htmx.low.text.Paragraph;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ListItem extends Container<ListItem, Component<?>> {

    protected ListItem() {
        super(new ArrayList<>(), "li");
    }

    protected ListItem(Component<?>... components) {
        super(List.of(components), "li");
    }

    @Contract("_ -> new")
    public static @NotNull ListItem of(String content) {
        return new ListItem(Paragraph.create(content));
    }

    @Contract("_ -> new")
    public static @NotNull ListItem of(Component<?>... components) {
        return new ListItem(components);
    }
}
