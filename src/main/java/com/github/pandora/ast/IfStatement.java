package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import org.objectweb.asm.Label;

public class IfStatement extends BaseScriptNode {

    public IfStatement(BaseScriptNode[] children, int endPos, int startPos) {
        super(children, endPos, startPos);
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        context.enterLabel(new Label());

        try {
            for (BaseScriptNode child : children) {
                child.generateCode(context);
            }
        } finally {
            context.emitLabel();
        }
    }
}
