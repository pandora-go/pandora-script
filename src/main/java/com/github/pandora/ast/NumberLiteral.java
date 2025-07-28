package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import com.github.pandora.exception.ParseException;

public class NumberLiteral extends Literal {

    private Number value;

    public NumberLiteral(int endPos, int startPos, String literal) {
        super(endPos, startPos, literal);

        // float and double
        if (literal.contains(".")) {
            try {
                double temp = Double.parseDouble(literal);

                value = temp;
                typeCode = TypeCode.DOUBLE;

                if (temp >= Float.MIN_VALUE && temp <= Float.MAX_VALUE) {
                    value = (float) temp;
                    typeCode = TypeCode.FLOAT;
                }
            } catch (NumberFormatException e) {
                throw new ParseException(e);
            }

            return;
        }

        // int and long
        try {
            long temp = Long.parseLong(literal);

            value = temp;
            typeCode = TypeCode.LONG;

            if (temp >= Integer.MIN_VALUE && temp <= Integer.MAX_VALUE) {
                value = (int) temp;
                typeCode = TypeCode.INT;
            }
        } catch (NumberFormatException e) {
            throw new ParseException(e);
        }
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        if (value == null) {
            throw new CompileException("value is null");
        }

        if (typeCode == TypeCode.INT) {
            context.getMethodVisitor().push(value.intValue());
        } else if (typeCode == TypeCode.LONG) {
            context.getMethodVisitor().push(value.longValue());
        } else if (typeCode == TypeCode.FLOAT) {
            context.getMethodVisitor().push(value.floatValue());
        } else if (typeCode == TypeCode.DOUBLE) {
            context.getMethodVisitor().push(value.doubleValue());
        } else {
            throw new CompileException("typeCode");
        }
    }
}
