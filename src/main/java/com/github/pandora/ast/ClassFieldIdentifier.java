package com.github.pandora.ast;

import com.github.pandora.exception.CompileException;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Local;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClassFieldIdentifier extends Identifier {

    private final String fieldName;

    private final Field field;

    public ClassFieldIdentifier(int endPos, int startPos, String name, boolean assign, String fieldName, Field field) {
        super(endPos, startPos, name, assign);
        this.fieldName = fieldName;
        this.field = field;

        typeCode = TypeCode.forClass(field.getType());
        primitive = field.getType().isPrimitive();
    }

    @Override
    public void generateCodeInner(GenerateContext context) throws CompileException {
        Type fieldType = Type.getType(field.getType());
        Type declaringClassType = Type.getType(field.getDeclaringClass());
        CodeEmitter mv = context.getMethodVisitor();
        if (assign) {
            if (Modifier.isStatic(field.getModifiers())) {
                mv.putstatic(declaringClassType, fieldName, fieldType);
                return;
            }

            mv.load_local(new Local(0, declaringClassType));
            mv.swap(fieldType, declaringClassType);
            mv.putfield(declaringClassType, fieldName, fieldType);
            return;
        }

        if (Modifier.isStatic(field.getModifiers())) {
            mv.getstatic(declaringClassType, fieldName, fieldType);
            return;
        }

        mv.load_local(new Local(0, declaringClassType));
        mv.getfield(declaringClassType, fieldName, fieldType);
    }
}
