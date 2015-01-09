package fr.inria.diverse.noveltytesting.model;

import fr.inria.diverse.noveltytesting.visitor.Visitable;
import fr.inria.diverse.noveltytesting.visitor.Visitor;

import java.util.*;

/**
 * a method has a name, a return value type, a list of parameters and list of outputs from the different target platforms
 * 
 * we associate a list of outputs to each target platform 
 * 
 * getMethodFitness() : is a comparison metric that compares the different outputs 
 * 
 * Created by leiko on 16/10/14.
 */
public class ImplementedClass {
	
	public Class<?> classes;
    public List<Method> constructors;
    
    public ImplementedClass() {
		this.constructors = new LinkedList<>();;
	}

	public void addConstructor(Method c) {
        this.constructors.add(c);
    }

    public Class<?> getClasses() {
		return classes;
	}
    
	public List<Method> getConstructors() {
		return constructors;
	}
	
	public Method getRandomConstructor() {
		return getConstructors().get((int)(java.lang.Math.random() * (getConstructors().size())));
	}

	public void setConstructors(List<Method> constructors) {
		this.constructors = constructors;
	}


	public void setClasses(Class<?> classes) {
		this.classes = classes;
	}



  
}
