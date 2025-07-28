package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;

public class BooleanLiteral extends Literal {

    private boolean value;

    public BooleanLiteral(int endPos, int startPos, String literal) {
        super(endPos, startPos, literal);

        typeCode = TypeCode.BOOLEAN;
        value = Boolean.parseBoolean(literal);
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        context.getMethodVisitor().push(value);
    }
}
