package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import org.objectweb.asm.Opcodes;

public class OrOpExpr extends LogicalOpExpr {

    public OrOpExpr(BaseScriptNode[] children, int endPos, int startPos, String operator) {
        super(children, endPos, startPos, operator);
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        generateCode(context, Opcodes.IFEQ, 1);
    }
}
