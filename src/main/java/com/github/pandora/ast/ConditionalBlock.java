package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

public abstract class ConditionalBlock extends BaseScriptNode {

    public ConditionalBlock(BaseScriptNode[] children, int endPos, int startPos) {
        super(children, endPos, startPos);
    }

    public Expression getCondition() {
        return (Expression) getChild(0);
    }

    public Block getBlock() {
        return (Block) getChild(1);
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        getCondition().generateCode(context);

        Label end = new Label();
        context.getMethodVisitor().if_jump(Opcodes.IFEQ, end);
        getBlock().generateCode(context);

        context.gotoCurrentLabel();
        context.getMethodVisitor().mark(end);
    }
}
