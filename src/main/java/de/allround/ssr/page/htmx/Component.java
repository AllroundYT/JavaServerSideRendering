package de.allround.ssr.page.htmx;

import de.allround.ssr.WebApplication;
import de.allround.ssr.annotations.Injected;
import de.allround.ssr.injection.InjectionUtil;
import de.allround.ssr.page.css.Style;
import de.allround.ssr.page.css.StyleLibrary;
import de.allround.ssr.util.Pair;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.util.*;

@Getter
@Accessors(fluent = true)
@Setter
public abstract class Component<T extends Component<?>> {
    private final List<Style> styles = new ArrayList<>();
    private final List<Pair<String, String>> attributes = new ArrayList<>();
    private final List<String> classes = new ArrayList<>();
    private final Map<String, String> hxAttributes = new HashMap<>();
    private final EnumSet<RequestSendingAttribute> requestSendingAttributes = EnumSet.noneOf(RequestSendingAttribute.class);
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected WebApplication webApplication;
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
    @Injected
    @Setter(AccessLevel.PRIVATE)
    protected Vertx vertx;
    private String id = "ID" + UUID.randomUUID().toString().replace("-", "");

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

    // Methods for HTMX attributes

    /**
     * Adds all set HTMX attributes to a Jsoup element.
     *
     * @param element the Jsoup element to add the HTMX attributes to
     */
    public void addAttributesToElement(Element element) {
        for (Map.Entry<String, String> entry : hxAttributes.entrySet()) {
            String attributeName = entry.getKey();
            String attributeValue = entry.getValue();
            element.attr(attributeName, attributeValue);
        }
    }

    /**
     * Sets the HTMX trigger attribute.
     *
     * @param triggerValue the value of the trigger attribute
     * @return the T instance
     */
    public T trigger(T.HTMXTrigger triggerValue) {
        return setAttribute("hx-trigger", triggerValue.getValue());
    }

    /**
     * Sets the HTMX get attribute.
     *
     * @param getURL the URL for the get request
     * @return the T instance
     */
    public T get(String getURL) {
        return setAttribute("hx-get", getURL);
    }

    /**
     * Sets the HTMX post attribute.
     *
     * @param postURL the URL for the post request
     * @return the T instance
     */
    public T post(String postURL) {
        return setAttribute("hx-post", postURL);
    }

    /**
     * Sets the HTMX delete attribute.
     *
     * @param deleteURL the URL for the delete request
     * @return the T instance
     */
    public T delete(String deleteURL) {
        return setAttribute("hx-delete", deleteURL);
    }

    /**
     * Sets the HTMX patch attribute.
     *
     * @param patchURL the URL for the patch request
     * @return the T instance
     */
    public T patch(String patchURL) {
        return setAttribute("hx-patch", patchURL);
    }

    /**
     * Sets the HTMX put attribute.
     *
     * @param putURL the URL for the put request
     * @return the T instance
     */
    public T put(String putURL) {
        return setAttribute("hx-put", putURL);
    }

    /**
     * Sets the HTMX swap attribute.
     *
     * @param swapSelector the selector for the element to swap
     * @return the T instance
     */
    public T swap(String swapSelector) {
        return setAttribute("hx-swap", swapSelector);
    }

    /**
     * Sets the HTMX swap attribute.
     *
     * @param swapSelector the selector for the element to swap
     * @return the T instance
     */
    public T swapOOB(String swapSelector) {
        return setAttribute("hx-swap-oob", swapSelector);
    }

    /**
     * Sets the HTMX swap-o attribute.
     *
     * @param swapSelector the selector for the outer element to swap
     * @return the T instance
     */
    public T swapOuter(String swapSelector) {
        return setAttribute("hx-swap-o", swapSelector);
    }

    /**
     * Sets the HTMX swap-i attribute.
     *
     * @param swapSelector the selector for the inner element to swap
     * @return the T instance
     */
    public T swapInner(String swapSelector) {
        return setAttribute("hx-swap-i", swapSelector);
    }

    /**
     * Sets the HTMX swap-script attribute.
     *
     * @param script the script to execute after swapping
     * @return the T instance
     */
    public T swapScript(String script) {
        return setAttribute("hx-swap-script", script);
    }

    /**
     * Sets the HTMX push-url attribute.
     *
     * @param url the URL to push to the history stack
     * @return the T instance
     */
    public T pushUrl(String url) {
        return setAttribute("hx-push-url", url);
    }

    /**
     * Sets the HTMX select attribute.
     *
     * @param selector the selector for the element to select
     * @return the T instance
     */
    public T select(String selector) {
        return setAttribute("hx-select", selector);
    }

    /**
     * Sets the HTMX select-oob attribute.
     *
     * @param selector the selector for the out-of-band element to select
     * @return the T instance
     */
    public T selectOOB(String selector) {
        return setAttribute("hx-select-oob", selector);
    }

    /**
     * Sets the HTMX boost attribute.
     *
     * @param boostDelay the delay in milliseconds before boosting
     * @return the T instance
     */
    public T boost(int boostDelay) {
        return setAttribute("hx-boost", String.valueOf(boostDelay));
    }

    public T timeout(int timeout) {
        return setAttribute("hx-timeout", String.valueOf(timeout));
    }

    /**
     * Sets the HTMX on attribute.
     *
     * @param eventName the name of the event to trigger
     * @return the T instance
     */
    public T on(String eventName) {
        return setAttribute("hx-on", eventName);
    }

    /**
     * Sets the HTMX confirm attribute.
     *
     * @param confirmMessage the confirmation message
     * @return the T instance
     */
    public T confirm(String confirmMessage) {
        return setAttribute("hx-confirm", confirmMessage);
    }

    /**
     * Sets the HTMX target attribute.
     *
     * @param targetSelector the selector for the target element
     * @return the T instance
     */
    public T target(String targetSelector) {
        return setAttribute("hx-target", targetSelector);
    }

    /**
     * Sets the HTMX vals attribute.
     *
     * @param vals the values to include in the request
     * @return the T instance
     */
    public T vals(String vals) {
        return setAttribute("hx-vals", vals);
    }

    /**
     * Sets the HTMX disable attribute.
     *
     * @param disableSelector the selector for the element to disable
     * @return the T instance
     */
    public T disable(String disableSelector) {
        return setAttribute("hx-disable", disableSelector);
    }

    /**
     * Sets the HTMX disinherit attribute.
     *
     * @param disinheritSelector the selector for the element to disinherit
     * @return the T instance
     */
    public T disinherit(String disinheritSelector) {
        return setAttribute("hx-disinherit", disinheritSelector);
    }

    /**
     * Sets the HTMX encoding attribute.
     *
     * @param encoding the encoding type
     * @return the T instance
     */
    public T encoding(String encoding) {
        return setAttribute("hx-encoding", encoding);
    }

    /**
     * Sets the HTMX ext attribute.
     *
     * @param ext the file extension
     * @return the T instance
     */
    public T ext(String ext) {
        return setAttribute("hx-ext", ext);
    }

    /**
     * Sets the HTMX headers attribute.
     *
     * @param headers the headers to include in the request
     * @return the T instance
     */
    public T headers(String headers) {
        return setAttribute("hx-headers", headers);
    }

    /**
     * Sets the HTMX history attribute.
     *
     * @param historyValue the history value
     * @return the T instance
     */
    public T history(String historyValue) {
        return setAttribute("hx-history", historyValue);
    }

    /**
     * Sets the HTMX history-elt attribute.
     *
     * @param url the URL to replace in the history stack
     * @return the T instance
     */
    public T historyElement(String url) {
        return setAttribute("hx-history-elt", url);
    }

    /**
     * Sets the HTMX include attribute.
     *
     * @param includeURL the URL to include content from
     * @return the T instance
     */
    public T include(String includeURL) {
        return setAttribute("hx-include", includeURL);
    }

    /**
     * Sets the HTMX indicator attribute.
     *
     * @param indicatorSelector the selector for the loading indicator element
     * @return the T instance
     */
    public T indicator(String indicatorSelector) {
        return setAttribute("hx-indicator", indicatorSelector);
    }

    /**
     * Sets the HTMX params attribute.
     *
     * @param params the parameters to include in the request
     * @return the T instance
     */
    public T params(String params) {
        return setAttribute("hx-params", params);
    }

    /**
     * Sets the HTMX preserve attribute.
     *
     * @param preserveAttributes the attributes to preserve during an update
     * @return the T instance
     */
    public T preserve(String preserveAttributes) {
        return setAttribute("hx-preserve", preserveAttributes);
    }

    /**
     * Sets the HTMX prompt attribute.
     *
     * @param promptMessage the prompt message
     * @return the T instance
     */
    public T prompt(String promptMessage) {
        return setAttribute("hx-prompt", promptMessage);
    }

    /**
     * Sets the HTMX replace-url attribute.
     *
     * @param replaceURL the URL to replace in the browser history
     * @return the T instance
     */
    public T replaceUrl(String replaceURL) {
        return setAttribute("hx-replace-url", replaceURL);
    }

    /**
     * Sets the HTMX request attribute.
     *
     * @param requestValue the request value
     * @return the T instance
     */
    public T request(String requestValue) {
        return setAttribute("hx-request", requestValue);
    }

    /**
     * Sets the HTMX sse attribute.
     *
     * @param sseURL the URL for the Server-Sent Events (SSE)
     * @return the T instance
     */
    public T sse(String sseURL) {
        return setAttribute("hx-sse", sseURL);
    }

    /**
     * Sets the HTMX sync attribute.
     *
     * @param syncValue the sync value
     * @return the T instance
     */
    public T sync(String syncValue) {
        return setAttribute("hx-sync", syncValue);
    }

    /**
     * Sets the HTMX validate attribute.
     *
     * @param validateValue the validate value
     * @return the T instance
     */
    public T validate(String validateValue) {
        return setAttribute("hx-validate", validateValue);
    }

    /**
     * Sets the HTMX vars attribute.
     *
     * @param vars the variables to include in the request
     * @return the T instance
     */
    public T vars(String vars) {
        return setAttribute("hx-vars", vars);
    }

    /**
     * Sets the HTMX ws attribute.
     *
     * @param wsURL the URL for the WebSocket
     * @return the T instance
     */
    public T ws(String wsURL) {
        return setAttribute("hx-ws", wsURL);
    }

    private void removeConflictingAttributes(String attributeName) {
        if (T.RequestSendingAttribute.isRequestSendingAttribute(attributeName)) {
            requestSendingAttributes.clear();
        }
    }

    public abstract @NotNull Element rawRender();

    @SuppressWarnings("unchecked")
    public T style(Style style) {
        this.styles.add(style);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T loadStyle(@NotNull StyleLibrary styleLibrary) {
        this.styles.addAll(styleLibrary.getStyle(this).styles());
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T styles(Style style, Style... styles) {
        this.styles.add(style);
        this.styles.addAll(List.of(styles));
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
        return URI.create(request.absoluteURI()).getAuthority();
    }

    public Element fullRender() {
        InjectionUtil.inject(this);
        Element element = rawRender();
        addAttributesToElement(element);
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
