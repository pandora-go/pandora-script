package com.github.pandora.ast;

public class Identifier extends Expression {

    protected String name;

    protected boolean assign;

    public Identifier(int endPos, int startPos, String name, boolean assign) {
        super(EMPTY_NODES, endPos, startPos);
        this.name = name;
        this.assign = assign;
    }

}
