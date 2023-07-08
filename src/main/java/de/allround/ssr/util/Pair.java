package de.allround.ssr.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(fluent = true)
public class Pair<F, S> {
    private F first;
    private S second;
}
