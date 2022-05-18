package symboltable;

import java.util.List;
import java.util.ArrayList;

public class Class {
	
	public String id;
	public List<Variable> variables;
	public List<Method> methods;
	
	public Class(String id) {
		this.id = id;
		this.variables = new ArrayList<Variable>();
		this.methods = new ArrayList<Method>();
	}
	
	public void addVariable(Variable variable) {
		variables.add(variable);
	}
	
	public void addMethod(Method method) {
		methods.add(method);
	}
	
	@Override
	public String toString() {
		return id;
	}

}
