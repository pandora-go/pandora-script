package com.github.pandora.antlr;

import com.github.pandora.antlr.generated.ScriptBaseVisitor;
import com.github.pandora.antlr.generated.ScriptLexer;
import com.github.pandora.antlr.generated.ScriptParser;
import com.github.pandora.ast.*;
import com.github.pandora.exception.ParseException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class AstNodeVisitor extends ScriptBaseVisitor<BaseScriptNode> {

    private final Class<?> klass;

    // desc map to id
    // 语法校验

    public AstNodeVisitor(Class<?> klass) {
        this.klass = klass;
    }

    public static int getStartPos(TerminalNode node) {
        Token token = node.getSymbol();
        return token.getLine() << 16 | token.getCharPositionInLine();
    }

    public static int getEndPos(TerminalNode node) {
        Token token = node.getSymbol();
        return token.getLine() << 16 | token.getCharPositionInLine() + token.getStopIndex() - token.getStartIndex();
    }

    // '-' expression                           #unaryMinusExpression
    @Override
    public BaseScriptNode visitUnaryMinusExpression(ScriptParser.UnaryMinusExpressionContext ctx) {
        BaseScriptNode v = this.visit(ctx.expression());
        TerminalNode node = ctx.Subtract();
        return new MinusOpExpr(new BaseScriptNode[]{v}, 0, getStartPos(node), node.getText());
    }

    // '!' expression                           #notExpression
    @Override
    public BaseScriptNode visitNotExpression(ScriptParser.NotExpressionContext ctx) {
        BaseScriptNode v = this.visit(ctx.expression());
        TerminalNode node = ctx.Excl();
        return new NotOpExpr(new BaseScriptNode[]{v}, 0, getStartPos(node), node.getText());
    }

    // expression op=( '*' | '/' | '%' ) expression         #multExpression
    @Override
    public BaseScriptNode visitMultExpression(ScriptParser.MultExpressionContext ctx) {
        switch (ctx.op.getType()) {
            case ScriptLexer.Multiply:
                return multiply(ctx);
            case ScriptLexer.Divide:
                return divide(ctx);
            case ScriptLexer.Modulus:
                return modulus(ctx);
            default:
                throw new RuntimeException("unknown operator type: " + ctx.op.getType());
        }
    }

    // expression op=( '+' | '-' ) expression               #addExpression
    @Override
    public BaseScriptNode visitAddExpression(ScriptParser.AddExpressionContext ctx) {
        switch (ctx.op.getType()) {
            case ScriptLexer.Add:
                return add(ctx);
            case ScriptLexer.Subtract:
                return subtract(ctx);
            default:
                throw new RuntimeException("unknown operator type: " + ctx.op.getType());
        }
    }

    // expression op=( '>=' | '<=' | '>' | '<' ) expression #compExpression
    @Override
    public BaseScriptNode visitCompExpression(ScriptParser.CompExpressionContext ctx) {
        switch (ctx.op.getType()) {
            case ScriptLexer.LT:
                return lt(ctx);
            case ScriptLexer.LTEquals:
                return ltEq(ctx);
            case ScriptLexer.GT:
                return gt(ctx);
            case ScriptLexer.GTEquals:
                return gtEq(ctx);
            default:
                throw new RuntimeException("unknown operator type: " + ctx.op.getType());
        }
    }

    // expression op=( '==' | '!=' ) expression             #eqExpression
    @Override
    public BaseScriptNode visitEqExpression(ScriptParser.EqExpressionContext ctx) {
        switch (ctx.op.getType()) {
            case ScriptLexer.Equals:
                return eq(ctx);
            case ScriptLexer.NEquals:
                return nEq(ctx);
            default:
                throw new RuntimeException("unknown operator type: " + ctx.op.getType());
        }
    }

    public BaseScriptNode multiply(ScriptParser.MultExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));
        
        return new MulOpExpr(new BaseScriptNode[]{lhs, rhs}, 0, 0, ctx.op.getText());
    }

    private BaseScriptNode divide(ScriptParser.MultExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));
        
        return new DivOpExpr(new BaseScriptNode[]{lhs, rhs}, 0, 0, ctx.op.getText());
    }

    private BaseScriptNode modulus(ScriptParser.MultExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));
        
        return new ModOpExpr(new BaseScriptNode[]{lhs, rhs}, 0, 0, ctx.op.getText());
    }

    private BaseScriptNode add(ScriptParser.AddExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));

        
        return new AddOpExpr(new BaseScriptNode[]{lhs, rhs}, 0, 0, ctx.op.getText());
    }

    private BaseScriptNode subtract(ScriptParser.AddExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));
        
        return new SubOpExpr(new BaseScriptNode[]{lhs, rhs}, 0, 0, ctx.op.getText());
    }

    private BaseScriptNode gtEq(ScriptParser.CompExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));
        
        return new GeOp(new BaseScriptNode[]{lhs, rhs}, 0, 0, ctx.op.getText());
    }

    private BaseScriptNode ltEq(ScriptParser.CompExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));
        
        return new LeOp(new BaseScriptNode[]{lhs, rhs}, 0, 0, ctx.op.getText());
    }

    private BaseScriptNode gt(ScriptParser.CompExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));
        
        return new GtOp(new BaseScriptNode[]{lhs, rhs}, 0, 0, ctx.op.getText());
    }

    private BaseScriptNode lt(ScriptParser.CompExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));
        
        return new LtOp(new BaseScriptNode[]{lhs, rhs}, 0, 0, ctx.op.getText());
    }

    private BaseScriptNode eq(ScriptParser.EqExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));
        
        return new EqOp(new BaseScriptNode[]{lhs, rhs}, 0, 0, ctx.op.getText());
    }

    private BaseScriptNode nEq(ScriptParser.EqExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));
        
        return new NeOp(new BaseScriptNode[]{lhs, rhs}, 0, 0, ctx.op.getText());
    }

    // expression '&&' expression               #andExpression
    @Override
    public BaseScriptNode visitAndExpression(ScriptParser.AndExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));

        TerminalNode node = ctx.And();
        return new AndOpExpr(new BaseScriptNode[]{lhs, rhs}, 0, 0, node.getText());
    }

    // expression '||' expression               #orExpression
    @Override
    public BaseScriptNode visitOrExpression(ScriptParser.OrExpressionContext ctx) {
        BaseScriptNode lhs = this.visit(ctx.expression(0));
        BaseScriptNode rhs = this.visit(ctx.expression(1));

        TerminalNode node = ctx.Or();
        return new OrOpExpr(new BaseScriptNode[]{lhs, rhs}, 0, 0, node.getText());
    }

    // Number                                   #numberExpression
    @Override
    public BaseScriptNode visitNumberExpression(ScriptParser.NumberExpressionContext ctx) {
        TerminalNode node = ctx.Number();
        return new NumberLiteral(getEndPos(node), getStartPos(node), node.getText());
    }

    // Bool                                     #boolExpression
    @Override
    public BaseScriptNode visitBoolExpression(ScriptParser.BoolExpressionContext ctx) {
        TerminalNode node = ctx.Bool();
        return new BooleanLiteral(getEndPos(node), getStartPos(node), node.getText());
    }

    // Null                                     #nullExpression
    @Override
    public BaseScriptNode visitNullExpression(ScriptParser.NullExpressionContext ctx) {
        TerminalNode node = ctx.Null();
        return new Null(getEndPos(node), getStartPos(node), node.getText());
    }

    // functionCall indexes?                    #functionCallExpression
    @Override
    public BaseScriptNode visitFunctionCallExpression(ScriptParser.FunctionCallExpressionContext ctx) {
        return visit(ctx.functionCall());
    }


    // Identifier indexes?                      #identifierExpression
    @Override
    public BaseScriptNode visitIdentifierExpression(ScriptParser.IdentifierExpressionContext ctx) {
        String id = ctx.Identifier().getText();
        TerminalNode node = ctx.Identifier();

        try {
            // tod name and id change
            return new ClassFieldIdentifier(getEndPos(node), getStartPos(node), id, false, id, klass.getField(id));
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    // String indexes?                          #stringExpression
    @Override
    public BaseScriptNode visitStringExpression(ScriptParser.StringExpressionContext ctx) {
        String text = ctx.getText();
        text = text.substring(1, text.length() - 1);

        TerminalNode node = ctx.String();

        return new StringLiteral(getEndPos(node), getStartPos(node), text);
    }

    // '(' expression ')' indexes?              #expressionExpression
    @Override
    public BaseScriptNode visitExpressionExpression(ScriptParser.ExpressionExpressionContext ctx) {
        return this.visit(ctx.expression());
    }

    // assignment
    // : Identifier indexes? '=' expression
    // ;
    @Override
    public BaseScriptNode visitAssignment(ScriptParser.AssignmentContext ctx) {
        BaseScriptNode newVal = this.visit(ctx.expression());

        TerminalNode node = ctx.Identifier();
        String id = node.getText();

        try {
            Identifier identifier = new ClassFieldIdentifier(getEndPos(node), getStartPos(node), id, true, id, klass.getField(id));

            return new Assignment(new BaseScriptNode[]{identifier, newVal}, 0, 0);
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    // Identifier '(' exprList? ')' #identifierFunctionCall
    @Override
    public BaseScriptNode visitIdentifierFunctionCall(ScriptParser.IdentifierFunctionCallContext ctx) {
        List<BaseScriptNode> body = new ArrayList<>();
        TerminalNode node = ctx.Identifier();
        String id = node.getText();
        body.add(new Identifier(getEndPos(node), getStartPos(node), id, false));

        for (ScriptParser.ExpressionContext exp : ctx.expressionList().expression()) {
            body.add(visit(exp));
        }
        return new FunctionCallExpr(body.toArray(new BaseScriptNode[0]), 0, 0);
    }


    // Print '(' expression ')'     #printFunctionCall
    @Override
    public BaseScriptNode visitPrintFunctionCall(ScriptParser.PrintFunctionCallContext ctx) {
        TerminalNode node = ctx.Print();
        String id = node.getText();
        Identifier identifier = new Identifier(getEndPos(node), getStartPos(node), id, false);

        ScriptParser.ExpressionContext exp = ctx.expression();
        BaseScriptNode expression = visit(exp);

        return new FunctionCallExpr(new BaseScriptNode[]{identifier, expression}, 0, 0);
    }

    // Assert '(' expression ')'    #assertFunctionCall
    @Override
    public BaseScriptNode visitAssertFunctionCall(ScriptParser.AssertFunctionCallContext ctx) {
        TerminalNode node = ctx.Assert();
        String id = node.getText();
        Identifier identifier = new Identifier(getEndPos(node), getStartPos(node), id, false);

        ScriptParser.ExpressionContext exp = ctx.expression();
        BaseScriptNode expression = visit(exp);

        return new FunctionCallExpr(new BaseScriptNode[]{identifier, expression}, 0, 0);
    }

    // ifStatement
    //  : ifStat elseIfStat* elseStat? End
    //  ;
    //
    // ifStat
    //  : If expression Do block
    //  ;
    //
    // elseIfStat
    //  : Else If expression Do block
    //  ;
    //
    // elseStat
    //  : Else Do block
    //  ;
    @Override
    public BaseScriptNode visitIfStatement(ScriptParser.IfStatementContext ctx) {

        List<BaseScriptNode> body = new ArrayList<>();

        // if ...
        {
            BaseScriptNode exp = this.visit(ctx.ifConditionBlock().expression());
            BaseScriptNode block = this.visit(ctx.ifConditionBlock().block());
            body.add(new IfConditionalBlock(new BaseScriptNode[]{exp, block}, 0, 0));
        }

        // else if ...
        for(int i = 0; i < ctx.elseIfConditionBlock().size(); i++) {
            BaseScriptNode exp =  this.visit(ctx.elseIfConditionBlock(i).expression());
            BaseScriptNode block = this.visit(ctx.elseIfConditionBlock(i).block());
            body.add(new ElseIfConditionalBlock(new BaseScriptNode[]{exp, block}, 0, 0));
        }

        // else ...
        if(ctx.elseBlock() != null) {
            BaseScriptNode node = this.visit(ctx.elseBlock().block());
            body.add(new ElseBlock(new BaseScriptNode[]{node}, 0, 0));
        }

        return new IfStatement(body.toArray(new BaseScriptNode[0]), 0, 0);
    }

    // block
    // : (statement)*
    // ;
    @Override
    public BaseScriptNode visitBlock(ScriptParser.BlockContext ctx) {

        List<BaseScriptNode> body = new ArrayList<>();
        for (ScriptParser.StatementContext sx: ctx.statement()) {
            BaseScriptNode node = this.visit(sx);
            body.add(node);
        }
        return new Block(body.toArray(new BaseScriptNode[0]), 0, 0);
    }

    @Override public BaseScriptNode visitStatement(ScriptParser.StatementContext ctx) {
        if (ctx.assignment() != null) {
            return visit(ctx.assignment());
        } else if (ctx.ifStatement() != null) {
            return visit(ctx.ifStatement());
        } else if (ctx.whileStatement() != null) {
            return visit(ctx.whileStatement());
        } else if (ctx.functionCall() != null) {
            return visit(ctx.functionCall());
        }
        return null;
    }

    // whileStatement
    // : While expression OBrace block CBrace
    // ;
    @Override
    public BaseScriptNode visitWhileStatement(ScriptParser.WhileStatementContext ctx) {
        BaseScriptNode exp = this.visit(ctx.expression());

        BaseScriptNode block = this.visit(ctx.block());

        return new WhileStatement(new BaseScriptNode[]{exp, block}, 0, 0);
    }
}
