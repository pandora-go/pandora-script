package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import net.sf.cglib.core.CodeEmitter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

public class NotOpExpr extends UnaryOpExpr {

    public NotOpExpr(BaseScriptNode[] children, int endPos, int startPos, String op) {
        super(children, endPos, startPos, op);
        typeCode = TypeCode.BOOLEAN;
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        getOperand().generateCode(context);

        context.boxAndUnbox(getOperand().primitive, primitive, typeCode);

        CodeEmitter mv = context.getMethodVisitor();

        Label target = new Label();
        Label end = new Label();

        // iinc + iand 0x1
        mv.if_jump(Opcodes.IFNE, target);
        mv.push(1);
        mv.goTo(end);
        mv.mark(target);
        mv.push(0);
        mv.mark(end);
    }
}
