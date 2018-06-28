package com.geeyao.common.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Retry {

    Class<? extends Exception>[] on();

    int times() default 1;

    boolean failInTransaction() default true;
}

