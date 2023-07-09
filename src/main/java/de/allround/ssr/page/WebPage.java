package de.allround.ssr.page;

import de.allround.ssr.annotations.Injected;
import de.allround.ssr.injection.InjectionUtil;
import de.allround.ssr.page.css.Style;
import de.allround.ssr.page.css.Stylesheet;
import de.allround.ssr.page.htmx.Component;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
@Accessors(fluent = true)
public abstract class WebPage {
    private final LinkedList<Component<?>> DOM = new LinkedList<>();

    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected HttpServerRequest request;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected RoutingContext context;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected User user;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected Route route;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected Session session;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected RequestBody body;
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected ParsedHeaderValues parsedHeaderValues;
    private String lang = "de";
    private String title = "Generated Webpage";
    private String template = """
            <html lang=%LANG%>
            <head>
            <title>%TITLE%</title>
            </head>
            <body>
            </body>
            </html>
            """
            .replace("%LANG%", lang)
            .replace("%TITLE%", title);

    public abstract void init();

    public WebPage add(Component<?>... components) {
        if (components != null) {
            this.DOM.addAll(List.of(components));
        }
        return this;
    }

    public String render(InjectionUtil injectionUtil, Object... objects) {
        DOM.clear();

        init();

        DOM.forEach(component -> injectionUtil.inject(component, List.of(objects)));
        List<Stylesheet> stylesheets = new ArrayList<>(DOM.stream().map(Component::renderStyles).filter(Objects::nonNull).toList());
        DOM.forEach(component -> stylesheets.add(new Stylesheet().add(component.styles().toArray(new Style[0]))));
        //TODO serve htmx js file as static resource

        Document document = Jsoup.parse(template, "", Parser.htmlParser());
        document.getElementsByTag("head").get(0).appendChildren(stylesheets.stream().map(Stylesheet::toStyleTag).filter(Objects::nonNull).toList()).append(
                "<script src=\"https://unpkg.com/htmx.org@1.9.2\" integrity=\"sha384-L6OqL9pRWyyFU3+/bjdSri+iIphTN/bvYyM37tICVyOJkWZLpP2vGn6VUEXgzg6h\" crossorigin=\"anonymous\"></script>"
        );
        document.getElementsByTag("body").get(0).appendChildren(DOM.stream().map(Component::fullRender).filter(Objects::nonNull).toList());

        return document.outerHtml();
    }
}
