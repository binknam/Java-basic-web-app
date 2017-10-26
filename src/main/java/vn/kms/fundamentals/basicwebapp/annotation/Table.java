//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {
    /**
     * The name of the table. If not declared, default to the entity name.
     */
    String name() default "";
}
