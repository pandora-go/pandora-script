package com.github.pandora.utils;

import com.github.pandora.exception.CompileException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

public class ClassLoaderUtils {

    private final static Method defineClass0;

    static
    {
        try
        {
            Method field = ClassLoader.class.getDeclaredMethod("defineClass0",
                    String.class,byte[].class, int.class, int.class, ProtectionDomain.class);
            field.setAccessible(true);
            defineClass0 = field;
        } catch (Exception e){
            throw new CompileException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> defineClass(ClassLoader cl, String name, byte[] newContent){
        try {
            return (Class<T>) defineClass0.invoke(cl, name, newContent, 0, newContent.length,
                    cl.getClass().getProtectionDomain());
        }catch (Exception e){
            throw new CompileException(e);
        }
    }

    public static void saveClassFile(String fileName, byte[] newContent) throws IOException{
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.write(newContent);
        fileOutputStream.close();
    }

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoaderUtils.class.getClassLoader();
            if (null == classLoader) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
