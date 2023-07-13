package de.allround.ssr.page.htmx.low.table;

import de.allround.ssr.page.htmx.low.container.Container;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Table extends Container<Table, TableRow> {

    private Table() {
        super(new ArrayList<>(), "table");
    }

    public static @NotNull Table create() {
        return new Table();
    }

    public Table addRows(List<TableRow> rows) {
        this.components.addAll(rows);
        return this;
    }

    public Table addRows(TableRow... rows) {
        return addRows(List.of(rows));
    }
}
