package fr.inria.diverse.noveltytesting.model;

import fr.inria.diverse.noveltytesting.visitor.Visitable;
import fr.inria.diverse.noveltytesting.visitor.Visitor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * getRelevantModels() return The list of interfaces that have generated
 * incoherence in the outputs taking into account the fitness value
 * 
 * @author leiko
 *
 */

public class Population implements Visitable {

    public List<Interface> interfaces;
    private String sourcePackage;
    private int size;
    
    public Population(int limit) {
        this.interfaces = new ArrayList<>();
    	this.size = limit;
	}

	public String getSourcePackage() {
		return sourcePackage;
	}

	public void setSourcePackage(String sourcePackage) {
		this.sourcePackage = sourcePackage;
	}

	public Population() {
        this.interfaces = new ArrayList<>();
    }

	public List<Interface> getRelevantModels() {
		return this.interfaces.stream().filter(i -> i.getFitness() < 1)
				.collect(Collectors.toCollection(LinkedList::new));
	}

    public List<Interface> getInterfaces() {
        return this.interfaces;
    }

    public void removeInterface(Interface anInterface) {
        this.interfaces.remove(anInterface);
    }

    public void addInterface(Interface anInterface) {
        this.interfaces.add(anInterface);
    }

    public void addInterfaces(List<Interface> interfaces) {
        this.interfaces.addAll(interfaces);
    }
    
    public void setInterfaces(List<Interface> interfaces) {
        this.interfaces=interfaces;
    }

    @Override
    public void accept(Visitor visitor, boolean visitChildren, boolean isRecursive) {
        visitor.visit(this);
        if (visitChildren) {
            if (isRecursive) {
                interfaces.forEach(i -> i.accept(visitor, true, true));
            } else {
                interfaces.forEach(i -> i.accept(visitor, false, false));
            }
        }
    }

    @Override
    public void accept(Visitor visitor) {
        this.accept(visitor, true, true);
    }
}
