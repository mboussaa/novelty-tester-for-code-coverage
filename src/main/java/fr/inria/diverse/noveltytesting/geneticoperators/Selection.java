package fr.inria.diverse.noveltytesting.geneticoperators;

import fr.inria.diverse.noveltytesting.model.Population;

/**

* for each population we use the selection operator to select interfaces that
 * have a novelty metric < a threshold and then we remove the remaining
 * interfaces
 * 
 * we use to save for each selection process the interfaces with a fitness value
 * below 1 in order to display at the end of the algorithm the list of
 * interfaces that have generated anomalies or incoherence in the outputs.
 * 
 * as well, we save the selected interfaces in a shared population called
 * Archive. In fact, the archive remembers past individuals that were highly
 * novel when they were first discovered (originated).
 * 
 * This is useful to calculate the novelty metric of each interface in the next
 * populations (see evaluation doc for more information about the novelty
 * metric)
 * 
 */
public class Selection implements Operator {

    private double threshold;
    private Population archive;

    public Selection(Population archive, double threshold) {
        this.archive = archive;
        this.threshold = threshold;
    }

	@Override
	public void process(Population population) {
        population.getInterfaces().forEach(i -> {
            if (i.getNoveltyMetric() < threshold) {
                population.removeInterface(i);
            }
        });

        archive.addInterfaces(population.getInterfaces());
	}
}
