package de.allround.ssr.annotations;


import de.allround.ssr.util.HttpMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Method {
    HttpMethod value();
}
