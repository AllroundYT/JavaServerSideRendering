package de.allround.ssr.page.htmx;

import de.allround.ssr.page.WebPage;
import de.allround.ssr.page.htmx.util.PreloadTrigger;
import org.jsoup.nodes.Element;

import java.util.*;

public class HtmxBuilder {

    private final Map<String, String> extensions = new HashMap<>();
    private final Map<String, Set<String>> attributeMap = new HashMap<>();

    private final Set<String> ignoredExtensions = new HashSet<>(List.of("method-override", "json-enc"));

    public HtmxBuilder ignoreExtension(String extension) {
        ignoredExtensions.add(extension);
        return this;
    }
    //TODO: DEFAULT

    public HtmxBuilder timeout(int milliseconds) {
        addAttribute("hx-timeout", Integer.toString(milliseconds));
        return this;
    }

    public HtmxBuilder preserve(String element) {
        addAttribute("hx-preserve", element);
        return this;
    }

    public HtmxBuilder onEvent(String event, String script) {
        addAttribute("hx-" + event, script);
        return this;
    }

    public HtmxBuilder preventDefault(boolean prevent) {
        addAttribute("hx-prevent", prevent ? "true" : "false");
        return this;
    }

    public HtmxBuilder swapAnimation(String animation) {
        addAttribute("hx-swap-oob", animation);
        return this;
    }

    public HtmxBuilder scroll(String target) {
        addAttribute("hx-scroll", target);
        return this;
    }

    public HtmxBuilder scrollAnimation(String animation) {
        addAttribute("hx-scroll-anim", animation);
        return this;
    }

    public HtmxBuilder scrollDuration(int duration) {
        addAttribute("hx-scroll-duration", Integer.toString(duration));
        return this;
    }

    public HtmxBuilder throttle(int milliseconds) {
        addAttribute("hx-throttle", Integer.toString(milliseconds));
        return this;
    }

    public HtmxBuilder debounce(int milliseconds) {
        addAttribute("hx-debounce", Integer.toString(milliseconds));
        return this;
    }

    public HtmxBuilder once(boolean enabled) {
        addAttribute("hx-once", enabled ? "true" : "false");
        return this;
    }

    public HtmxBuilder pushUrl(String url) {
        addAttribute("hx-push-url", url);
        return this;
    }

    public HtmxBuilder swapStyle(String style) {
        addAttribute("hx-swap-style", style);
        return this;
    }

    public HtmxBuilder transition(String transition) {
        addAttribute("hx-transition", transition);
        return this;
    }

    public HtmxBuilder preserveScroll(String element) {
        addAttribute("hx-preserve-scroll", element);
        return this;
    }

    public HtmxBuilder fragment(String fragment) {
        addAttribute("hx-fragment", fragment);
        return this;
    }

    // Additional HTMX Attributes
    public HtmxBuilder animation(String animation) {
        addAttribute("hx-animation", animation);
        return this;
    }

    public HtmxBuilder animateDelay(int milliseconds) {
        addAttribute("hx-animate-delay", Integer.toString(milliseconds));
        return this;
    }

    public HtmxBuilder animateDuration(int milliseconds) {
        addAttribute("hx-animate-duration", Integer.toString(milliseconds));
        return this;
    }

    public HtmxBuilder animateFrom(String fromValue) {
        addAttribute("hx-animate-from", fromValue);
        return this;
    }

    public HtmxBuilder animateTo(String toValue) {
        addAttribute("hx-animate-to", toValue);
        return this;
    }

    public HtmxBuilder animateKeyframes(String keyframes) {
        addAttribute("hx-animate-keyframes", keyframes);
        return this;
    }

    public HtmxBuilder onSwapStart(String script) {
        addAttribute("hx-swap-oob-start", script);
        return this;
    }

    public HtmxBuilder onSwapEnd(String script) {
        addAttribute("hx-swap-oob-end", script);
        return this;
    }

    public HtmxBuilder onSwapCanceled(String script) {
        addAttribute("hx-swap-oob-canceled", script);
        return this;
    }

    public HtmxBuilder swapQueueName(String queueName) {
        addAttribute("hx-swap-oob-queue", queueName);
        return this;
    }

    public HtmxBuilder swapQueueInterval(int milliseconds) {
        addAttribute("hx-swap-oob-interval", Integer.toString(milliseconds));
        return this;
    }

    public HtmxBuilder withCredentials(boolean withCredentials) {
        addAttribute("hx-with-credentials", withCredentials ? "true" : "false");
        return this;
    }

    public HtmxBuilder ignoreDuplicates(boolean ignoreDuplicates) {
        addAttribute("hx-ignore-duplicates", ignoreDuplicates ? "true" : "false");
        return this;
    }

    public HtmxBuilder injectJavaScript(String script) {
        addAttribute("hx-inject", script);
        return this;
    }

    public HtmxBuilder injectCSS(String css) {
        addAttribute("hx-inject-css", css);
        return this;
    }

    public HtmxBuilder injectHTML(String html) {
        addAttribute("hx-inject-html", html);
        return this;
    }

    public HtmxBuilder validate(boolean validate) {
        addAttribute("hx-validate", validate ? "true" : "false");
        return this;
    }

    public HtmxBuilder clearValidation(boolean clearValidation) {
        addAttribute("hx-clear-previous-errors", clearValidation ? "true" : "false");
        return this;
    }

    public HtmxBuilder validationErrorClass(String className) {
        addAttribute("hx-validation-error-class", className);
        return this;
    }

    public HtmxBuilder validationMessageTarget(String target) {
        addAttribute("hx-validation-message-target", target);
        return this;
    }

    public HtmxBuilder validationMessageAnimation(String animation) {
        addAttribute("hx-validation-message-animation", animation);
        return this;
    }

    public HtmxBuilder validationMessageDuration(int milliseconds) {
        addAttribute("hx-validation-message-duration", Integer.toString(milliseconds));
        return this;
    }

    public HtmxBuilder validationMessageEnterAnimation(String animation) {
        addAttribute("hx-validation-message-enter-animation", animation);
        return this;
    }

    public HtmxBuilder validationMessageExitAnimation(String animation) {
        addAttribute("hx-validation-message-exit-animation", animation);
        return this;
    }

    public HtmxBuilder scrollMarginTop(int pixels) {
        addAttribute("hx-scroll-margin-top", Integer.toString(pixels));
        return this;
    }

    public HtmxBuilder scrollMarginRight(int pixels) {
        addAttribute("hx-scroll-margin-right", Integer.toString(pixels));
        return this;
    }

    public HtmxBuilder scrollMarginBottom(int pixels) {
        addAttribute("hx-scroll-margin-bottom", Integer.toString(pixels));
        return this;
    }

    public HtmxBuilder scrollMarginLeft(int pixels) {
        addAttribute("hx-scroll-margin-left", Integer.toString(pixels));
        return this;
    }

    public HtmxBuilder scrollBehavior(String behavior) {
        addAttribute("hx-scroll-behavior", behavior);
        return this;
    }

    // RESTORED
    public HtmxBuilder restored() {
        extensions.put("restored", "restored.js");
        addAttribute("hx-trigger", "restored");
        return this;
    }

    //SSE
    public HtmxBuilder sseConnect(String uri) {
        extensions.put("sse", "sse.js");
        addAttribute("sse-connect", uri);
        return this;
    }

    public HtmxBuilder sseSwap(String eventName) {
        extensions.put("sse", "sse.js");
        addAttribute("sse-swap", eventName);
        return this;
    }

    public HtmxBuilder sseTrigger(String eventName) {
        extensions.put("sse", "sse.js");
        addAttribute("hx-trigger", "sse:" + eventName);
        return this;
    }

    //TODO: RESPONSE-TARGETS

    //REMOVE-ME
    public HtmxBuilder removeMe(int millis) {
        extensions.put("remove-me", "remove-me.js");
        addAttribute("remove-me", millis + "ms");
        return this;
    }

    //PRELOAD
    public HtmxBuilder preload(String trigger) {
        extensions.put("preload", "preload.js");
        if (trigger == null) trigger = PreloadTrigger.MOUSE_DOWN.getValue();
        addAttribute("preload", trigger);
        return this;
    }

    //PATH-DEPS
    public HtmxBuilder pathDeps(String path) {
        extensions.put("path-deps", "path-deps.js");
        addAttribute("hx-trigger", "path-deps").addAttribute("path-deps", path);
        return this;
    }

    //MULTI-SWAP
    public HtmxBuilder multiSwap(String... targets) {
        extensions.put("multi-swap", "multi-swap.js");
        addAttribute("hx-swap", "multi:" + String.join(",", List.of(targets)));
        return this;
    }

    //MORPHDOM-SWAP
    public HtmxBuilder morphdomSwap() {
        extensions.put("morphdom-swap", "morphdom-swap.js");
        addAttribute("hx-swap", "morphdom");
        return this;
    }

    //METHOD-OVERRIDE
    public HtmxBuilder methodOverride() {
        extensions.put("method-override", "method-override.js");
        ignoredExtensions.remove("method-override");
        return this;
    }

    //JSON-ENC
    public HtmxBuilder jsonEnc() {
        extensions.put("json-enc", "json-enc.js");
        ignoredExtensions.remove("json-enc");
        return this;
    }

    //INCLUDE-VALS
    public HtmxBuilder includeVals(String value) {
        extensions.put("include-vals", "include-vals.js");
        addAttribute("include-vals", value);
        return this;
    }

    public HtmxBuilder addAttribute(String key, String... values) {
        attributeMap.compute(key, (attributeName, attributeValues) -> {
            if (attributeValues == null) attributeValues = new HashSet<>();
            attributeValues.addAll(List.of(values));
            return attributeValues;
        });
        return this;
    }

    public HtmxBuilder registerExtensions(WebPage webPage) {
        extensions.forEach((name, uri) -> {
            webPage.extensions().add(uri);
            webPage.extensionNames().add(name);
        });
        return this;
    }

    public HtmxBuilder registerAttributes(Element element) {
        addAttribute("hx-ext", (String[]) ignoredExtensions.stream().map(s -> "ignored:" + s).toArray());
        attributeMap.forEach((key, values) -> element.attr(key, String.join(", ", values)));
        return this;
    }
}
