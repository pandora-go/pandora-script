package com.github.pandora.ast;

import com.github.pandora.exception.ParseException;

import static com.github.pandora.ast.TypeCode.VOID;

public class ArithmeticOpExpr extends OpExpr {

    public ArithmeticOpExpr(BaseScriptNode[] children, int endPos, int startPos, String operator) {
        super(children, endPos, startPos, operator);

        Expression left = getLeftOperand();
        Expression right = getRightOperand();

        if (TypeCode.isInvalidArithmeticOp(left.typeCode)
                || TypeCode.isInvalidArithmeticOp(right.typeCode)) {
            throw new ParseException("unsupported data type: " + left.typeCode + " " + right.typeCode);
        }

        typeCode = VOID;
        // to support big decimal
        if (left.typeCode == TypeCode.DOUBLE || right.typeCode == TypeCode.DOUBLE) {
            typeCode = TypeCode.DOUBLE;
        } else if (left.typeCode == TypeCode.FLOAT || right.typeCode == TypeCode.FLOAT) {
            typeCode = TypeCode.FLOAT;
            // to support big int
        } else if (left.typeCode == TypeCode.LONG || right.typeCode == TypeCode.LONG) {
            typeCode = TypeCode.LONG;
        } else if (left.typeCode == TypeCode.INT || right.typeCode == TypeCode.INT) {
            typeCode = TypeCode.INT;
            // to support string
        }
    }

    protected void generateCode4Op(GenerateContext context) {
        Expression left = getLeftOperand();
        left.generateCode(context);

        if (!left.primitive) {
            context.unbox(left.typeCode);
        }

        context.typeConvert(typeCode, left.typeCode);

        Expression right = getRightOperand();
        right.generateCode(context);

        if (!right.primitive) {
            context.unbox(right.typeCode);
        }

        context.typeConvert(typeCode, right.typeCode);
    }
}
