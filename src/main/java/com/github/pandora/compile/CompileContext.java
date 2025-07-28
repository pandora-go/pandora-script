package com.github.pandora.compile;

public class CompileContext {

    private Class<?> targetClass;

    public CompileContext() {
    }

    public CompileContext(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }
}
