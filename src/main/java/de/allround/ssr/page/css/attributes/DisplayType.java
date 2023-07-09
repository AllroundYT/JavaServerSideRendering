package de.allround.ssr.page.css.attributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DisplayType {
    FLEX("flex"),
    BOX("box"),
    FLEXBOX("flexbox"),
    BLOCK("block"),
    CONTENTS("contents"),
    FLOW_ROOT("flow-root"),
    GRID("grid"),
    INLINE("inline"),
    INLINE_BLOCK("inline-block"),
    INLINE_FLEX("inline-flex"),
    INLINE_FLEXBOX("inline-flexbox"),
    INLINE_TABLE("inline-table"),
    NONE("none"),
    LIST_ITEM("list-item"),
    TABLE("table");

    //TODO mehr display typen hinzufügen

    public final String cssValue;
}
