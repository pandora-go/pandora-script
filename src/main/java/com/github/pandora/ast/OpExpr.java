package com.github.pandora.ast;

import com.github.pandora.exception.ParseException;
import com.github.pandora.utils.StringUtils;

public class OpExpr extends Expression {

    protected String operator;

    public OpExpr(BaseScriptNode[] children, int endPos, int startPos, String operator) {
        super(children, endPos, startPos);

        if (children.length != OPERATOR_NUM) {
            throw new ParseException("children nodes invalid");
        }

        if (StringUtils.isBlank(operator)) {
            throw new ParseException("operator is empty");
        }

        this.operator = operator;
    }

    protected Expression getLeftOperand() {
        return (Expression) getChild(0);
    }

    protected Expression getRightOperand() {
        return (Expression) getChild(1);
    }
}
