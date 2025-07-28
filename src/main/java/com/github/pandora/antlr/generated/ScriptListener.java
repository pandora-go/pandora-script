package com.github.pandora.antlr.generated;// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ScriptParser}.
 */
public interface ScriptListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ScriptParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ScriptParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ScriptParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ScriptParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ScriptParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ScriptParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierFunctionCall}
	 * labeled alternative in {@link ScriptParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierFunctionCall(ScriptParser.IdentifierFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierFunctionCall}
	 * labeled alternative in {@link ScriptParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierFunctionCall(ScriptParser.IdentifierFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printFunctionCall}
	 * labeled alternative in {@link ScriptParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterPrintFunctionCall(ScriptParser.PrintFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printFunctionCall}
	 * labeled alternative in {@link ScriptParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitPrintFunctionCall(ScriptParser.PrintFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assertFunctionCall}
	 * labeled alternative in {@link ScriptParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterAssertFunctionCall(ScriptParser.AssertFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assertFunctionCall}
	 * labeled alternative in {@link ScriptParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitAssertFunctionCall(ScriptParser.AssertFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ScriptParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ScriptParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#ifConditionBlock}.
	 * @param ctx the parse tree
	 */
	void enterIfConditionBlock(ScriptParser.IfConditionBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#ifConditionBlock}.
	 * @param ctx the parse tree
	 */
	void exitIfConditionBlock(ScriptParser.IfConditionBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#elseIfConditionBlock}.
	 * @param ctx the parse tree
	 */
	void enterElseIfConditionBlock(ScriptParser.ElseIfConditionBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#elseIfConditionBlock}.
	 * @param ctx the parse tree
	 */
	void exitElseIfConditionBlock(ScriptParser.ElseIfConditionBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#elseBlock}.
	 * @param ctx the parse tree
	 */
	void enterElseBlock(ScriptParser.ElseBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#elseBlock}.
	 * @param ctx the parse tree
	 */
	void exitElseBlock(ScriptParser.ElseBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(ScriptParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(ScriptParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(ScriptParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(ScriptParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpression(ScriptParser.BoolExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpression(ScriptParser.BoolExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNumberExpression(ScriptParser.NumberExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNumberExpression(ScriptParser.NumberExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(ScriptParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(ScriptParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpression(ScriptParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpression(ScriptParser.NotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(ScriptParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(ScriptParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinusExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpression(ScriptParser.UnaryMinusExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinusExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpression(ScriptParser.UnaryMinusExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powerExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPowerExpression(ScriptParser.PowerExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powerExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPowerExpression(ScriptParser.PowerExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqExpression(ScriptParser.EqExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqExpression(ScriptParser.EqExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(ScriptParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(ScriptParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterStringExpression(ScriptParser.StringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitStringExpression(ScriptParser.StringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionExpression(ScriptParser.ExpressionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionExpression(ScriptParser.ExpressionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddExpression(ScriptParser.AddExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddExpression(ScriptParser.AddExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompExpression(ScriptParser.CompExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompExpression(ScriptParser.CompExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNullExpression(ScriptParser.NullExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNullExpression(ScriptParser.NullExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(ScriptParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(ScriptParser.FunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultExpression(ScriptParser.MultExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultExpression(ScriptParser.MultExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ternaryExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpression(ScriptParser.TernaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ternaryExpression}
	 * labeled alternative in {@link ScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpression(ScriptParser.TernaryExpressionContext ctx);
}