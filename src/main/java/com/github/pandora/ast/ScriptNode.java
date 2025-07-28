package com.github.pandora.ast;

import com.github.pandora.eval.Evaluator;
import com.github.pandora.exception.CompileException;

public interface ScriptNode extends Evaluator {

    int OPERATOR_NUM = 2;

    int UNARY_OPERATOR_NUM = 1;

    int getChildrenCount();

    ScriptNode getChild(int index);

    ScriptNode getParent();

    void generateCode(GenerateContext context) throws CompileException;

    String decompile();

    Position getStartPosition();

    Position getEndPosition();
}
