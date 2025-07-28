package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import com.github.pandora.exception.ParseException;

public class StringLiteral extends Expression {

    protected String literal;

    public StringLiteral(int endPos, int startPos, String literal) {
        super(EMPTY_NODES, endPos, startPos);

        if (literal == null) {
            throw new ParseException("literal is null");
        }

        this.literal = literal;
        typeCode = TypeCode.STRING;
        primitive = false;
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        context.getMethodVisitor().push(literal);
    }
}
