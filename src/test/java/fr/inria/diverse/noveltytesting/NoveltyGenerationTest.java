package fr.inria.diverse.noveltytesting;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

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
 * 
 * 
 * @author mboussaa
 *
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

		this.myPackage = "org.apache.commons.math3.analysis";
		this.myInterface = "org.apache.commons.math3.analysis.UnivariateFunction";
		this.myCompiledClasses = "/Users/mboussaa/Documents/workspace/novelty-tester/target/classes/org/apache/commons/math3";
		this.popSize = 100;
		this.numberGenerations = 1000;
		this.limit = 100000;
		this.k = popSize/2;
		this.threshold =20;

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

			engine.evaluate(pop);
			
			engine.bestCoverage(pop);
			
			//pop.accept(visitor);
       	 
			engine.geneticProcess(pop);
			
			engine.generateNewData(pop);

		}
		
		System.out.println("maaaaaaaax "+engine.getBestCoverage().getCoverageRatio());
	}
}
