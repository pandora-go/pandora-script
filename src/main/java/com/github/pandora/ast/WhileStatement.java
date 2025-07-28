package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import net.sf.cglib.core.CodeEmitter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

public class WhileStatement extends BaseScriptNode {

    public WhileStatement(BaseScriptNode[] children, int endPos, int startPos) {
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
        Label begin = new Label();

        CodeEmitter mv = context.getMethodVisitor();

        mv.mark(begin);
        getCondition().generateCode(context);

        Label end = new Label();
        mv.if_jump(Opcodes.IFEQ, end);
        getBlock().generateCode(context);

        mv.goTo(begin);
        mv.mark(end);
    }
}
