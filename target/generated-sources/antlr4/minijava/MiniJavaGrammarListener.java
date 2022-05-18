// Generated from MiniJavaGrammar.g4 by ANTLR 4.4
package minijava;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniJavaGrammarParser}.
 */
public interface MiniJavaGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#mainclass}.
	 * @param ctx the parse tree
	 */
	void enterMainclass(@NotNull MiniJavaGrammarParser.MainclassContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#mainclass}.
	 * @param ctx the parse tree
	 */
	void exitMainclass(@NotNull MiniJavaGrammarParser.MainclassContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#op}.
	 * @param ctx the parse tree
	 */
	void enterOp(@NotNull MiniJavaGrammarParser.OpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#op}.
	 * @param ctx the parse tree
	 */
	void exitOp(@NotNull MiniJavaGrammarParser.OpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#methoddecl}.
	 * @param ctx the parse tree
	 */
	void enterMethoddecl(@NotNull MiniJavaGrammarParser.MethoddeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#methoddecl}.
	 * @param ctx the parse tree
	 */
	void exitMethoddecl(@NotNull MiniJavaGrammarParser.MethoddeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#exprlist}.
	 * @param ctx the parse tree
	 */
	void enterExprlist(@NotNull MiniJavaGrammarParser.ExprlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#exprlist}.
	 * @param ctx the parse tree
	 */
	void exitExprlist(@NotNull MiniJavaGrammarParser.ExprlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#formallist}.
	 * @param ctx the parse tree
	 */
	void enterFormallist(@NotNull MiniJavaGrammarParser.FormallistContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#formallist}.
	 * @param ctx the parse tree
	 */
	void exitFormallist(@NotNull MiniJavaGrammarParser.FormallistContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#formalrest}.
	 * @param ctx the parse tree
	 */
	void enterFormalrest(@NotNull MiniJavaGrammarParser.FormalrestContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#formalrest}.
	 * @param ctx the parse tree
	 */
	void exitFormalrest(@NotNull MiniJavaGrammarParser.FormalrestContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(@NotNull MiniJavaGrammarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(@NotNull MiniJavaGrammarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#vardecl}.
	 * @param ctx the parse tree
	 */
	void enterVardecl(@NotNull MiniJavaGrammarParser.VardeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#vardecl}.
	 * @param ctx the parse tree
	 */
	void exitVardecl(@NotNull MiniJavaGrammarParser.VardeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull MiniJavaGrammarParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull MiniJavaGrammarParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#classdecl}.
	 * @param ctx the parse tree
	 */
	void enterClassdecl(@NotNull MiniJavaGrammarParser.ClassdeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#classdecl}.
	 * @param ctx the parse tree
	 */
	void exitClassdecl(@NotNull MiniJavaGrammarParser.ClassdeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#exprrest}.
	 * @param ctx the parse tree
	 */
	void enterExprrest(@NotNull MiniJavaGrammarParser.ExprrestContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#exprrest}.
	 * @param ctx the parse tree
	 */
	void exitExprrest(@NotNull MiniJavaGrammarParser.ExprrestContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull MiniJavaGrammarParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull MiniJavaGrammarParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(@NotNull MiniJavaGrammarParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(@NotNull MiniJavaGrammarParser.ExprContext ctx);
}