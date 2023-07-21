package de.allround.ssr.page;


import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.StyleRenderFunction;
import de.allround.ssr.util.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.util.*;

@Getter
@Setter
@Accessors(fluent = true)
public abstract class WebPage {
    private final LinkedList<Component<?>> dom = new LinkedList<>();
    private StyleRenderFunction styleRenderFunction = renderData -> Set.of();
    private final Data data = new Data();
    protected String lang = "de";
    protected String title = "Generated Webpage";
    protected final Set<String> extensions = new HashSet<>();
    private String script = "";


    public abstract void init();

    public WebPage add(Component<?>... components) {
        if (components != null) {
            this.dom.addAll(List.of(components));
        }
        return this;
    }

    public WebPage loadExtensions(String... names) {
        extensions.addAll(Set.of(names));
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
        head.append("<script src=\"//" + URI.create(data.request().absoluteURI()).getAuthority() + "/htmx/ssr-utils.js\" />");

        Element stylesElement = new Element("style");
        styleRenderFunction.renderStyles(data).forEach(style -> stylesElement.appendText(style.compile()));

        dom.forEach(component -> {
            Element element = component.render(data);
            registerExtensions(element);
            component.styles().renderStyles(data).forEach(style -> stylesElement.appendText(style.compile()));
            String localScript = component.script().apply(data);
            if (localScript != null && !this.script.contains(localScript)) this.script += "\n" + localScript;
            body.appendChild(element);
        });

        head.appendChild(stylesElement);

        if (script != null && !script.trim().equals("")) head.appendChild(new Element("script").text(this.script));

        extensions.forEach(extension -> {
            if (!Objects.equals(extension.trim(), "") && !extension.equalsIgnoreCase("ssr-utils"))
                head.append("<script src=\"//" + URI.create(data.request().absoluteURI()).getAuthority() + "/htmx/" + extension.trim() + ".js\" />");
        });

        return document.outerHtml();
    }

    private void registerExtensions(@NotNull Element element) {
        if (element.childrenSize() > 0) {
            element.children().forEach(this::registerExtensions);
        } else {
            extensions.addAll(Arrays.stream(element.attr("hx-ext").split(",")).map(String::trim).toList());
        }
    }
}
