package minijava;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import symboltable.Program;
import symboltable.Class;
import symboltable.Method;
import symboltable.Variable;
import minijava.MiniJavaListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MyListener extends MiniJavaGrammarBaseListener {


    MiniJavaGrammarParser parser;
    Program prog;
    Class currClass = null;
    Method currMethod = null;

    public void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }


    public MyListener(MiniJavaGrammarParser parse) {
        this.parser = parser;
    }

    @Override
    public void enterProgram(MiniJavaGrammarParser.ProgramContext ctx) {
        prog = MiniJavaListener.getProgram();
        //System.out.println("---------------------");
    }


    @Override
    public void exitProgram(MiniJavaGrammarParser.ProgramContext ctx) {
    	
    }

    @Override
    public void enterMainclass(MiniJavaGrammarParser.MainclassContext ctx) {
        currClass = prog.classes.get(0);
    }

    @Override
    public void exitMainclass(MiniJavaGrammarParser.MainclassContext ctx) {
    	currClass = null;
    }

    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
    	String id = ctx.getChild(1).toString();
    	for (Class c : prog.classes) {
    		if (c.id.equals(id)) {
    			currClass = c;
    		}
    	}
    }

    @Override
    public void exitClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
    	currClass = null;
    }
    
    @Override
    public void enterMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
    	String id = ctx.ID().toString();
    	String parentClassId = ctx.getParent().getChild(1).toString();
    	for (Class c : prog.classes) {
    		if (c.id.equals(parentClassId)) {
    			for (Method method : c.methods) {
    				if (method.id.equals(id)) {
    					currMethod = method;
    				}
    			}
    		}
    	}
    }

    @Override
    public void exitMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
    	currMethod = null;
    }

    @Override
    public void enterVardecl(MiniJavaGrammarParser.VardeclContext ctx) {

    }

    @Override
    public void exitVardecl(MiniJavaGrammarParser.VardeclContext ctx) {

    }

    @Override
    public void enterFormallist(MiniJavaGrammarParser.FormallistContext ctx) {

    }

    @Override
    public void exitFormallist(MiniJavaGrammarParser.FormallistContext ctx) {

    }

    @Override
    public void enterFormalrest(MiniJavaGrammarParser.FormalrestContext ctx) {

    }

    @Override
    public void exitFormalrest(MiniJavaGrammarParser.FormalrestContext ctx) {

    }

    @Override
    public void enterType(MiniJavaGrammarParser.TypeContext ctx) {

    }

    @Override
    public void exitType(MiniJavaGrammarParser.TypeContext ctx) {

    }
    
    // Gets list of leaf nodes as Token type
    public static List<Token> getTerminalNodes(ParseTree tree) {
    	List<Token> tokens = new ArrayList<Token>();
    	traverseTree(tokens, tree);
    	return tokens;
    }
    
    // Traverses the parse tree in order to make list of leaf nodes
    public static void traverseTree(List<Token> tokens, ParseTree parent) {
    	if (parent != null) {
    		for (int i = 0; i < parent.getChildCount(); i++) {
    			ParseTree child = parent.getChild(i);
    			if (child instanceof TerminalNode) {
    				TerminalNode leaf = (TerminalNode) child;
    				tokens.add(leaf.getSymbol());
    			} else {
    				traverseTree(tokens, child);
    			}
    		}
    	}
    }
    
    // Converts list of Tokens into an array of string objects
    public static List<String> tokenListToStringList(List<Token> tokenList) {
    	List<String> stringList = new ArrayList<String>();
    	for (Token token : tokenList) {
    		String tokenString = token.getText();
    		stringList.add(tokenString);
    	}
    	return stringList;
    }
    
    // Write method to search method and class to check if variable use is in scope
    public boolean inScope(Variable variable) {
    	if ((currClass.variables.contains(variable)) || (currMethod.localVariables.contains(variable))) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //write method to get type of variable from symbol table
    public String getType(String varId) {
    	for (Class c : prog.classes) {
    		if (c.id.equals(varId)) {
        		return "class";
        	}
    		for (Variable v : c.variables) {
    			if (v.id.equals(varId)) {
    				return v.type;
    			}
    		}
    		for (Method m : c.methods) {
    			if (m.id.equals(varId)) {
    				return "method";
    			}
    			for (Variable v : m.localVariables) {
    				if (v.id.equals(varId)) {
    					return v.type;
    				}
    			}
    		}
    	}
    	return "Variable not found!";
    }
    
    // after each type error, still return expected type so type checking can continue!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
    /*public String resolveType(ParseTree expression) {
    	if (expression.getChildCount() == 1) {
    		// if Inetger.parseInt works then return int (use try/catch block)
    		// if true or false return boolean
        	// if "this" continue
        	// else lookup ID in symbol table and return ID.type
    		
    		try {
    			int strInt = Integer.parseInt(expression.getChild(0).toString());
    			return "int";
    		} catch (Exception e) {
    			
    		}
    		
    		if ((expression.getChild(0).toString().equals("true")) || (expression.getChild(0).toString().equals("false"))) {
    			return "boolean";
    		} else if (expression.getChild(0).toString().equals("this")) {
    			//trying to access class variable in currClass
    		} else {
    			String id = expression.getChild(0).toString();
    			return getType(id);
    		}
    		
    	} else if (expression.getChildCount() == 2) {
    		// expr will recurse on expression.getChild(1) and should return a boolean
    		if (resolveType(expression.getChild(1)).equals("boolean")) {
    			return "boolean";
    		} else {
    			System.out.println("Type error 1! Expected boolean but got " + resolveType(expression.getChild(1)));
    			System.out.println(expression.getText());
    			System.out.println(expression.getChildCount());
    		}
    	} else if (expression.getChildCount() == 3) {
    		// if expression.getChild(1) is "." then recurse on expression.getChild(0) expecting an int[] type
    		// if getChild(0) is "(" and getChild(2) is ")" then recurse on getChild(1)
    		// if getChild(1) is "&&", "<", "+", "-", or "*" then recurse on getChild(0) and getChild(2) expecting bool or int depending on operator
    		
    		if (expression.getChild(1).toString().equals(".")) {
    			if (resolveType(expression.getChild(0)).equals("int[]")) {
    				return "int[]";
    			} else {
    				System.out.println("Type error 2! Expected int[] but got " + resolveType(expression.getChild(0)));
    			}
    		} else if ((expression.getChild(0).toString().equals("(")) && (expression.getChild(2).toString().equals(")"))) {
    			return resolveType(expression.getChild(1));
    		} else if (expression.getChild(1).toString().equals("&&")) {
    			if ((resolveType(expression.getChild(0)).equals("boolean")) && (resolveType(expression.getChild(2)).equals("boolean"))) {
    				return "boolean";
    			} else {
    				System.out.println("Type error 3! Expected boolean but got " + resolveType(expression.getChild(0)) + " and " + resolveType(expression.getChild(2)));
    			}
    		} else if (expression.getChild(1).toString().equals("<")) {
    			if ((resolveType(expression.getChild(0)).equals("int")) && (resolveType(expression.getChild(2)).equals("int"))) {
    				return "boolean";
    			} else {
    				System.out.println("Type error 4! Expected int but got " + resolveType(expression.getChild(0)) + " and " + resolveType(expression.getChild(2)));
    			}
    		} else if ((expression.getChild(1).toString().equals("+")) || (expression.getChild(1).toString().equals("-")) || expression.getChild(1).toString().equals("*")) {
    			if ((resolveType(expression.getChild(0)).equals("int")) && (resolveType(expression.getChild(2)).equals("int"))) {
    				return "int";
    			}
    		} else {
    			System.out.println("Unidentified tokens encountered 1!");
    			System.out.println(expression.getText());
    			System.out.println(expression.getChildCount());
    		}
    	} else if (expression.getChildCount() == 4) {
    		// if getChild(1) is "[" and getChild(3) is "]" then recurse on getChild(0) expecting int[] and recurse on getChild(2) expecting int... return an int[] type if all goes well
    		// if getChild(0) is "new" then look in symboltable for getChild(1) expecting Class type
    		
    		if ((expression.getChild(1).toString().equals("[")) && (expression.getChild(3).toString().equals("]"))) {
    			if ((resolveType(expression.getChild(0)).equals("int[]")) && (resolveType(expression.getChild(2)).equals("int"))) {
    				return "int";
    			} else {
    				System.out.println("Type error 5! Expected int[] and int but got " + resolveType(expression.getChild(0)) + "and " + resolveType(expression.getChild(2)));
    			}
    		} else if (expression.getChild(0).toString().equals("new")) {
    			String id = expression.getChild(1).toString();
    			if (getType(id).equals("class")) {
    				return "class";
    			} else {
    				System.out.println("Type error 6! Expected class but got " + getType(id));
    			}
    		} else {
    			System.out.println("Unidentified tokens encountered 2!");
    		}
    	} else if (expression.getChildCount() == 5) {
    		// if getChild(0) is "new" recurse on getChild(3) expecting an int returning int[]
    		// if getChild(1) is "." check getChild(0) expecting Class type
    		
    		if (expression.getChild(0).toString().equals("new")) {
    			if (resolveType(expression.getChild(3)).equals("int")) {
    				return "int[]";
    			} else {
    				System.out.println("Type error 7! Expected int but got " + resolveType(expression.getChild(3)));
    			}
    		} else if (expression.getChild(1).toString().equals(".")) {
    			if (getType(expression.getChild(0).toString()).equals("class")) {
    				return "class";
    			} else {
    				System.out.println("Type error 8! Expected class but got " + getType(expression.getChild(0).toString()));
    			}
    		} else {
    			System.out.println("Unidentified tokens encountered 3!");
    		}
    	} else if (expression.getChildCount() > 5) {
    		// recurse on getChild(0) expecting Class type and getChild(2) should be "method" and go through getChild(4) which is params checking type against symbol table
    		if (expression.getChild(1).toString().equals(".")) {
    			if ((resolveType(expression.getChild(0)).equals("class")) && (resolveType(expression.getChild(2)).equals("method"))) {
    				//check here if method id exists in class scope
    				
    				String classId = expression.getChild(0).toString();
    				String methodId = expression.getChild(2).toString();
    				String methodType = "";
    				int expectedParamsNum = -1;
    				String[] expectedParamsType = new String[expectedParamsNum];
    				for (Class c : prog.classes) {
    					if (c.id.equals(classId)) {
    						for (Method m : c.methods) {
    							if (m.id.equals(methodId)) {
    								expectedParamsNum = m.parameters.size();
    								methodType = m.resultType;
    							}
    							for (int i = 0; i < m.parameters.size(); i++) {
    								expectedParamsType[i] = m.parameters.get(i).type;
    							}
    						}
    					}
    				}
    				if (expectedParamsNum == -1) {
    					System.out.println("Method with id " + methodId + " not found in class " + classId);
    				} else {
    					if (((expression.getChild(4).getChildCount() + 1) / 2) != expectedParamsNum) {
    						System.out.println("Error: " + methodId + " call has wrong number of parameters");
    					} else {
    						for (int i = 0; i < (expression.getChild(4).getChildCount() + 1) / 2; i++) {
        						// if getType != expected\
    							if (!resolveType(expression.getChild(4).getChild(i)).equals(expectedParamsType[i])) {
    								System.out.println("Type error 9!");
    							}
        					}
    					}
    				}
    				return methodType;
    			}
    		} else {
    			System.out.println("Unidentified tokens encountered 4!");
    		}
    	} else {
    		// this shouldn't be reachable code
    		System.out.println("Something went wrong!");
    	}
    	return "Oops";
    }*/
    
    public String resolveType(ParseTree expression) {
    	if (expression.getChildCount() == 1) { // base cases
    		try {
    			int strInt = Integer.parseInt(expression.getChild(0).toString());
    			return "int";
    		} catch (Exception e) {
    			
    		}
    		
    		if ((expression.getChild(0).toString().equals("true")) || (expression.getChild(0).toString().equals("false"))) {
    			return "boolean";
    		} else if (expression.getChild(0).toString().equals("this")) {
    			//trying to access class variable in currClass
    			return "class";
    		} else {
    			return getType(expression.getChild(0).toString());
    		}
    	} else if (expression.getChildCount() == 2)  { // NOT expr branch
    		return "boolean";
    	} else if (expression.getChildCount() == 3) {
    		if (expression.getChild(2).toString().equals("length")) { // expr DOT LENGTH branch
    			return "int";
    		} else if (expression.getChild(0).toString().equals("(")) { // LPAREN expr RPAREN branch
    			return resolveType(expression.getChild(1));
    		} else if ((expression.getChild(1).toString().equals("&&")) || (expression.getChild(1).toString().equals("<"))) { // expr op expr branch with boolean result type
    			return "boolean";
    		} else if ((expression.getChild(1).toString().equals("+")) || (expression.getChild(1).toString().equals("-")) || (expression.getChild(1).toString().equals("*"))) { // expr op expr branch with int result type
    			return "int";
    		} else {
    			return "oops";
    		}
    	} else if (expression.getChildCount() == 4) {
    		if (expression.getChild(1).toString().equals("[")) { // expr LSQUARE expr RSQUARE branch
    			return "int";
    		} else if (expression.getChild(0).toString().equals("new")) { // NEW ID LPAREN RPAREN branch
    			return "class";
    		} else {
    			return "oops";
    		}
    	} else if (expression.getChildCount() == 5) {
    		if (expression.getChild(0).toString().equals("new")) { // NEW INT LSQUARE expr RSQUARE branch
    			return "int[]";
    		} else if (expression.getChild(1).toString().equals(".")) { // expr DOT ID LPAREN exprlist? RPAREN branch
    			String classId = "";
    			if (expression.getChild(0).getChild(0).toString().equals("this")) {
    				classId = currClass.id;
    			} else {
    				classId = expression.getChild(0).getChild(0).toString();
    			}
    			for (Class c : prog.classes) {
    				if (c.id.equals(classId)) {
    					for (Method m : c.methods) {
    						if (m.id.equals(expression.getChild(2).toString())) {
    							return m.resultType;
    						}
    					}
    				}
    			}
    		} else {
    			return "oops";
    		}
    	} else if (expression.getChildCount() == 6) { // expr DOT ID LPAREN exprlist? RPAREN branch
    		String classId = "";
			if (expression.getChild(0).getChild(0).toString().equals("this")) {
				classId = currClass.id;
			} else {
				classId = expression.getChild(0).getChild(0).toString();
			}
			for (Class c : prog.classes) {
				if (c.id.equals(classId)) {
					for (Method m : c.methods) {
						if (m.id.equals(expression.getChild(2).toString())) {
							return m.resultType;
						}
					}
				}
			}
    	} else {
    		return "oops";
    	}
    	return "oops";
    }	

    @Override
    public void enterStatement(MiniJavaGrammarParser.StatementContext ctx) {
    	
    	if (ctx.IF() != null) { // Case 2 - If statement
    		//System.out.println("Reached If Statement");
    		//System.out.println(tokenListToStringList(getTerminalNodes(ctx.expr(0))));
    		//System.out.println(resolveType(ctx.getChild(2)));
    	} else if (ctx.WHILE() != null) { // Case 3 - While loop
    		//System.out.println("Reached While Loop");
    		//System.out.println(tokenListToStringList(getTerminalNodes(ctx.expr(0))));
    		//System.out.println(resolveType(ctx.getChild(2)));
    	} else if(ctx.SYSTEMOUT() != null) { // Case 4 - Print statement
    		//System.out.println("Reached Print Statement");
    		//System.out.println(tokenListToStringList(getTerminalNodes(ctx.expr(0))));
    		//System.out.println(resolveType(ctx.getChild(2)));
    	} else if ((ctx.ID() != null) && (ctx.EQUALS() != null) && (ctx.LSQUARE() == null)) { // Case 5 - Variable setter
    		//System.out.println("Reached Variable Setter");
    		//System.out.println(tokenListToStringList(getTerminalNodes(ctx.expr(0))));
    		//System.out.println(resolveType(ctx.getChild(2)));
    	} else if ((ctx.LSQUARE() != null) && (ctx.EQUALS() != null)) { // Case 6 - Array element setter
    		//System.out.println("Reached Array Element Setter");
    		//System.out.println(tokenListToStringList(getTerminalNodes(ctx.expr(0))));
    		//System.out.println(tokenListToStringList(getTerminalNodes(ctx.expr(1))));
    		//System.out.println(resolveType(ctx.getChild(2)));
    		//System.out.println(resolveType(ctx.getChild(5)));
    	} else { // Case 1 - Statement in parentheses
    		//System.out.println("Reached Statement in Parentheses");
    		//resolveType(ctx.getChild(1));
    	}
    	
    }

    @Override
    public void exitExpr(MiniJavaGrammarParser.ExprContext ctx) {
    	
    }

    @Override
    public void enterOp(MiniJavaGrammarParser.OpContext ctx) {

    }

    @Override
    public void exitOp(MiniJavaGrammarParser.OpContext ctx) {

    }

    @Override
    public void enterExprlist(MiniJavaGrammarParser.ExprlistContext ctx) {

    }

    @Override
    public void exitExprlist(MiniJavaGrammarParser.ExprlistContext ctx) {

    }

    @Override
    public void enterExprrest(MiniJavaGrammarParser.ExprrestContext ctx) {

    }

    @Override
    public void exitExprrest(MiniJavaGrammarParser.ExprrestContext ctx) {

    }
}

