package com.github.pandora.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ReflectionUtils {

    public static List<Method> findAnnotatedMethods(Class<?> klass, Class<? extends Annotation> annotation) {
        return findFilteredMethods(klass, method -> method.isAnnotationPresent(annotation));
    }

    public static List<Method> findFilteredMethods(Class<?> klass, Predicate<Method> predict) {
        List<Method> targetMethods = new ArrayList<>();
        targetMethods.addAll(Arrays.asList(klass.getDeclaredMethods()));
        if (klass.isInterface()) {
            for (Class<?> target : klass.getInterfaces()) {
                targetMethods.addAll(findFilteredMethods(target, predict));
            }
        } else {
            targetMethods.addAll(findConcreteMethodsOnInterfaces(klass));
            if (klass.getSuperclass() != Object.class) {
                targetMethods.addAll(findFilteredMethods(klass.getSuperclass(), predict));
            }
        }
        return targetMethods.stream()
                .filter(predict)
                .peek(method -> method.setAccessible(true))
                .collect(Collectors.toList());
    }

    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        List<Method> result = new ArrayList<>();
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method ifcMethod : ifc.getMethods()) {
                if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
                    result.add(ifcMethod);
                }
            }
        }
        return result;
    }

}
