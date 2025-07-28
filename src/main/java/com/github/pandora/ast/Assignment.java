package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;

public class Assignment extends BaseScriptNode {

    public Assignment(BaseScriptNode[] children, int endPos, int startPos) {
        super(children, endPos, startPos);
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        Identifier id = (Identifier) getChild(0);
        Expression right = (Expression) getChild(1);

        right.generateCode(context);

        context.typeConvert4Assign(right.primitive, right.typeCode, id.primitive, id.typeCode);

        id.generateCode(context);
    }
}
