package minijava;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import symboltable.Program;
import symboltable.Class;
import symboltable.Method;
import symboltable.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MiniJavaListener extends MiniJavaGrammarBaseListener {

	public static Program prog;
    MiniJavaGrammarParser parser;

    public void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }
    
    public static Program getProgram() {
    	return prog;
    }


    public MiniJavaListener(MiniJavaGrammarParser parse) {
        this.parser = parser;
    }

    @Override
    public void enterProgram(MiniJavaGrammarParser.ProgramContext ctx) {
        // create a scope for the program
        //System.out.println("Entered program");
        //System.out.println(ctx);
        
        prog = new Program();
    }


    @Override
    public void exitProgram(MiniJavaGrammarParser.ProgramContext ctx) {
    	//System.out.println("Exited program");
    	//System.out.println(ctx);
    	
    	//System.out.println("Done!");
    	
    	/*
    	System.out.println("\nClasses:");
    	for (Class c : prog.classes) {
    		System.out.println(c.id);
    	}
    	System.out.println("Count = " + prog.classes.size());
    	
    	System.out.println("\nMethods:");
    	int methodCount = 0;
    	for (Class c : prog.classes) {
    		System.out.println(c.id + ":" + "(" + c.methods.size() + ")");
    		for (Method m : c.methods) {
    			System.out.println(m.id + " with type " + m.resultType);
    			methodCount++;
    		}
    	}
    	System.out.println("Count = " + methodCount);
    	
    	System.out.println("\nVariables:");
    	int varCount = 0;
    	for (Class c : prog.classes) {
    		System.out.println(c.id + ":" + "(" + c.variables.size() + ")");
    		for (Variable v : c.variables) {
    			System.out.println(v.id + " with type " + v.type);
    		}
    		for (Method m : c.methods) {
    			System.out.println(m.id + ":" + "(" + m.localVariables.size() + ")");
    			for (Variable v : m.localVariables) {
    				System.out.println(v.id + " with type " + v.type);
    				varCount++;
    			}
    		}
    	}
    	System.out.println("Count = " + varCount);
    	*/
    	
    	System.out.println("Symbol Table:");
    	for (Class c : prog.classes) {
    		System.out.println("\nClass " + c.id + ":");
    		for (Variable v : c.variables) {
    			System.out.println(v.id + " (" + v.type + ")");
    		}
    		for (Method m : c.methods) {
    			System.out.println("Method: " + m.id + " (" + m.resultType + ")");
    			for (Variable v : m.localVariables) {
    				System.out.println(v.id + " (" + v.type + ")");
    			}
    		}
    	}
    	
    }

    @Override
    public void enterMainclass(MiniJavaGrammarParser.MainclassContext ctx) {
        //System.out.println("Entered main class");
        //System.out.println(ctx);

        Class mainClass = new Class("main");
        prog.addClass(mainClass);
        //System.out.println("Added main class to program");
    }

    @Override
    public void exitMainclass(MiniJavaGrammarParser.MainclassContext ctx) {
    	//System.out.println("Exited main class");
    	//System.out.println(ctx);
    }

    @Override
    public void enterClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {/////////////////////////////
    	//System.out.println("Entered class declaration");
    	//System.out.println(ctx);
    	
    	String id = ctx.getChild(1).toString();
    	Class newClass = new Class(id);
    	prog.addClass(newClass);
    	//System.out.println("Added class "  + id + " to program");
    }

    @Override
    public void exitClassdecl(MiniJavaGrammarParser.ClassdeclContext ctx) {
    	//System.out.println("Exited class declaration");
    	//System.out.println(ctx);
    }
    
    @Override
    public void enterMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {///////////////////////////
    	//System.out.println("Entered method declaration");
    	//System.out.println(ctx);
    	
    	String id = ctx.ID().toString();
    	List<Variable> params = new ArrayList<Variable>();
    	//System.out.println("Type should be: " + ctx.getChild(4).getChild(0).getChild(0).toString());
    	//System.out.println("Variable name should be: " + ctx.getChild(4).getChild(1).toString());
    	if (!ctx.getChild(4).toString().equals(")")) {
    		Variable tempParam = new Variable(ctx.getChild(4).getChild(1).toString(), ctx.getChild(4).getChild(0).getChild(0).toString());
    		params.add(tempParam);
    		if (ctx.getChild(4).getChildCount() > 2) {
    			for (int i = 1; i < ctx.getChild(4).getChild(2).getChildCount(); i=i+3) {
    				tempParam = new Variable(ctx.getChild(4).getChild(2).getChild(i+1).toString(), ctx.getChild(4).getChild(2).getChild(i).getChild(0).toString());
    				params.add(tempParam);
    			}
    		}
    	}
    	String resultType = ctx.getChild(1).getChild(0).toString();
    	String parentClassId = ctx.getParent().getChild(1).toString();
    	Class parentClass = new Class("temp");
    	for (Class c : prog.classes) {
    		if (c.id.equals(parentClassId)) {
    			parentClass = c;
    		}
    	}
    	Method newMethod = new Method(id, params, resultType, parentClass);
    	prog.addMethod(newMethod);
    	for (int j = 0; j < prog.classes.size(); j++) {
    		if (prog.classes.get(j).id.equals(parentClassId)) {
    			prog.classes.get(j).addMethod(newMethod);
    			break;
    		}
    	}
    	//system.out.println("Added method " + id + " to class " + parentClassId);
    	
    }

    @Override
    public void exitMethoddecl(MiniJavaGrammarParser.MethoddeclContext ctx) {
    	//System.out.println("Exited method declaration");
    	//System.out.println(ctx);
    }

    @Override
    public void enterVardecl(MiniJavaGrammarParser.VardeclContext ctx) {/////////////////////////////////
    	//System.out.println("Entered variable declaration");

    	String id = ctx.getChild(1).toString();
    	String type = ctx.getChild(0).getChild(0).toString();
    	Variable newVariable = new Variable(id, type);
    	// String parentId = ctx.getParent().getChild(1).toString(); for class variable
    	// String parentId = ctx.getParent().getChild(2).toString(); for method variable

    	if (ctx.getParent().getChild(0).toString().equals("class")) {	// Case: add to class
    		String parentClassId = ctx.getParent().getChild(1).toString();
    		for (int i = 0; i < prog.classes.size(); i++) {
    			if (prog.classes.get(i).id.equals(parentClassId)) {
    				boolean identifierDeclared = false;
    				for (Variable v : prog.classes.get(i).variables) {
    					if (v.id.equals(id)) {
    						identifierDeclared = true;
    					}
    				}
    				if (!identifierDeclared) {
    					newVariable.setParentClass(prog.classes.get(i));
    					prog.classes.get(i).addVariable(newVariable);
        				break;
    				} else {
    					printError("Error: An identifier with id '" + id + "' has already been declared.");
    				}
    			}
    		}
    		//system.out.println("Added variable " + id + " with type " + type + " to class " + parentClassId);
    	} else {	// Case: add to method
    		String parentMethodId = ctx.getParent().getChild(2).toString();
    		String parentClassId = ctx.getParent().getParent().getChild(1).toString();
    		for (int i = 0; i < prog.classes.size(); i++) {	// For each Class in prog
    			//System.out.println("Curr class id " + prog.classes.get(i).id + " looking for " + ("[" + parentClassId + "]"));
    			if (prog.classes.get(i).id.equals(parentClassId)) {
    				boolean identifierDeclared = false;
    				for (Variable v : prog.classes.get(i).variables) {
    					if (v.id.equals(id)) {
    						identifierDeclared = true;
    					}
    				}
    				for (Method method : prog.classes.get(i).methods) {	// For each method in the class
    					if (method.id.equals(parentMethodId)) {
    						for (Variable v : method.localVariables) {
    	    					if (v.id.equals(id)) {
    	    						identifierDeclared = true;
    	    					}
    	    				}
    						if (!identifierDeclared) {
    							newVariable.setParentClass(prog.classes.get(i));
    							newVariable.setParentMethod(method);
    							method.addLocalVariable(newVariable);
    							break;
        						//system.out.println("Added variable " + id + " with type " + type + " to method " + parentMethodId + " in class " + parentClassId);
    						} else {
    							printError("Error: An identifier with id '" + id + "' has already been declared.");
    						}
    					}
    				}
    			}
    		}
    	} 
    }

    @Override
    public void exitVardecl(MiniJavaGrammarParser.VardeclContext ctx) {
    	//System.out.println("Exited variable declaration");
    	//System.out.println(ctx);
    }

    @Override
    public void enterFormallist(MiniJavaGrammarParser.FormallistContext ctx) {
    	//System.out.println("Entered formal list");
    	//System.out.println(ctx);
    }

    @Override
    public void exitFormallist(MiniJavaGrammarParser.FormallistContext ctx) {
        //System.out.println("Exited formal list");
        //System.out.println(ctx);
    }

    @Override
    public void enterFormalrest(MiniJavaGrammarParser.FormalrestContext ctx) {
    	//System.out.println("Entered formal rest");
    	//System.out.println(ctx);
    }

    @Override
    public void exitFormalrest(MiniJavaGrammarParser.FormalrestContext ctx) {
        //System.out.println("Exited formal rest");
        //System.out.println(ctx);
    }

    @Override
    public void enterType(MiniJavaGrammarParser.TypeContext ctx) {
    	//System.out.println("Entered type");
    	//System.out.println(ctx);
    }

    @Override
    public void exitType(MiniJavaGrammarParser.TypeContext ctx) {
    	//System.out.println("Exited type");
    	//System.out.println(ctx);
    }

    @Override
    public void enterStatement(MiniJavaGrammarParser.StatementContext ctx) {
    	//System.out.println("Entered statement");
    	//System.out.println(ctx);
    }

    @Override
    public void exitExpr(MiniJavaGrammarParser.ExprContext ctx) {
    	//System.out.println("Exited expression");
    	//System.out.println(ctx);
    }

    @Override
    public void enterOp(MiniJavaGrammarParser.OpContext ctx) {
    	//System.out.println("Entered operation");
    	//System.out.println(ctx);
    }

    @Override
    public void exitOp(MiniJavaGrammarParser.OpContext ctx) {
    	//System.out.println("Exited operation");
    	//System.out.println(ctx);
    }

    @Override
    public void enterExprlist(MiniJavaGrammarParser.ExprlistContext ctx) {
    	//System.out.println("Entered expression list");
    	//System.out.println(ctx);
    }

    @Override
    public void exitExprlist(MiniJavaGrammarParser.ExprlistContext ctx) {
    	//System.out.println("Exited expression list");
    	//System.out.println(ctx);
    }

    @Override
    public void enterExprrest(MiniJavaGrammarParser.ExprrestContext ctx) {
    	//System.out.println("Entered expression rest");
    	//System.out.println(ctx);
    }

    @Override
    public void exitExprrest(MiniJavaGrammarParser.ExprrestContext ctx) {
    	//System.out.println("Exited expression rest");
    	//System.out.println(ctx);
    }
}

