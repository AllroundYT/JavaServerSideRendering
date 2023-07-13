package de.allround.ssr.page;

import de.allround.ssr.page.css.Style;
import de.allround.ssr.page.css.Stylesheet;
import de.allround.ssr.page.htmx.Component;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@lombok.Data
@Accessors(fluent = true)
public abstract class WebPage {
    private final LinkedList<Component<?>> DOM = new LinkedList<>();
    private final Set<String> extensions = new HashSet<>();
    private final Set<String> extensionNames = new HashSet<>();
    private final List<Style> styles = new ArrayList<>();
    protected RoutingContext context;
    @Setter(AccessLevel.PRIVATE)
    protected User user;
    @Setter(AccessLevel.PRIVATE)
    protected Session session;
    @Setter(AccessLevel.PRIVATE)
    protected HttpServerResponse response;
    @Setter(AccessLevel.PRIVATE)
    protected HttpServerRequest request;
    @Setter(AccessLevel.PRIVATE)
    protected RequestBody requestBody;
    @Setter(AccessLevel.PRIVATE)
    protected Route currentRoute;
    private Vertx vertx;
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

    public WebPage useSSE() {
        extensions.add("/sse.js");
        extensionNames.add("sse");
        return this;
    }

    public WebPage useRestored() {
        extensions.add("/sse.js");
        extensionNames.add("sse");
        return this;
    }

    public WebPage context(@NotNull RoutingContext context) {
        this.context = context;
        this.user = context.user();
        this.session = context.session();
        this.response = context.response();
        this.request = context.request();
        this.requestBody = context.body();
        this.currentRoute = context.currentRoute();
        return this;
    }

    public WebPage style(Style... styles) {
        this.styles.addAll(List.of(styles));
        return this;
    }

    public abstract void init();

    public WebPage add(Component<?>... components) {
        if (components != null) {
            this.DOM.addAll(List.of(components));
        }
        return this;
    }

    public void updateTemplate() {
        template = template
                .replace("%LANG%", lang)
                .replace("%TITLE%", title);
    }

    @SneakyThrows
    public String render() {
        DOM.clear();
        init();
        updateTemplate();
        DOM.forEach(component -> component.context(context).vertx(vertx).page(this));
        List<Stylesheet> stylesheets = DOM.stream().map(Component::stylesheet).collect(Collectors.toList());
        styles.forEach(style -> stylesheets.add(new Stylesheet().add(style)));
        Document document = Jsoup.parse(template, "", Parser.htmlParser());
        Element head = document.getElementsByTag("head").get(0);
        Element body = document.getElementsByTag("body").get(0);
        head.appendChildren(stylesheets.stream().map(Stylesheet::toStyleTag).filter(Objects::nonNull).toList()).append(
                "<script src=\"//" + URI.create(context.request().absoluteURI()).getAuthority() + "/htmx/htmx.min.js\" />"
        );

        extensions.forEach(s -> head.append("<script src=\"//" + URI.create(context.request().absoluteURI()).getAuthority() + "/htmx/" + s + "\" />"));

        body.attr("hx-ext", String.join(",", extensionNames));

        body.appendChildren(DOM.stream().map(Component::fullRender).filter(Objects::nonNull).toList());

        return document.outerHtml();
    }
}
