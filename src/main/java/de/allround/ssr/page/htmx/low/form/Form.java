package de.allround.ssr.page.htmx.low.form;

import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.low.container.Container;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class Form extends Container<Form, Form.FormInputComponent<?>> {

    private final String action;

    protected Form(String action) {
        super(new ArrayList<>(), "form");
        this.action = action;
    }

    public static Form create(String action) {
        return new Form(action);
    }

    @Override
    public @NotNull Element rawRender() {
        return super.rawRender().attr("action", action);
    }

    @RequiredArgsConstructor
    @Getter
    @Setter
    @Accessors(fluent = true)
    public static abstract class FormInputComponent<T extends FormInputComponent<?>> extends Component<T> {
        private final String type;
        private boolean checked, disabled, readonly, required;
        private Integer max, min, step, maxlength;
        private String value, name, pattern;

        @Override
        public @NotNull Element rawRender() {
            Element element = new Element("input").attr("type", type);
            if (checked) element.attr("checked");
            if (disabled) element.attr("disabled", "");
            if (readonly) element.attr("readonly", "");
            if (required) element.attr("required", "");
            if (max != null) element.attr("max", max.toString());
            if (min != null) element.attr("min", min.toString());
            if (step != null) element.attr("step", step.toString());
            if (maxlength != null) element.attr("maxlength", maxlength.toString());
            if (value != null) element.attr("value", value);
            if (name != null) element.attr("name", name);
            if (pattern != null) element.attr("pattern", pattern);
            return render(element);
        }

        public Element render(Element element) {
            return element;
        }
    }
}
