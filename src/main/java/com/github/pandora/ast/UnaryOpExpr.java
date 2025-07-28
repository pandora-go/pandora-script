package com.github.pandora.ast;

import com.github.pandora.exception.ParseException;
import com.github.pandora.utils.StringUtils;

public abstract class UnaryOpExpr extends Expression {

    protected String operator;

    public UnaryOpExpr(BaseScriptNode[] children, int endPos, int startPos, String operator) {
        super(children, endPos, startPos);

        if (children.length != UNARY_OPERATOR_NUM) {
            throw new ParseException("children nodes invalid");
        }

        if (StringUtils.isBlank(operator)) {
            throw new ParseException("operator is empty");
        }

        this.operator = operator;
    }

    public Expression getOperand() {
        return (Expression) getChild(0);
    }

    public String getOperator() {
        return operator;
    }
}
