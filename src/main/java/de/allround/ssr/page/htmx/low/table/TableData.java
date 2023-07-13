package de.allround.ssr.page.htmx.low.table;

import de.allround.ssr.page.htmx.Component;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

@RequiredArgsConstructor(staticName = "of")
public class TableData extends Component<TableData> {
    private final String content;
    private final TableDataType type;

    @Contract("_ -> new")
    public static @NotNull TableData header(String header) {
        return new TableData(header, TableDataType.HEADER);
    }

    @Contract("_ -> new")
    public static @NotNull TableData data(String data) {
        return new TableData(data, TableDataType.DATA);
    }

    public enum TableDataType {
        DATA, HEADER
    }

    @Override
    public @NotNull Element rawRender() {
        return new Element(type == TableDataType.DATA ? "td" : "tg").text(content);
    }
}
