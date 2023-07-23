package de.allround.ssr.page.htmx.components.navigation;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.Container;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


public class ListItem extends Container<ListItem> {


    protected ListItem() {
        super("li");
    }

    @Contract("_ -> new")
    public static @NotNull ListItem create(String content) {
        return new ListItem().content(content);
    }

    @Contract("_ -> new")
    public static @NotNull ListItem create(Component<?>... components) {
        return new ListItem().add(components);
    }
}
