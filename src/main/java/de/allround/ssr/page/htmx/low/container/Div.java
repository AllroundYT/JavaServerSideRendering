package de.allround.ssr.page.htmx.low.container;

import de.allround.ssr.page.htmx.Component;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;


@Getter
@Accessors(fluent = true)
public class Div extends Container<Div, Component<?>> {

    private Div(List<Component<?>> components) {
        super("div", components);
    }

    @Contract("_ -> new")
    public static @NotNull Div create(List<Component<?>> components) {
        return new Div(components);
    }

    @Contract("_ -> new")
    public static @NotNull Div create(Component<?>... components) {
        return new Div(List.of(components));
    }
}
