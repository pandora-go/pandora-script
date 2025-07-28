package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import com.github.pandora.function.FunctionBox;
import com.github.pandora.function.FunctionCall;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Signature;
import org.objectweb.asm.Type;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class FunctionCallExpr extends Expression {

    public FunctionCallExpr(BaseScriptNode[] children, int endPos, int startPos) {
        super(children, endPos, startPos);

        Identifier id = getIdentifier();

        FunctionCall call = FunctionBox.getInstance().getFunction(id.name);
        if (call == null) {
            throw new CompileException("can not find function with name " + id.name);
        }

        typeCode = TypeCode.forClass(call.getReturnType());
        primitive = TypeCode.isPrimitive(call.getReturnType());
    }

    protected Identifier getIdentifier() {
        return (Identifier) getChild(0);
    }

    protected List<Expression> getArguments() {
        List<Expression> args = new ArrayList<>();
        for (int i = 1; i < getChildrenCount(); i++) {
            args.add((Expression) getChild(i));
        }
        return args;
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        Identifier id = getIdentifier();

        FunctionCall call = FunctionBox.getInstance().getFunction(id.name);
        if (call == null) {
            throw new CompileException("can not find function with name " + id.name);
        }

        Class<?>[] argsType = call.getArgsType();
        List<Expression> args = getArguments();
        if (args.size() != argsType.length) {
            throw new CompileException(String.format("function call %s with unmatched args len %d, %d", id.name, argsType.length, args.size()));
        }

        List<Type> argsTypes = new ArrayList<>();
        for (int i = 0; i < argsType.length; i++) {
            Expression exp = args.get(i);
            Class<?> klass = argsType[i];

            TypeCode targetTypeCode = TypeCode.forClass(klass);
            if (targetTypeCode != exp.typeCode) {
                throw new CompileException(String.format("function call %s with unmatched %d th args type %s, %s", id.name, i, targetTypeCode, exp.typeCode));
            }

            exp.generateCode(context);

            boolean targetPrimitive = TypeCode.isPrimitive(klass);
            context.boxAndUnbox(exp.primitive, targetPrimitive, targetTypeCode);

            argsTypes.add(Type.getType(klass));
        }

        CodeEmitter mv = context.getMethodVisitor();
        Class<?> klass = call.getKlass();
        int mod = call.getMethod().getModifiers();
        if (!Modifier.isStatic(mod) || !Modifier.isPublic(mod)) {
            throw new CompileException(String.format("function call %s with invalid modifier type %s", id.name, Modifier.toString(mod)));
        }
        mv.invoke_static(Type.getType(klass), new Signature(call.getMethod().getName(), Type.getType(call.getReturnType()), argsTypes.toArray(new Type[0])));
    }
}
