package de.allround.ssr.page.htmx.components.custom.tab;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.Container;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.page.htmx.StyleRenderFunction;
import de.allround.ssr.page.htmx.css.Style;
import de.allround.ssr.page.htmx.util.DisplayType;

import java.util.Set;

public class TabContainer extends Container<TabContainer> {
    protected TabContainer() {
        super("div");
        addAttribute("ssr-tab-container");
        extension("ssr-utils");
    }

    public static TabContainer create(Component<?>... components) {
        return new TabContainer().add(components);
    }

    @Override
    public RenderFunction preRender() {
        return data -> {
            boolean first = true;
            for (Component<?> component : this.components.stream().filter(Tab.class::isInstance).toList()) {
                if (!first) component.clazz("ssr-tab-inactive");
                first = false;
            }
            return super.preRender().render(data);
        };
    }

    @Override
    public StyleRenderFunction styles() {
        return data -> Set.of(
                Style.selector("[ssr-tab].ssr-tab-inactive").display(DisplayType.NONE)
        );
    }
}
