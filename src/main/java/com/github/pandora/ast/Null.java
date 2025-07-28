package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;

public class Null extends Literal {

    public Null(int endPos, int startPos, String literal) {
        super(endPos, startPos, literal);

        typeCode = TypeCode.OBJECT;
        primitive = false;
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        context.getMethodVisitor().aconst_null();
    }
}
