package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;

public class ElseBlock extends BaseScriptNode {

    public ElseBlock(BaseScriptNode[] children, int endPos, int startPos) {
        super(children, endPos, startPos);
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        getChild(0).generateCode(context);
    }
}
