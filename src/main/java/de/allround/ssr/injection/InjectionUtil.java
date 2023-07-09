package de.allround.ssr.injection;

import de.allround.ssr.page.WebPage;
import de.allround.ssr.rest.RestAPI;
import io.vertx.ext.web.RoutingContext;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InjectionUtil {
    public void inject(Object targetObject, List<Object> objectsToInject, Object... objects) {
        if (objectsToInject == null || objectsToInject.size() == 0) return;
        List<Object> newList = new ArrayList<>(objectsToInject);
        if (objects != null) newList.addAll(List.of(objects));
        performInjection(targetObject, newList);
    }

    private void performInjection(@NotNull Object targetObject, List<Object> objectsToInject) {
        if (targetObject instanceof RestAPI restAPI) {
            for (int i = 0; i < restAPI.getClass().getSuperclass().getDeclaredFields().length; i++) {
                Field field = restAPI.getClass().getSuperclass().getDeclaredFields()[i];
                Class<?> type = field.getType();
                objectsToInject.stream().filter(type::isInstance).findFirst().ifPresent(o -> {
                    field.setAccessible(true);
                    try {
                        field.set(restAPI, o);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    field.setAccessible(false);
                });
            }
        } else if (targetObject instanceof WebPage webPage) {
            for (int i = 0; i < webPage.getClass().getSuperclass().getDeclaredFields().length; i++) {
                Field field = webPage.getClass().getSuperclass().getDeclaredFields()[i];
                Class<?> type = field.getType();
                objectsToInject.stream().filter(type::isInstance).findFirst().ifPresent(o -> {
                    field.setAccessible(true);
                    try {
                        field.set(webPage, o);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    field.setAccessible(false);
                });
            }
        }
    }

    public List<Object> contextToInjectionObjectList(RoutingContext context) {
        List<Object> list = new ArrayList<>(List.of(context));
        if (context.user() != null) list.add(context.user());
        if (context.session() != null) list.add(context.session());
        if (context.parsedHeaders() != null) list.add(context.parsedHeaders());
        if (context.body() != null) list.add(context.body());
        if (context.response() != null) list.add(context.response());
        if (context.request() != null) list.add(context.request());
        if (context.currentRoute() != null) list.add(context.currentRoute());
        return list;
    }
}
