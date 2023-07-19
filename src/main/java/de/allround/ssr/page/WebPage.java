package de.allround.ssr.page;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.util.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.util.*;

@Getter
@Setter
@Accessors(fluent = true)
public abstract class WebPage {
    private final LinkedList<Component<?>> dom = new LinkedList<>();
    private final Data data = new Data();
    protected String lang = "de";
    protected String title = "Generated Webpage";


    public abstract void init();

    public WebPage add(Component<?>... components) {
        if (components != null) {
            this.dom.addAll(List.of(components));
        }
        return this;
    }


    public String render() {
        dom.clear();
        init();
        Document document = new Document("");

        Element head = document.head();
        Element body = document.body();

        Objects.requireNonNull(document.getElementsByTag("html").first()).attr("lang", lang);
        head.appendChild(new Element("title").text(title));
        head.append("<script src=\"//" + URI.create(data.request().absoluteURI()).getAuthority() + "/htmx/htmx.min.js\" />");

        Set<String> extensions = new HashSet<>();

        dom.forEach(component -> {
            Element element = component.render(data);
            extensions.addAll(Arrays.stream(element.attr("hx-ext").split(",")).map(String::trim).toList());
            Element stylesElement = component.renderStyles(data);
            head.appendChild(stylesElement);
            body.appendChild(element);
        });

        extensions.forEach(extension -> head.append("<script src=\"//" + URI.create(data.request().absoluteURI()).getAuthority() + "/htmx/" + extension + ".js\" />"));

        return document.outerHtml();
    }
}
