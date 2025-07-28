package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import org.objectweb.asm.Opcodes;

import static com.github.pandora.ast.TypeCode.*;
import static com.github.pandora.ast.TypeCode.DOUBLE;

public class MinusOpExpr extends UnaryOpExpr {

    public MinusOpExpr(BaseScriptNode[] children, int endPos, int startPos, String op) {
        super(children, endPos, startPos, op);
        typeCode = getOperand().typeCode;
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        getOperand().generateCode(context);

        context.boxAndUnbox(getOperand().primitive, primitive, typeCode);

        if (typeCode == INT) {
            context.getMethodVisitor().visitInsn(Opcodes.INEG);
        } else if (typeCode == LONG) {
            context.getMethodVisitor().visitInsn(Opcodes.LNEG);
        } else if (typeCode == FLOAT) {
            context.getMethodVisitor().visitInsn(Opcodes.FNEG);
        } else if (typeCode == DOUBLE) {
            context.getMethodVisitor().visitInsn(Opcodes.DNEG);
        } else {
            throw new CompileException("typeCode");
        }
    }
}
