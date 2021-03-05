package com.winter.aop;

import com.winter.model.User;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAnnotation {

    Class<?> clazz() default User.class;

    String name() default "";
}