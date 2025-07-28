package com.github.pandora.ast;

import com.github.pandora.exception.ParseException;
import com.github.pandora.utils.StringUtils;

public class Literal extends Expression {

    protected String literal;

    public Literal(int endPos, int startPos, String literal) {
        super(EMPTY_NODES, endPos, startPos);

        if (StringUtils.isBlank(literal)) {
            throw new ParseException("literal is empty");
        }
        this.literal = literal;
    }
}
