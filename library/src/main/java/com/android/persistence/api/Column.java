package com.android.persistence.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    String name();
    boolean autoIncrement() default false;
    boolean primaryKey() default false;
    boolean notNull() default true;
    boolean uniqueKey() default false;
    Type type() default Type.TEXT;

}
