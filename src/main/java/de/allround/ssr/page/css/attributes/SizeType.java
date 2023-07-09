package de.allround.ssr.page.css.attributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SizeType {
    PERCENT("%"),
    CM("cm"),
    EM("em"),
    PX("px");

    public final String symbol;
}
