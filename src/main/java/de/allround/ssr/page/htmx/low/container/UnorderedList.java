package de.allround.ssr.page.htmx.low.container;

import de.allround.ssr.page.htmx.Component;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class UnorderedList extends Component<UnorderedList> {

    private final List<ListItem> listItems = new ArrayList<>();

    public UnorderedList addItems(ListItem... items) {
        this.listItems.addAll(List.of(items));
        return this;
    }

    public UnorderedList addStrings(String... items) {
        this.listItems.addAll(Stream.of(items).map(ListItem::of).toList());
        return this;
    }

    public UnorderedList addItems(List<ListItem> items) {
        this.listItems.addAll(items);
        return this;
    }

    public UnorderedList addStrings(@NotNull List<String> strings) {
        this.listItems.addAll(strings.stream().map(ListItem::of).toList());
        return this;
    }

    @Override
    public @NotNull Element rawRender() {
        return new Element("ul").appendChildren(listItems.stream().map(Component::fullRender).toList());
    }


}
