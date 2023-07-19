package de.allround.ssr.page.htmx;

import de.allround.ssr.util.Data;
import org.jsoup.nodes.Element;

@FunctionalInterface
public interface RenderFunction {
    Element render(Data data);
}
