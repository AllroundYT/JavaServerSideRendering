package de.allround.ssr.page.htmx.util;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class CSSSize {
    private double value;
    private SizeUnit sizeUnit;

    public CSSSize(double value, SizeUnit sizeUnit) {
        this.value = value;
        this.sizeUnit = sizeUnit;
    }

    @Override
    public String toString() {
        return value + sizeUnit.symbol();
    }
}
