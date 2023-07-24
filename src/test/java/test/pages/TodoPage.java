package test.pages;

import de.allround.ssr.page.WebPage;
import de.allround.ssr.page.htmx.components.container.Div;
import de.allround.ssr.page.htmx.components.custom.LazyComponent;
import de.allround.ssr.page.htmx.components.input.Button;
import de.allround.ssr.page.htmx.components.input.TextInput;
import de.allround.ssr.page.htmx.components.navigation.UnorderedList;
import de.allround.ssr.page.htmx.components.text.BreakLine;
import de.allround.ssr.page.htmx.components.text.Headline;
import de.allround.ssr.util.Data;

import java.net.URI;

public class TodoPage extends WebPage {
    @Override
    public void init(Data data) {
        add(
                Div.create(
                        Headline.create().content("Todo-List"),
                        UnorderedList.create().id("todo-list").addAttribute("styles", "padding: 0"),
                        LazyComponent.init(UnorderedList.class, URI.create("/")),
                        BreakLine.create(),
                        TextInput.create().placeholder("Write down a new todo...").name("todo-input").id("todo-input"),
                        Button.create().content("Add todo")
                                .trigger("click keydown.enter")
                                .post(URI.create("/api/v1/todo/add"))
                                .include("#todo-input")
                                .target("#todo-list")
                                .swap("beforeend")
                ).id("todo-container")
        );
    }
}
