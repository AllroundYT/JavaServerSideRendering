package de.allround.ssr.page.htmx.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public enum DisplayType {
    NONE("none"),
    BLOCK("block"),
    INLINE_BLOCK("inline-block"),
    FLEX("flex"),
    FLEX_BOX("flex-box");

    private final String value;
}
