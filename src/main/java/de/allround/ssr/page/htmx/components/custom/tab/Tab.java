package de.allround.ssr.page.htmx.components.custom.tab;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.Container;

public class Tab extends Container<Tab> {

    protected Tab() {
        super("div");
        addAttribute("ssr-tab");
        extension("ssr-utils");
    }

    public static Tab create(Component<?>... components) {
        return new Tab().add(components);
    }
}
