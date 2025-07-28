package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import net.sf.cglib.core.CodeEmitter;
import org.objectweb.asm.Label;

public abstract class LogicalOpExpr extends OpExpr {

    public LogicalOpExpr(BaseScriptNode[] children, int endPos, int startPos, String operator) {
        super(children, endPos, startPos, operator);

        typeCode = TypeCode.BOOLEAN;
    }


    protected void generateCode(GenerateContext context, int opcode, int val) throws CompileException {
        Expression left = getLeftOperand();
        left.generateCode(context);

        context.boxAndUnbox(left.primitive, primitive, typeCode);

        CodeEmitter mv = context.getMethodVisitor();
        Label target = new Label();
        mv.if_jump(opcode, target);
        mv.push(val);
        Label end = new Label();
        mv.goTo(end);
        mv.mark(target);

        Expression right = getRightOperand();
        right.generateCode(context);
        context.boxAndUnbox(right.primitive, primitive, typeCode);

        mv.mark(end);
    }
}
