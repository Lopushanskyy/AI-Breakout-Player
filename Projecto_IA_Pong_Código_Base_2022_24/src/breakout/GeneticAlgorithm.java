package breakout;

import utils.Commons;

 

public class GeneticAlgorithm {
	
	private final int POPULATION_SIZE = 100;
	private final int NUM_GENERATIONS = 1000;
	private final double MUTATION_RATE = 0.05;
	double[] individual = new double[52];
	
	

	public GeneticAlgorithm() {
		generatePopulation();
	}
	
	
}


