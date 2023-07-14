package de.allround.ssr.page.htmx.low.form;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;


@Getter
@Accessors(fluent = true)
@Setter

public class TextInput extends Form.FormInputComponent<TextInput> {

    @Contract(" -> new")
    public static @NotNull TextInput create() {
        return new TextInput();
    }

    @Contract("_ -> new")
    public static @NotNull TextInput create(String placeholder) {
        return new TextInput(placeholder);
    }

    private String placeholder = "Password";

    protected TextInput() {
        super("password");
    }

    protected TextInput(String placeholder) {
        super("password");
        this.placeholder = placeholder;
    }


    @Override
    public @NotNull Element render(@NotNull Element element) {
        return element.attr("placeholder", placeholder);
    }
}
