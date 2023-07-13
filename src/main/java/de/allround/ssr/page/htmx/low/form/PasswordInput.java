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

public class PasswordInput extends Form.FormInputComponent<PasswordInput> {

    @Contract(" -> new")
    public static @NotNull PasswordInput create() {
        return new PasswordInput();
    }

    @Contract("_ -> new")
    public static @NotNull PasswordInput create(String placeholder) {
        return new PasswordInput(placeholder);
    }

    private String placeholder = "Password";

    public PasswordInput() {
        super("password");
    }

    public PasswordInput(String placeholder) {
        super("password");
        this.placeholder = placeholder;
    }


    @Override
    public @NotNull Element render(@NotNull Element element) {
        return element.attr("placeholder", placeholder);
    }
}
