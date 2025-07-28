package com.github.pandora.utils;


import com.github.pandora.function.FunctionRegister;

public class StringUtils {

    public static String EMPTY = "";

    @FunctionRegister("isBlank")
    public static boolean isBlank(String cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
