package symboltable;

import java.util.ArrayList;
import java.util.List;

public class Program {

	public List<Class> classes;
	public List<Method> methods;
	
	public Program() {
		this.classes = new ArrayList<>();
		this.methods = new ArrayList<>();
	}
	
	public void addClass(Class c) {
		classes.add(c);
	}
	
	public void addMethod(Method m) {
		methods.add(m);
	}
	
}