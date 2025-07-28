package com.github.pandora.utils;

import com.github.pandora.exception.AssertException;
import com.github.pandora.function.FunctionRegister;

public class MiscUtils {

    @FunctionRegister("assert")
    public static void assertTrue(boolean flag) {
        if (!flag) {
            throw new AssertException();
        }
    }

    @FunctionRegister("assert_msg")
    public static void assertTrue(boolean flag, String msg) {
        if (!flag) {
            throw new AssertException(msg);
        }
    }

    @FunctionRegister("print")
    public static void print(String msg) {
        System.out.println(msg);
    }
}
