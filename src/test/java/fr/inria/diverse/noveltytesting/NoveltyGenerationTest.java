package fr.inria.diverse.noveltytesting;

import java.io.IOException;
import java.util.List;

import fr.inria.diverse.noveltytesting.classfinder.ClassFinder;
import fr.inria.diverse.noveltytesting.classfinder.ClassFinderImpl;
import fr.inria.diverse.noveltytesting.model.ImplementedClass;
import fr.inria.diverse.noveltytesting.model.Interface;
import fr.inria.diverse.noveltytesting.model.Method;
import fr.inria.diverse.noveltytesting.model.Population;
import fr.inria.diverse.noveltytesting.noveltyengine.NoveltyEngine;
import fr.inria.diverse.noveltytesting.noveltyengine.NoveltyEngineImpl;
import fr.inria.diverse.noveltytesting.visitor.InputOutputVisitor;
import fr.inria.diverse.noveltytesting.visitor.Visitor;

import org.junit.Before;
import org.junit.Test;

/**
 * each client instantiates the engine by giving as a parameter the services'
 * interface, the pop and the archive size and then apply the different services
 * of the novelty algorithm
 * 
 * Unit test
 */
public class NoveltyGenerationTest {

	private String myPackage;
	private String myInterface;
	private String myCompiledClasses;
	private int popSize;
	private int numberGenerations;
	private int k;
	private int limit;
	private double threshold;
	private int l = 0;

	private ClassFinder cf = new ClassFinderImpl();
	private NoveltyEngine engine;


	@Before
	public void testBefore() throws ClassNotFoundException, IOException,
			InstantiationException, IllegalAccessException {

		this.myPackage = "fr.inria.diverse.noveltytesting.sut";
		this.myInterface = "fr.inria.diverse.noveltytesting.sut.Functions";
		this.myCompiledClasses = "/Users/mboussaa/Documents/workspace/novelty-tester/target/classes/fr/inria/diverse/noveltytesting/sut";
		this.popSize = 50;
		this.numberGenerations = 2;
		this.limit = 10000;
		this.k = 50;
		this.threshold = 30;

		List<Class<?>> implementedClasses = cf.findClassesPerInterface(
				cf.findTargetInterface(myInterface, myPackage), myPackage);
		for (Class<?> implemented : implementedClasses) {
			System.out.println("Implemented class : " + implemented.getName());
		}

	}

	/**
	 * we generate test data for the first discovered services interface
	 */
	@Test
	public void testTestClass() throws Exception {

		Class<?> targetInterface = cf.findTargetInterface(myInterface,myPackage);
		
		engine = new NoveltyEngineImpl(limit, k, threshold, popSize, myPackage,targetInterface,myCompiledClasses);

		Population pop = engine.generatePopulation();

		engine.generateData(pop);

		Visitor visitor = new InputOutputVisitor();
		for (int i = 0; i < this.numberGenerations; i++) {
			engine.executeMethods(pop);
			pop.accept(visitor);
			engine.evaluate(pop);
			engine.geneticProcess(pop);
			pop.accept(visitor);
			engine.generateData(pop);
		}
	}
}
