package de.allround.ssr.page.htmx;

import de.allround.ssr.page.WebPage;
import de.allround.ssr.page.css.Selector;
import de.allround.ssr.page.css.Style;
import de.allround.ssr.page.css.StyleLibrary;
import de.allround.ssr.page.css.Stylesheet;
import de.allround.ssr.page.htmx.util.PreloadTrigger;
import de.allround.ssr.util.Pair;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.util.*;

@Getter
@Setter
@Accessors(fluent = true)
@SuppressWarnings("ALL")
public abstract class Component<T extends Component<?>> {
    private final List<Style> styles = new ArrayList<>();
    private final List<Pair<String, String>> attributes = new ArrayList<>();
    private final List<String> classes = new ArrayList<>();
    private final Map<String, String> hxAttributes = new HashMap<>();
    private final EnumSet<RequestSendingAttribute> requestSendingAttributes = EnumSet.noneOf(RequestSendingAttribute.class);
    private final Map<String, String> extensions = new HashMap<>();
    private final Map<String, Set<String>> attributeMap = new HashMap<>();
    private final Set<String> ignoredExtensions = new HashSet<>(List.of("method-override", "json-enc"));
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
    protected WebPage page;
    protected Vertx vertx;

    // Methods for HTMX attributes
    private String id = "ID" + UUID.randomUUID().toString().replace("-", "");

    public Stylesheet stylesheet() {
        return new Stylesheet().add(styles);
    }

    /**
     * Sets the value for an HTMX attribute.
     *
     * @param attributeName  the name of the HTMX attribute
     * @param attributeValue the value of the HTMX attribute
     * @return the T instance
     */
    public T setAttribute(String attributeName, String attributeValue) {
        removeConflictingAttributes(attributeName);
        hxAttributes.put(attributeName, attributeValue);
        return (T) this;
    }

    /**
     * Gets the value of an HTMX attribute.
     *
     * @param attributeName the name of the HTMX attribute
     * @return the value of the HTMX attribute
     */
    public String getAttribute(String attributeName) {
        return hxAttributes.get(attributeName);
    }

    /**
     * Removes an HTMX attribute.
     *
     * @param attributeName the name of the HTMX attribute to remove
     * @return the T instance
     */
    public T removeAttribute(String attributeName) {
        hxAttributes.remove(attributeName);
        return (T) this;
    }

    /**
     * Adds all set HTMX attributes to a Jsoup element.
     *
     * @param element the Jsoup element to add the HTMX attributes to
     */
    public void addAttributesToElement(Element element) {
        for (Map.Entry<String, String> entry : hxAttributes.entrySet()) {
            String attributeName = entry.getKey();
            String attributeValue = entry.getValue();
            if (attributeValue.startsWith("#")) attributeValue = attributeValue.replace("#", "#ID");
            element.attr(attributeName, attributeValue);
        }
    }

    private void removeConflictingAttributes(String attributeName) {
        if (RequestSendingAttribute.isRequestSendingAttribute(attributeName)) {
            requestSendingAttributes.clear();
        }
    }

    public T vertx(Vertx vertx) {
        this.vertx = vertx;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T page(WebPage webPage) {
        this.page = webPage;
        return (T) this;
    }

    public T context(@NotNull RoutingContext context) {
        this.context = context;
        this.user = context.user();
        this.session = context.session();
        this.response = context.response();
        this.request = context.request();
        this.requestBody = context.body();
        this.currentRoute = context.currentRoute();
        return (T) this;
    }

    public abstract @NotNull Element rawRender();

    @SuppressWarnings("unchecked")
    public T style(@NotNull Style style) {
        style.selector(Selector.byID(id));
        this.styles.add(style);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T loadStyle(@NotNull StyleLibrary styleLibrary) {
        this.styles.addAll(styleLibrary.getStyle(this).styles());
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T styles(@NotNull Style style, Style @NotNull ... styles) {
        style.selector(Selector.byID(id));
        for (Style sty : styles) {
            sty.selector(Selector.byID(id));
        }
        this.styles.add(style);
        this.styles.addAll(List.of(styles));
        return (T) this;
    }

    public T ignoreExtension(String extension) {
        ignoredExtensions.add(extension);
        return (T) this;
    }
    //TODO: DEFAULT

    public T trigger(String trigger) {
        addAttribute("hx-trigger", trigger);
        return (T) this;
    }

    public T target(String target) {
        addAttribute("hx-target", target);
        return (T) this;
    }

    public T swap(String swap) {
        addAttribute("hx-swap", swap);
        return (T) this;
    }

    public T params(String... params) {
        addAttribute("hx-params", params);
        return (T) this;
    }

    public T include(String... include) {
        addAttribute("hx-include", include);
        return (T) this;
    }

    public T get(String get) {
        addAttribute("hx-get", get);
        return (T) this;
    }

    public T post(String post) {
        addAttribute("hx-post", post);
        return (T) this;
    }

    public T put(String put) {
        addAttribute("hx-put", put);
        return (T) this;
    }

    public T delete(String delete) {
        addAttribute("hx-delete", delete);
        return (T) this;
    }

    public T confirm(String confirm) {
        addAttribute("hx-confirm", confirm);
        return (T) this;
    }

    //SSR-UTILS

    public T scroll(@NotNull ScrollDestination scrollDestination) {
        extensions.put("ssr-utils", "ssr-utils.js");
        addAttribute("ssr-scroll", scrollDestination.name().toLowerCase());
        return (T) this;
    }

    //RESTORED
    public T restored() {
        extensions.put("restored", "restored.js");
        addAttribute("hx-trigger", "restored");
        return (T) this;
    }

    //SSE
    public T sseConnect(String uri) {
        extensions.put("sse", "sse.js");
        addAttribute("sse-connect", uri);
        return (T) this;
    }

    public T sseSwap(String eventName) {
        extensions.put("sse", "sse.js");
        addAttribute("sse-swap", eventName);
        return (T) this;
    }

    public T sseTrigger(String eventName) {
        extensions.put("sse", "sse.js");
        addAttribute("hx-trigger", "sse:" + eventName);
        return (T) this;
    }

    //REMOVE-ME
    public T removeMe(int millis) {
        extensions.put("remove-me", "remove-me.js");
        addAttribute("remove-me", millis + "ms");
        return (T) this;
    }

    //TODO: RESPONSE-TARGETS

    //PRELOAD
    public T preload(String trigger) {
        extensions.put("preload", "preload.js");
        if (trigger == null) trigger = PreloadTrigger.MOUSE_DOWN.getValue();
        addAttribute("preload", trigger);
        return (T) this;
    }

    //PATH-DEPS
    public T pathDeps(String path) {
        extensions.put("path-deps", "path-deps.js");
        addAttribute("hx-trigger", "path-deps").addAttribute("path-deps", path);
        return (T) this;
    }

    //MULTI-SWAP
    public T multiSwap(String... targets) {
        extensions.put("multi-swap", "multi-swap.js");
        addAttribute("hx-swap", "multi:" + String.join(",", List.of(targets)));
        return (T) this;
    }

    //MORPHDOM-SWAP
    public T morphdomSwap() {
        extensions.put("morphdom-swap", "morphdom-swap.js");
        addAttribute("hx-swap", "morphdom");
        return (T) this;
    }

    //METHOD-OVERRIDE
    public T methodOverride() {
        extensions.put("method-override", "method-override.js");
        ignoredExtensions.remove("method-override");
        return (T) this;
    }

    //JSON-ENC
    public T jsonEnc() {
        extensions.put("json-enc", "json-enc.js");
        ignoredExtensions.remove("json-enc");
        return (T) this;
    }

    //INCLUDE-VALS
    public T includeVals(String value) {
        extensions.put("include-vals", "include-vals.js");
        addAttribute("include-vals", value);
        return (T) this;
    }

    public T addAttribute(String key, String... values) {
        attributeMap.compute(key, (attributeName, attributeValues) -> {
            if (attributeValues == null) attributeValues = new HashSet<>();
            attributeValues.addAll(List.of(values));
            return attributeValues;
        });
        return (T) this;
    }

    public T registerExtensions(WebPage webPage) {
        if (webPage == null) return (T) this;
        extensions.forEach((name, uri) -> {
            webPage.extensions().add(uri);
            webPage.extensionNames().add(name);
        });
        return (T) this;
    }

    public T registerAttributes(Element element) {
        ignoredExtensions.forEach(s -> addAttribute("hx-ext", "ignored:" + s));
        attributeMap.forEach((key, values) -> element.attr(key, String.join(", ", values)));
        return (T) this;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final T addAttributes(Pair<String, String>... pairs) {
        this.attributes.addAll(List.of(pairs));
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T id(@NotNull String id) {
        this.id = "ID" + id.replace("-", "");
        return (T) this;
    }

    public String host() {
        return URI.create(context.request().absoluteURI()).getAuthority();
    }

    public Element fullRender() {
        Element element = rawRender();
        addAttributesToElement(element);
        registerAttributes(element).registerExtensions(page);
        classes.forEach(element::addClass);
        if (id != null) element.id(id);
        attributes.forEach(stringStringPair -> element.attr(stringStringPair.first(), stringStringPair.second()));
        return element;
    }

    public List<String> classes() {
        return classes;
    }

    public String id() {
        return id;
    }

    @SuppressWarnings("unchecked")
    public T clazz(String clazz) {
        this.classes.add(clazz);
        return (T) this;
    }

    public static enum ScrollDestination {
        TOP, BOTTOM, UP, DOWN, LEFT, RIGHT
    }

    // Enum for HTMX trigger attribute values
    public enum HTMXTrigger {
        CLICK("click"),
        MOUSEOVER("mouseover"),
        KEYDOWN("keydown"),
        VALUE("value"),
        LOAD("load"),
        SCROLL("scroll");

        private final String value;

        HTMXTrigger(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Enum for request-sending attributes
    private enum RequestSendingAttribute {
        GET("hx-get"),
        POST("hx-post"),
        DELETE("hx-delete"),
        PATCH("hx-patch"),
        PUT("hx-put"),
        CONFIRM("hx-confirm"),
        TARGET("hx-target"),
        BOOST("hx-boost"),
        ON("hx-on"),
        PUSH_URL("hx-push-url"),
        SELECT("hx-select"),
        SELECT_OOB("hx-select-oob"),
        SWAP("hx-swap"),
        SWAP_O("hx-swap-o"),
        SWAP_I("hx-swap-i"),
        SWAP_SCRIPT("hx-swap-script"),
        VALS("hx-vals"),
        DISABLE("hx-disable"),
        DISINHERIT("hx-disinherit"),
        ENCODING("hx-encoding"),
        EXT("hx-ext"),
        HEADERS("hx-headers"),
        HISTORY("hx-history"),
        HISTORY_ELT("hx-history-elt"),
        INCLUDE("hx-include"),
        INDICATOR("hx-indicator"),
        PARAMS("hx-params"),
        PRESERVE("hx-preserve"),
        PROMPT("hx-prompt"),
        REPLACE_URL("hx-replace-url"),
        REQUEST("hx-request"),
        SSE("hx-sse"),
        SYNC("hx-sync"),
        VALIDATE("hx-validate"),
        VARS("hx-vars"),
        WS("hx-ws");

        private final String attributeName;

        RequestSendingAttribute(String attributeName) {
            this.attributeName = attributeName;
        }

        public static boolean isRequestSendingAttribute(String attributeName) {
            for (RequestSendingAttribute attribute : values()) {
                if (attribute.attributeName.equals(attributeName)) {
                    return true;
                }
            }
            return false;
        }
    }
}
