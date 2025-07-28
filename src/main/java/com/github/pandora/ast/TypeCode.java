package com.github.pandora.ast;

import java.util.EnumSet;

public enum TypeCode {

    VOID(Void.TYPE, Void.class),

    OBJECT(Object.class),

    STRING(String.class),

    BOOLEAN(Boolean.TYPE, Boolean.class),

    BYTE(Byte.TYPE, Byte.class),

    CHAR(Character.TYPE, Character.class),

    SHORT(Short.TYPE, Short.class),

    INT(Integer.TYPE, Integer.class),

    LONG(Long.TYPE, Long.class),

    FLOAT(Float.TYPE, Float.class),

    DOUBLE(Double.TYPE, Double.class),
    ;


    private final Class<?>[] type;


    TypeCode(Class<?> ...type) {
        this.type = type;
    }


    public Class<?>[] getType() {
        return this.type;
    }


    public static TypeCode forClass(Class<?> clazz) {
        TypeCode[] allValues = TypeCode.values();
        for (TypeCode typeCode : allValues) {
            for (Class<?> type : typeCode.getType()) {
                if (clazz == type) {
                    return typeCode;
                }
            }
        }
        return OBJECT;
    }

    public static boolean isPrimitive(Class<?> clazz) {
        TypeCode typeCode = forClass(clazz);
        Class<?>[] klass = typeCode.getType();
        return klass.length > 1 && clazz == klass[0];
    }

    private final static EnumSet<TypeCode> InvalidRelationalOp = EnumSet.of(OBJECT, STRING, VOID);

    private final static EnumSet<TypeCode> InvalidArithmeticOp = EnumSet.of(OBJECT, STRING, VOID, BOOLEAN);

    public static boolean isInvalidRelationalOp(TypeCode typeCode) {
        return InvalidRelationalOp.contains(typeCode);
    }

    public static boolean isInvalidArithmeticOp(TypeCode typeCode) {
        return InvalidArithmeticOp.contains(typeCode);
    }
}
