package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;

public class Block extends BaseScriptNode {

    public Block(BaseScriptNode[] children, int endPos, int startPos) {
        super(children, endPos, startPos);
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        for (BaseScriptNode child : children) {
            child.generateCode(context);
        }
    }
}
