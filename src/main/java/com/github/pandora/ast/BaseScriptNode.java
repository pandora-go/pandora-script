package com.github.pandora.ast;

import com.github.pandora.eval.EvaluationContext;
import com.github.pandora.eval.EvaluationValue;
import com.github.pandora.exception.EvaluationException;
import com.github.pandora.exception.CompileException;
import com.github.pandora.utils.StringUtils;

public abstract class BaseScriptNode implements ScriptNode {

    public static BaseScriptNode[] EMPTY_NODES = new BaseScriptNode[0];

    protected BaseScriptNode parent;

    protected BaseScriptNode[] children;

    protected int endPos;

    protected int startPos;

    protected TypeCode typeCode;

    protected boolean primitive = true;

    public BaseScriptNode(BaseScriptNode[] children, int endPos, int startPos) {
        this.children = children;
        this.endPos = endPos;
        this.startPos = startPos;
        for (BaseScriptNode node : children) {
            node.parent = this;
        }
        if (startPos == 0 && children.length > 0) {
            this.startPos = children[0].startPos;
        }
        if (endPos == 0 && children.length > 0) {
            this.endPos = children[children.length - 1].endPos;
        }
    }

    @Override
    public int getChildrenCount() {
        return children.length;
    }

    @Override
    public ScriptNode getChild(int index) {
        return children[index];
    }

    @Override
    public ScriptNode getParent() {
        return parent;
    }

    @Override
    public void generateCode(GenerateContext context) throws CompileException {
        context.emitLine(getStartPosition().getLine());
        generateCodeInner(context);
    }

    public void generateCodeInner(GenerateContext context) throws CompileException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String decompile() {
        return StringUtils.EMPTY;
    }

    @Override
    public Position getStartPosition() {
        return new Position(startPos >> 16, startPos & 0xffff);
    }

    @Override
    public Position getEndPosition() {
        return new Position(endPos >> 16, endPos & 0xffff);
    }

    @Override
    public EvaluationValue evaluate(EvaluationContext context) throws EvaluationException {
        throw new UnsupportedOperationException();
    }
}
