package de.allround.ssr.page.htmx.components.custom;

import de.allround.ssr.page.htmx.Component;

import java.net.URI;

public class LazyComponent {

    @SuppressWarnings("unchecked")
    public static <T extends Component<T>> Component<T> init(Class<T> tClass, URI uri) {
        return (Component<T>) T.create().get(uri).swap("outerHTML").trigger("load");
    }
}
