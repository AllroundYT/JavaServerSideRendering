package de.allround.ssr.page.htmx.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PreloadTrigger {
    MOUSE_DOWN("mousedown"),
    MOUSE_OVER("mouseover"),
    PRELOAD_INIT("preload:init");

    private final String value;
}
