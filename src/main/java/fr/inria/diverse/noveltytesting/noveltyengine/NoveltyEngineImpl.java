package fr.inria.diverse.noveltytesting.noveltyengine;

import fr.inria.diverse.noveltytesting.geneticoperators.*;
import fr.inria.diverse.noveltytesting.model.Interface;
import fr.inria.diverse.noveltytesting.model.Population;
import fr.inria.diverse.noveltytesting.modelgeneration.ModelGenerator;
import fr.inria.diverse.noveltytesting.modelgeneration.ModelGeneratorImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Includes basic processes for the execution of the novelty algorithm mainly :
 * 
 * generateInitialPopulation : generate models + data
 * 
 * executeMethods : execute models + update outputs
 * 
 * evaluateSolutions : set the novelty metric
 * 
 * displayPopulation : display on console
 * 
 * applyGeneticOperators : apply selection, mutation and crossover on current
 * population
 * 
 * generateNextPopulation : generate new interfaces in order to fulfill the
 * current population after having remove non-novel interfaces
 * 
 * 
 * @author mboussaa
 *
 *
 */

public class NoveltyEngineImpl implements NoveltyEngine  {

    private ModelGenerator gen;
    private Operator selection;
    private Operator mutation;
    private Operator crossover;
    private Operator evaluation;
    private Population archive;
    private int limit;
    private int k;
    private double threshold;
    private int popSize;
    private String myPackage;
    private String myCompiledClasses;
    private Class<?> targetInterface;
    
	/**
	 * Set Novelty Search parameters values
	 *
	 * @param myPackage
	 *            Package Under Test
	 * @param myInterface
	 *            Interface Under Test
	 * @param myCompiledClasse
	 * 			  .class folder
	 * @param popSize
	 *            Single population size
	 * @param numberGenerations
	 *            number of generations
	 * @param limit
	 *            The maximum size of the archive of past discovered solutions
	 * @param k
	 *            To be used in calculating the distance from k nearest
	 *            neighbors
	 * @param threshold
	 *            For how novel an example has to be before it will be added the
	 *            archive
	 */
    
	public NoveltyEngineImpl(int limit, int k, double threshold,
			int popSize, String myPackage, Class<?> targetInterface, String myCompiledClasses) {
        this.gen = new ModelGeneratorImpl();
        this.archive = new Population(limit);
        this.selection = new Selection(archive,threshold);
        this.mutation = new Mutation();
        this.crossover = new Crossover();
        this.evaluation = new Evaluation(archive, k);
        
        this.limit = limit;
        this.k = k;
        this.threshold = threshold;
        this.popSize = popSize;
        this.myPackage = myPackage;
        this.myCompiledClasses = myCompiledClasses;
        this.targetInterface = targetInterface;
    }


	@Override
    public void setExclusionPattern(String pattern) {
        this.gen.setExclusionPattern(pattern);
    }

    @Override
    public Population getArchive() {
        return archive;
    }

    @Override
    public Population generatePopulation() throws Exception {

        Population population = new Population(popSize);
        population.setSourcePackage(myPackage);
        
        for (int i = 0; i < popSize; i++) {
            Interface anInterface = gen.generateModel(targetInterface,myPackage);
            population.addInterface(anInterface);
        }

        return population;
    }

    @Override
    public void generateData(Population population) {
        ModelGenerator gen = new ModelGeneratorImpl();
        population.getInterfaces().forEach(gen::generateData);
    }
    
    @Override
    public void generateNewData(Population population) throws InstantiationException, IllegalAccessException, IOException {
        ModelGenerator gen = new ModelGeneratorImpl();
        for (int i = 0; i < (popSize-threshold); i++) {
            Interface anInterface = gen.generateModel(targetInterface,myPackage);
            gen.generateData(anInterface);
            population.addInterface(anInterface);
            
        }
    }

    @Override
    public void executeMethods(Population population)
            throws IOException, Exception {
        for (Interface i : population.getInterfaces()) {
            gen.executeMethods(population.getSourcePackage(),i,myCompiledClasses);
        }
    }

    @Override
    public void geneticProcess(Population population) {
        selection.process(population);
        mutation.process(population);
        crossover.process(population);
    }

    @Override
    public void evaluate(Population population) {
        evaluation.process(population);
    }
}