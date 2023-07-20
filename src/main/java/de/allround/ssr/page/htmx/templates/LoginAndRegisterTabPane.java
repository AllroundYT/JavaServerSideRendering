package de.allround.ssr.page.htmx.templates;

import de.allround.ssr.page.htmx.Template;
import de.allround.ssr.page.htmx.components.container.Form;
import de.allround.ssr.page.htmx.components.custom.tab.Tab;
import de.allround.ssr.page.htmx.components.custom.tab.TabContainer;
import de.allround.ssr.page.htmx.components.input.Button;
import de.allround.ssr.util.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginAndRegisterTabPane extends Template {

    private final String loginApiEndpoint;
    private final String registerApiEndpoint;
    private HttpMethod httpMethod = HttpMethod.POST;


    @Override
    public void init() {
        add(
                Button.create().content("Login"),
                Button.create().content("Register"),
                TabContainer.create(
                        Tab.create(
                                Form.create(loginApiEndpoint, httpMethod).add(

                                )
                        ).id("login-tab"),
                        Tab.create(
                                Form.create(registerApiEndpoint, httpMethod).add(

                                )
                        ).id("register-tab")
                ).id("login-and-register-tab-container")
        );
    }

    /*
    LOGIN
    Headline.create("Login").type(3),
    EmailInput.create().name("login-email").placeholder("Email").id("login-email"),
    LineBreak.create(),
    PasswordInput.create().name("login-password").placeholder("Password").id("login-password"),
    LineBreak.create(),
    SubmitButton.create().id("login-submit")

    REGISTER
    Headline.create("Register").type(3),
    EmailInput.create().name("register-email").placeholder("Email").id("register-email"),
    LineBreak.create(),
    TextInput.create().name("register-username").placeholder("Username").id("register-username"),
    LineBreak.create(),
    PasswordInput.create().name("register-password-1").placeholder("Password").id("register-password-1"),
    LineBreak.create(),
    PasswordInput.create().name("register-password-2").placeholder("Password again").id("register-password-2"),
    LineBreak.create(),
    SubmitButton.create().id("register-submit")
    */
}
