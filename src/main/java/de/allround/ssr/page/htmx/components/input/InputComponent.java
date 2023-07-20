package de.allround.ssr.page.htmx.components.input;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.RenderFunction;
import de.allround.ssr.page.htmx.util.FormMethod;
import de.allround.ssr.util.Data;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.function.Function;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class InputComponent<T extends Component<?>> extends Component<T> {
    private final String type;

    protected InputComponent(String type) {
        this.type = type;
    }

    public T accept(String fileExtension) {
        addAttribute("accept", fileExtension);
        return (T) this;
    }

    public T alt(String text) {
        addAttribute("alt", text);
        return (T) this;
    }

    public T autoComplete(boolean onOff) {
        addAttribute("autocomplete", onOff);
        return (T) this;
    }

    public T accept(Function<Data, String> fileExtension) {
        addAttribute("accept", fileExtension);
        return (T) this;
    }

    public T alt(Function<Data, String> text) {
        addAttribute("alt", text);
        return (T) this;
    }

    public T autoComplete(Function<Data, Boolean> onOff) {
        addAttribute("autocomplete", onOff);
        return (T) this;
    }

    public T autoFocus() {
        addAttribute("autofocus", "");
        return (T) this;
    }

    public T checked() {
        addAttribute("checked", "");
        return (T) this;
    }

    public T dirname(String dirname) {
        addAttribute("dirname", dirname);
        return (T) this;
    }

    public T dirname(Function<Data, String> dirname) {
        addAttribute("dirname", dirname);
        return (T) this;
    }

    public T disabled() {
        addAttribute("disabled", "");
        return (T) this;
    }

    public T form(String formId) {
        addAttribute("form", formId);
        return (T) this;
    }

    public T formaction(URL url) {
        addAttribute("formaction", url);
        return (T) this;
    }

    public T formenctype(String formenctype) {
        addAttribute("formenctype", formenctype);
        return (T) this;
    }

    public T formmethod(@NotNull FormMethod method) {
        addAttribute("formmethod", method.name());
        return (T) this;
    }

    public T formnovalidate(boolean formnovalidate) {
        addAttribute("formnovalidate", formnovalidate);
        return (T) this;
    }

    public T formtarget(String formtarget) {
        addAttribute("formtarget", formtarget);
        return (T) this;
    }

    public T height(int pixels) {
        addAttribute("height", pixels);
        return (T) this;
    }

    public T list(String listId) {
        addAttribute("list", listId);
        return (T) this;
    }

    public T max(int max) {
        addAttribute("max", max);
        return (T) this;
    }

    public T max(String max) {
        addAttribute("max", max);
        return (T) this;
    }

    public T maxlength(int maxlength) {
        addAttribute("maxlength", maxlength);
        return (T) this;
    }

    public T min(int min) {
        addAttribute("min", min);
        return (T) this;
    }

    public T min(String min) {
        addAttribute("min", min);
        return (T) this;
    }

    public T minlength(int minlength) {
        addAttribute("minlength", minlength);
        return (T) this;
    }

    //---------------------------
    public T form(Function<Data, String> formId) {
        addAttribute("form", formId);
        return (T) this;
    }

    public T formaction(Function<Data, URL> url) {
        addAttribute("formaction", url);
        return (T) this;
    }

    public T formenctype(Function<Data, String> formenctype) {
        addAttribute("formenctype", formenctype);
        return (T) this;
    }

    public T formmethod(@NotNull Function<Data, FormMethod> method) {
        addAttribute("formmethod", method);
        return (T) this;
    }

    public T formnovalidate(Function<Data, Boolean> formnovalidate) {
        addAttribute("formnovalidate", formnovalidate);
        return (T) this;
    }

    public T formtarget(Function<Data, String> formtarget) {
        addAttribute("formtarget", formtarget);
        return (T) this;
    }

    public T height(Function<Data, Integer> pixels) {
        addAttribute("height", pixels);
        return (T) this;
    }

    public T list(Function<Data, String> listId) {
        addAttribute("list", listId);
        return (T) this;
    }

    public T max(Function<Data, Integer> max) {
        addAttribute("max", max);
        return (T) this;
    }


    public T maxlength(Function<Data, Integer> maxlength) {
        addAttribute("maxlength", maxlength);
        return (T) this;
    }

    public T min(Function<Data, Integer> min) {
        addAttribute("min", min);
        return (T) this;
    }


    public T minlength(Function<Data, Integer> minlength) {
        addAttribute("minlength", minlength);
        return (T) this;
    }
    //---------------------------

    public T multiple() {
        addAttribute("multiple", "");
        return (T) this;
    }

    public T name(String name) {
        addAttribute("name", name);
        return (T) this;
    }

    public T pattern(@NotNull Pattern pattern) {
        addAttribute("pattern", pattern.pattern());
        return (T) this;
    }

    public T placeholder(String placeholder) {
        addAttribute("placeholder", placeholder);
        return (T) this;
    }

    public T name(Function<Data, String> name) {
        addAttribute("name", name);
        return (T) this;
    }

    public T pattern(@NotNull Function<Data, String> pattern) {
        addAttribute("pattern", pattern);
        return (T) this;
    }

    public T placeholder(Function<Data, String> placeholder) {
        addAttribute("placeholder", placeholder);
        return (T) this;
    }

    public T readonly() {
        addAttribute("readonly", "");
        return (T) this;
    }

    public T required() {
        addAttribute("required", "");
        return (T) this;
    }

    public T size(int size) {
        addAttribute("size", size);
        return (T) this;
    }

    public T src(URL src) {
        addAttribute("src", src);
        return (T) this;
    }

    public T step(int step) {
        addAttribute("step", step);
        return (T) this;
    }

    public T value(String value) {
        addAttribute("value", value);
        return (T) this;
    }

    public T width(int pixels) {
        addAttribute("width", pixels);
        return (T) this;
    }


    public T size(Function<Data, Integer> size) {
        addAttribute("size", size);
        return (T) this;
    }

    public T src(Function<Data, URL> src) {
        addAttribute("src", src);
        return (T) this;
    }

    public T step(Function<Data, Integer> step) {
        addAttribute("step", step);
        return (T) this;
    }

    public T value(Function<Data, String> value) {
        addAttribute("value", value);
        return (T) this;
    }

    public T width(Function<Data, Integer> pixels) {
        addAttribute("width", pixels);
        return (T) this;
    }

    @Override
    public RenderFunction preRender() {
        return data -> new Element("input").attr("type", type);
    }


}
