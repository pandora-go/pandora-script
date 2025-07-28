package com.github.pandora.function;

import java.lang.reflect.Method;

public class FunctionCall {

    private final Method method;

    public FunctionCall(Method method) {
        this.method = method;
    }

    public Class<?> getKlass() {
        return method.getDeclaringClass();
    }

    public Method getMethod() {
        return method;
    }

    public Class<?>[] getArgsType() {
        return method.getParameterTypes();
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }
}
