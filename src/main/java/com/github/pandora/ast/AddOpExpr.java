package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import org.objectweb.asm.Opcodes;

import static com.github.pandora.ast.TypeCode.*;

public class AddOpExpr extends ArithmeticOpExpr {

    public AddOpExpr(BaseScriptNode[] children, int endPos, int startPos, String operator) {
        super(children, endPos, startPos, operator);
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        generateCode4Op(context);

        if (typeCode == INT) {
            context.getMethodVisitor().visitInsn(Opcodes.IADD);
        } else if (typeCode == LONG) {
            context.getMethodVisitor().visitInsn(Opcodes.LADD);
        } else if (typeCode == FLOAT) {
            context.getMethodVisitor().visitInsn(Opcodes.FADD);
        } else if (typeCode == DOUBLE) {
            context.getMethodVisitor().visitInsn(Opcodes.DADD);
        } else {
            throw new CompileException("typeCode");
        }
    }
}
