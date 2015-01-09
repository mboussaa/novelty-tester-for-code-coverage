package fr.inria.diverse.noveltytesting.geneticoperators;

import fr.inria.diverse.noveltytesting.model.Population;

/**
 * The first step in calculating the novelty of a new individual is to measure
 * its behavioral distance to all other individuals in the population and to all
 * individuals in the archive, reflecting how different it is from current
 * behaviors (the current population) as well as behaviors that were novel in
 * the past (the archive).
 * 
 * To do so, we calculate the novelty metric (behaviour) and we assign it to
 * each interface of the population to handle the selection process
 * 
 * @author mboussaa
 *
 */
public class Evaluation implements Operator {

    private Population archive;
    private int k;

    public Evaluation(Population archive, int k) {
        this.archive = archive;
        this.k = k;
    }

    @Override
    public void process(Population population) {

        population.getInterfaces().forEach(i -> i.processNoveltyMetric(population, archive, k));
    }
}