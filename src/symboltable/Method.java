package symboltable;

import java.util.ArrayList;
import java.util.List;

public class Method {

	public String id;
	public List<Variable> parameters;
	public String resultType;
	public Class parentClass;
	public List<Variable> localVariables;
	
	public Method(String id, List<Variable> parameters, String resultType, Class parentClass) {
		this.id = id;
		this.parameters = parameters;
		this.resultType = resultType;
		this.parentClass = parentClass;
		this.localVariables = new ArrayList<Variable>();
	}
	
	
	public void addLocalVariable(Variable variable) {
		localVariables.add(variable);
	}
	
	@Override
	public String toString() {
		return id;
	}
	
}
