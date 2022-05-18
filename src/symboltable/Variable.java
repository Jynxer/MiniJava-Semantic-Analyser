package symboltable;

public class Variable {

	public String id;
	public String type;
	public Class parentClass;
	public Method parentMethod = null;
	
	public Variable(String id, String type) {
		this.id = id;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return id;
	}
	
	public void setParentClass(Class parentClass) {
		this.parentClass = parentClass;
	}
	
	public void setParentMethod(Method parentMethod) {
		this.parentMethod = parentMethod;
	}
	
}
