package de.allround.ssr.page.htmx.components.container;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.Container;

public class Div extends Container<Div> {
    protected Div() {
        super("div");
    }

    public static Div create(Component<?>... components) {
        return new Div().add(components);
    }
}
