package de.allround.ssr.page.htmx.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public enum SizeUnit {
    PERCENT("%"),
    PIXEL("px"),
    CENTIMETER("cm");
    private final String symbol;
}
