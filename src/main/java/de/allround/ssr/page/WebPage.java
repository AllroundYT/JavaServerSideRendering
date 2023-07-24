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
import java.nio.file.Path;
import java.util.*;

@Getter
@Setter
@Accessors(fluent = true)
public abstract class WebPage {
    protected final Set<String> extensions = new HashSet<>();
    private final LinkedList<Component<?>> dom = new LinkedList<>();
    private final Data data = new Data();
    protected String lang = "de";
    protected String title = "Generated Webpage";
    private StyleRenderFunction styleRenderFunction = renderData -> Set.of();
    private String script = "";
    private Path faviconPath;
    private boolean useTailwind = false;
    private boolean useBootstrap = false;
    private boolean useBoostrapPopper = true;
    private boolean useBulma = false;
    private boolean useMaterializeCss = false;
    private boolean useFoundationFramework = false;
    private boolean usePureCss = false;
    private boolean useUIKit = false;
    private boolean usePico = false;

    public abstract void init(Data data);

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

    public boolean isHeadElement(@NotNull Element element) {
        return List.of("link", "style", "script", "meta").contains(element.tagName().toLowerCase());
    }

    public String render() {
        dom.clear();
        init(data);
        Document document = new Document("");

        Element head = document.head();
        Element body = document.body();

        Objects.requireNonNull(document.getElementsByTag("html").first()).attr("lang", lang);
        head.appendChild(new Element("title").text(title));
        if (useBootstrap) {
            head.append("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM\" crossorigin=\"anonymous\">");
            head.append("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js\" integrity=\"sha384-fbbOQedDUMZZ5KreZpsbe1LCZPVmfTnH7ois6mU1QK+m14rQ1l2bGBq41eYeM/fS\" crossorigin=\"anonymous\"></script>");
            if (useBoostrapPopper)
                head.append("<script src=\"https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js\" integrity=\"sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r\" crossorigin=\"anonymous\"></script>");
        }
        if (usePico) {
            head.append("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/@picocss/pico@1/css/pico.min.css\">");
        }
        if (useMaterializeCss) {
            head.append("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css\">");
            head.append("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js\"></script>");
        }
        if (useFoundationFramework) {
            head.append("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/foundation-sites@6.7.5/dist/css/foundation.min.css\" crossorigin=\"anonymous\">");
            head.append("<script src=\"https://cdn.jsdelivr.net/npm/foundation-sites@6.7.5/dist/js/foundation.min.js\" crossorigin=\"anonymous\"></script>");
        }
        if (usePureCss) {
            head.append("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css\" integrity=\"sha384-X38yfunGUhNzHpBaEBsWLO+A0HDYOQi8ufWDkZ0k9e0eXz/tH3II7uKZ9msv++Ls\" crossorigin=\"anonymous\">");
        }
        if (useUIKit) {
            head.append("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/uikit@3.16.22/dist/css/uikit.min.css\" />");
            head.append("<script src=\"https://cdn.jsdelivr.net/npm/uikit@3.16.22/dist/js/uikit.min.js\"></script>");
            head.append("<script src=\"https://cdn.jsdelivr.net/npm/uikit@3.16.22/dist/js/uikit-icons.min.js\"></script>");
        }
        if (useBulma) head.append("<link href=\"https://www.jsdelivr.com/package/npm/bulma\" rel=\"stylesheet\">");
        if (useTailwind) head.append("<script src=\"https://cdn.tailwindcss.com\"></script>");
        head.append("<script src=\"" + URI.create("/htmx/htmx.min.js") + "\" />");
        head.append("<script src=\"" + URI.create("/htmx/ssr-utils.js") + "\" />");

        Element stylesElement = new Element("style");
        styleRenderFunction.renderStyles(data).stream().filter(style -> !stylesElement.text().contains(style.compile())).forEach(style -> stylesElement.appendText(style.compile()));

        dom.forEach(component -> {
            Element element = component.render(data);
            registerExtensions(element);
            component.styles().renderStyles(data).stream().filter(style -> !stylesElement.text().contains(style.compile())).forEach(style -> stylesElement.appendText(style.compile()));
            String localScript = component.script();
            if (localScript != null && !this.script.contains(localScript)) this.script += "\n" + localScript;
            if (isHeadElement(element)) head.appendChild(element);
            else body.appendChild(element);
        });

        head.appendChild(stylesElement);

        if (script != null && !script.trim().equals("")) head.appendChild(new Element("script").text(this.script));


        extensions.forEach(extension -> {
            if (!Objects.equals(extension.trim(), "") && !extension.equalsIgnoreCase("ssr-utils"))
                head.append("<script src=\"" + URI.create("/htmx/" + extension.trim() + ".js") + "\" />");
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
