package net.toften.prop4j;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PropertySet {

    String propertySet() default "";

    String header() default "";
}