package de.allround.ssr.page.htmx;

import de.allround.ssr.page.htmx.css.Style;
import de.allround.ssr.util.Data;

import java.util.Set;

@FunctionalInterface
public interface StyleRenderFunction {
    Set<Style> renderStyles(Data data);
}
