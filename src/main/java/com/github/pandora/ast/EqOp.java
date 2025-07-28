package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import org.objectweb.asm.Opcodes;

public class EqOp extends RelationalOpExpr {

    public EqOp(BaseScriptNode[] children, int endPos, int startPos, String operator) {
        super(children, endPos, startPos, operator);
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        generateCode4Op(context, Opcodes.IFNE, Opcodes.IF_ICMPNE);
    }
}
