package test.rest;

import de.allround.ssr.annotations.Method;
import de.allround.ssr.annotations.Route;
import de.allround.ssr.page.htmx.components.input.Checkbox;
import de.allround.ssr.page.htmx.components.navigation.ListItem;
import de.allround.ssr.page.htmx.components.navigation.UnorderedList;
import de.allround.ssr.page.htmx.components.text.Text;
import de.allround.ssr.rest.RestAPI;
import de.allround.ssr.util.HttpMethod;
import io.vertx.core.json.JsonObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Route("/api/v1/todo")
public class TodoApi extends RestAPI {
    @Route("/add")
    @Method(HttpMethod.POST)
    public void postAddTodo() {
        String todo = data.request().getParam("todo-input");
        if (data.session().get("todos") == null) data.session().put("todos", new ArrayList<>());
        List<String> todos = data.session().get("todos");
        if (todos.contains(todo)) {
            data.response().setStatusCode(204);
            data.response().send();
            return;
        }
        todos.add(todo);

        sendResponse(todoItem(todo));
    }


    public ListItem todoItem(String todo) {
        return ListItem.create(
                Checkbox.create().trigger("click").json(JsonObject.of("todo", todo)).post(URI.create("/api/v1/todo/remove")).swap("outerHTML").target("#todo-list"),
                Text.create(todo).addAttribute("margin-left", "10px")
        ).addAttribute("style", "display: flex; flex-direction: row; margin: 0");
    }

    @Route("/remove")
    @Method(HttpMethod.POST)
    public void postRemoveTodo() {
        String todoToRemove = data.requestBody().asJsonObject().getString("todo");
        if (data.session().get("todos") == null) data.session().put("todos", new ArrayList<String>());
        List<String> todos = data.session().get("todos");
        todos.remove(todoToRemove);
        UnorderedList todoList = UnorderedList.create().id("todo-list").addAttribute("styles", "padding: 0");
        todos.forEach(todo -> todoList.add(todoItem(todo)));
        sendResponse(todoList);
    }
}
