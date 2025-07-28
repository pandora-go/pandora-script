package com.github.pandora.ast;

import com.github.pandora.exception.ParseException;
import net.sf.cglib.core.CodeEmitter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

import static com.github.pandora.ast.TypeCode.*;

public abstract class RelationalOpExpr extends OpExpr {

    public RelationalOpExpr(BaseScriptNode[] children, int endPos, int startPos, String operator) {
        super(children, endPos, startPos, operator);

        typeCode = TypeCode.BOOLEAN;
    }

    protected void generateCode4Op(GenerateContext context, int leftOp, int rightOp) {

        Expression left = getLeftOperand();
        Expression right = getRightOperand();

        TypeCode targetType;
        CodeEmitter mv = context.getMethodVisitor();

        if (TypeCode.isInvalidRelationalOp(left.typeCode)
                || TypeCode.isInvalidRelationalOp(right.typeCode)) {
            throw new ParseException("unsupported data type: " + left.typeCode + " " + right.typeCode);
        }

        targetType = VOID;
        // to support big decimal
        if (left.typeCode == DOUBLE || right.typeCode == DOUBLE) {
            targetType = DOUBLE;
        } else if (left.typeCode == FLOAT || right.typeCode == FLOAT) {
            targetType = FLOAT;
            // to support big int
        } else if (left.typeCode == LONG || right.typeCode == LONG) {
            targetType = LONG;
        } else if (left.typeCode == INT || right.typeCode == INT) {
            targetType = INT;
        } else if (left.typeCode == BOOLEAN || right.typeCode == BOOLEAN) {
            targetType = INT;
            // to support string
        }

        left.generateCode(context);

        if (!left.primitive) {
            context.unbox(left.typeCode);
        }
        context.typeConvert(targetType, left.typeCode);

        right.generateCode(context);

        if (!right.primitive) {
            context.unbox(right.typeCode);
        }
        context.typeConvert(targetType, right.typeCode);

        Label target = new Label();
        Label end = new Label();

        if (targetType == DOUBLE) {
            mv.visitInsn(Opcodes.DCMPG);
            mv.visitJumpInsn(leftOp, target);
        } else if (targetType == FLOAT) {
            mv.visitInsn(Opcodes.FCMPG);
            mv.visitJumpInsn(leftOp, target);
        } else if (targetType == LONG) {
            mv.visitInsn(Opcodes.LCMP);
            mv.visitJumpInsn(leftOp, target);
        } else {
            mv.visitJumpInsn(rightOp, target);
        }

        mv.push(1);
        mv.goTo(end);
        mv.mark(target);
        mv.push(0);
        mv.mark(end);
    }
}
