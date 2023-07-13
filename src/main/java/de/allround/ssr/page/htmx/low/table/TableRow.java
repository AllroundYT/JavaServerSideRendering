package de.allround.ssr.page.htmx.low.table;

import de.allround.ssr.page.htmx.low.container.Container;

import java.util.List;

public class TableRow extends Container<TableRow, TableData> {

    public static TableRow of(List<TableData> data) {
        return new TableRow(data);
    }

    public static TableRow of(TableData... data) {
        return new TableRow(List.of(data));
    }


    public TableRow addData(List<TableData> data) {
        this.components.addAll(data);
        return this;
    }

    public TableRow addData(TableData... data) {
        return addData(List.of(data));
    }

    private TableRow(List<TableData> data) {
        super(data, "tr");
    }
}
