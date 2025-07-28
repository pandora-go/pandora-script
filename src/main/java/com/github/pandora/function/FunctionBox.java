package com.github.pandora.function;

import com.github.pandora.utils.ClassLoaderUtils;
import com.github.pandora.utils.MiscUtils;
import com.github.pandora.utils.ReflectionUtils;
import com.github.pandora.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FunctionBox {

    private final Map<String, FunctionCall> callMap = new HashMap<>();

    private static volatile  FunctionBox box;

    public boolean register(String name, FunctionCall fun) {
        if (callMap.containsKey(name)) {
            return false;
        }
        callMap.put(name, fun);
        return true;
    }

    public FunctionCall getFunction(String name) {
        return callMap.get(name);
    }

    private FunctionBox() {
    }

    public static FunctionBox getInstance() {
        if (box == null) {
            synchronized (FunctionBox.class) {
                if (box == null) {
                    box = new FunctionBox();
                    Stream.of(MiscUtils.class, StringUtils.class).forEach(klass -> ReflectionUtils.findAnnotatedMethods(klass, FunctionRegister.class).forEach(
                            m -> {
                                FunctionRegister register = m.getAnnotation(FunctionRegister.class);
                                box.register(register.value(), new FunctionCall(m));
                            }
                    ));
                }
            }
        }
        return box;
    }
}
