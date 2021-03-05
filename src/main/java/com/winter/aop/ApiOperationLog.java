package com.winter.aop;
 
import java.lang.annotation.*;
 
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiOperationLog {
    String resourceId() default "";
    String operationType();
    String description() default "";
}