package com.github.pandora.function;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FunctionRegister {

    String value() default "";
}
