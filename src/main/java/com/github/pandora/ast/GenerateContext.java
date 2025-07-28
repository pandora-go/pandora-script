package com.github.pandora.ast;

import com.github.pandora.eval.EvalTemplate;
import com.github.pandora.utils.ClassLoaderUtils;
import com.github.pandora.exception.CompileException;
import net.sf.cglib.core.*;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.Stack;

public class GenerateContext {

    private final CodeEmitter methodVisitor;

    private final ClassWriter classWriter;

    private final ClassEmitter classEmitter;

    private final String klassName;

    private int lineNumber = 0;

    private static boolean debug = false;

    private static String outPutClassFileDir = null;

    private final Stack<Label> labelStack = new Stack<>();

    private volatile Class<EvalTemplate> templateClass;

    private ClassLoader classLoader = ClassLoaderUtils.getClassLoader();

    public static void enableDebug() {
        debug = true;
    }

    public static void enableOutPutClassFile(String dir) {
        outPutClassFileDir = dir;
    }

    public void enterLabel(Label l) {
        labelStack.push(l);
    }

    public void gotoCurrentLabel() {
        methodVisitor.goTo(labelStack.peek());
    }

    public void emitLabel() {
        methodVisitor.visitLabel(labelStack.pop());
    }

    public void emitLine(int lineNumber) {
        if (debug) {
            return;
        }
        if (this.lineNumber != lineNumber) {
            this.lineNumber = lineNumber;
            Label label = methodVisitor.make_label();
            methodVisitor.visitLabel(label);
            methodVisitor.visitLineNumber(lineNumber, label);
        }
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public CodeEmitter getMethodVisitor() {
        return methodVisitor;
    }

    public Class<EvalTemplate> getTemplateClass() {
        if (templateClass == null) {
            synchronized (this) {
                if (templateClass == null) {
                    methodVisitor.load_arg(0);
                    methodVisitor.return_value();
                    methodVisitor.end_method();

                    classEmitter.end_class();

                    if (outPutClassFileDir != null) {
                        try {
                            ClassLoaderUtils.saveClassFile(outPutClassFileDir + klassName.substring(klassName.lastIndexOf('.') + 1) + ".class", classWriter.toByteArray());
                        } catch (Exception e) {
                            throw new CompileException(e);
                        }
                    }

                    templateClass = ClassLoaderUtils.defineClass(classLoader, klassName, classWriter.toByteArray());
                }
            }
        }
        return templateClass;
    }

    public GenerateContext() {
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classEmitter = new ClassEmitter(classWriter);

        klassName = EvalTemplate.class.getName() + "@" + System.nanoTime();
        classEmitter.begin_class(Constants.V1_8, Constants.ACC_PUBLIC, klassName,
                Type.getType(EvalTemplate.class), null, klassName.substring(klassName.lastIndexOf('.') + 1) + ".java");

        CodeEmitter constructor = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature(Constants.CONSTRUCTOR_NAME,"()V"),null);
        constructor.load_this();
        constructor.super_invoke_constructor();
        constructor.return_value();
        constructor.end_method();

        methodVisitor = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature("eval","(Ljava/lang/Object;)Ljava/lang/Object;"),null);
    }

    public GenerateContext(Class<?> klass) {
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classEmitter = new ClassEmitter(classWriter);

        klassName = EvalTemplate.class.getName() + "@" + System.nanoTime();
        classEmitter.begin_class(Constants.V1_8, Constants.ACC_PUBLIC, klassName,
                Type.getType(EvalTemplate.class), null, klassName.substring(klassName.lastIndexOf('.') + 1) + ".java");

        CodeEmitter constructor = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature(Constants.CONSTRUCTOR_NAME,"()V"),null);
        constructor.load_this();
        constructor.super_invoke_constructor();
        constructor.return_value();
        constructor.end_method();

        methodVisitor = classEmitter.begin_method(Constants.ACC_PUBLIC,new Signature("eval","(Ljava/lang/Object;)Ljava/lang/Object;"),null);
        methodVisitor.load_arg(0);
        methodVisitor.checkcast(Type.getType(klass));
        methodVisitor.store_local(new Local(0, Type.getType(klass)));

    }

    public void boxAndUnbox(boolean prim, boolean targetPrim, TypeCode target) {
        if (prim != targetPrim) {
            if (targetPrim) {
                unbox(target);
            } else {
                box(target);
            }
        }
    }

    public void unbox(TypeCode target) {
        CodeEmitter mv = methodVisitor;
        switch (target) {
            case BOOLEAN:
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
                break;
            case BYTE:
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B", false);
                break;
            case CHAR:
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C", false);
                break;
            case DOUBLE:
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
                break;
            case FLOAT:
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F", false);
                break;
            case INT:
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
                break;
            case LONG:
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
                break;
            case SHORT:
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S", false);
                break;
            default:
                throw new CompileException("Unsupported type: " + target);
        }
    }

    public void box(TypeCode target) {
        CodeEmitter mv = this.methodVisitor;
        switch (target) {
            case BOOLEAN:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
                break;
            case BYTE:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
                break;
            case CHAR:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
                break;
            case SHORT:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
                break;
            case FLOAT:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                break;
            case DOUBLE:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                break;
            case INT:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                break;
            case LONG:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                break;
            default:
                throw new CompileException("Unsupported type: " + target);
        }
    }

    public void typeConvert4Assign(boolean prim, TypeCode stack, boolean targetPrim, TypeCode target) {
        if (prim != targetPrim) {
            if (!prim) { // double d = Float f
                unbox(stack);
            }
            typeConvert(target, stack);
            if (prim) { // Double d = float f
                box(target);
            }
        } else {
            if (!prim) {
                // Double d = Float f
                if (target != stack) {
                    unbox(stack);
                    typeConvert(target, stack);
                    box(target);
                }
                // Double d = Double k
            } else {
                // double d = float f
                typeConvert(target, stack);
            }
        }
    }

    public void typeConvert(TypeCode target, TypeCode stack) {
        CodeEmitter mv = methodVisitor;
        if (stack == TypeCode.INT || stack == TypeCode.BYTE || stack == TypeCode.SHORT || stack == TypeCode.CHAR) {
            if (target == TypeCode.DOUBLE) {
                mv.visitInsn(Opcodes.I2D);
            }
            else if (target == TypeCode.FLOAT) {
                mv.visitInsn(Opcodes.I2F);
            }
            else if (target == TypeCode.LONG) {
                mv.visitInsn(Opcodes.I2L);
            }
            else if (target == TypeCode.INT) {
                // nop
            }
            else {
                throw new CompileException("Cannot convert from " + stack + " to " + target);
            }
        }
        else if (stack == TypeCode.LONG) {
            if (target == TypeCode.DOUBLE) {
                mv.visitInsn(Opcodes.L2D);
            }
            else if (target == TypeCode.FLOAT) {
                mv.visitInsn(Opcodes.L2F);
            }
            else if (target == TypeCode.LONG) {
                // nop
            }
            else if (target == TypeCode.INT) {
                mv.visitInsn(Opcodes.L2I);
            }
            else {
                throw new CompileException("Cannot convert from " + stack + " to " + target);
            }
        }
        else if (stack == TypeCode.FLOAT) {
            if (target == TypeCode.DOUBLE) {
                mv.visitInsn(Opcodes.F2D);
            }
            else if (target == TypeCode.FLOAT) {
                // nop
            }
            else if (target == TypeCode.LONG) {
                mv.visitInsn(Opcodes.F2L);
            }
            else if (target == TypeCode.INT) {
                mv.visitInsn(Opcodes.F2I);
            }
            else {
                throw new CompileException("Cannot convert from " + stack + " to " + target);
            }
        }
        else if (stack == TypeCode.DOUBLE) {
            if (target == TypeCode.DOUBLE) {
                // nop
            }
            else if (target == TypeCode.FLOAT) {
                mv.visitInsn(Opcodes.D2F);
            }
            else if (target == TypeCode.LONG) {
                mv.visitInsn(Opcodes.D2L);
            }
            else if (target == TypeCode.INT) {
                mv.visitInsn(Opcodes.D2I);
            }
            else {
                throw new CompileException("Cannot convert from " + stack + " to " + target);
            }
        }
    }

}
