package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import org.objectweb.asm.Opcodes;

public class SubOpExpr extends ArithmeticOpExpr {

    public SubOpExpr(BaseScriptNode[] children, int endPos, int startPos, String operator) {
        super(children, endPos, startPos, operator);
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        generateCode4Op(context);

        if (typeCode == TypeCode.INT) {
            context.getMethodVisitor().visitInsn(Opcodes.ISUB);
        } else if (typeCode == TypeCode.LONG) {
            context.getMethodVisitor().visitInsn(Opcodes.LSUB);
        } else if (typeCode == TypeCode.FLOAT) {
            context.getMethodVisitor().visitInsn(Opcodes.FSUB);
        } else if (typeCode == TypeCode.DOUBLE) {
            context.getMethodVisitor().visitInsn(Opcodes.DSUB);
        } else {
            throw new CompileException("typeCode");
        }
    }
}
