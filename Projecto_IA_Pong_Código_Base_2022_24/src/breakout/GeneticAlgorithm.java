package breakout;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import utils.Commons;

 

public class GeneticAlgorithm {
	
	
	private static final int INDIVIDUAL_LENGTH = (Commons.BREAKOUT_STATE_SIZE * NeuralNetwork.HIDDEN_DIM) + NeuralNetwork.HIDDEN_DIM + (NeuralNetwork.HIDDEN_DIM * Commons.BREAKOUT_NUM_ACTIONS) + Commons.BREAKOUT_NUM_ACTIONS;
	private static final int POPULATION_SIZE = 1000;
	private static final int NUM_GENERATIONS = 20;
	private static final double MUTATION_RATE = 0.05;
	private static final double SELECTION_PERCENTAGE = 0.1;
	
	private ArrayList<double[]> population = new ArrayList<>(POPULATION_SIZE);
	
	
	
	

	 GeneticAlgorithm () {
		 initialPopulation();
    }
	 
	 public double[] search() {
		 
		 double[]bestSolution = null;
		 int generation = 0;
		 
		 while (generation < NUM_GENERATIONS) {
			 
			 population.sort((a, b) -> Double.compare(getFitness(b), getFitness(a)));    

			 bestSolution = population.get(0);    
			 if(getFitness(bestSolution) > 400000.0) break;	
	        
		    ArrayList<double[]> newGeneration = new ArrayList<>(POPULATION_SIZE);

		    for (int i = 0; i < POPULATION_SIZE/2; i++) {  
		    	double[] parent1 = torneio();
		    	double[] parent2 = torneio();

		    	ArrayList<double[]> children = reproduce(parent1, parent2);

		    	for(double[] child : children) {
		    		mutate(child);
		    		newGeneration.add(child);
		    	}
		    	
		    }
		        
		    createNewPopulation(newGeneration);
		    generation++;
		    System.out.println("Generation: " + generation);
		    System.out.println("Best Fitness: " + getFitness(bestSolution));
		    
		    if (generation % 100 == 0) {
		    	valuesToTxt("indivtest.txt", population.get(0));
		    }
		 }

		    return bestSolution;
		}
	 
	 private void createNewPopulation(ArrayList<double[]> newGeneration) {
		 
		 	int top20Percent = (int) (POPULATION_SIZE * SELECTION_PERCENTAGE);
	        newGeneration.sort((a, b) -> Double.compare(getFitness(b), getFitness(a)));
	        ArrayList<double[]> topChildren = new ArrayList<>(newGeneration.subList(0, top20Percent));
		     
	        int topPreviousCount = (int) (POPULATION_SIZE * (1 - SELECTION_PERCENTAGE));
	        ArrayList<double[]> topPrevious = new ArrayList<>(population.subList(0, topPreviousCount));

		    population.clear(); 

		    for (double[] child : topChildren) {
		    	population.add(child);
		   	}
		    

		    for (double[] prev : topPrevious) {
		    	population.add(prev);
		    }
	 }
	
	
	 private double[] torneio(){
	        int random1 = (int) (Math.random() * POPULATION_SIZE);
	        int random2 = (int) (Math.random() * POPULATION_SIZE);
	        while (random2 == random1) {//se os indexes forem iguais tentar de novo
	            random2 = (int) (Math.random() * POPULATION_SIZE);
	        }
	        double [] indiv1 = population.get(random1);
	        double [] indiv2 = population.get(random2);

	        if(getFitness(indiv1) >= getFitness(indiv2)) return indiv1;
	        return indiv2;
	    }

	    private ArrayList<double[]> reproduce(double[] parent1, double[] parent2) {
	    	 ArrayList<double[]> children = new ArrayList<>(2);
	        int point = (int) (Math.random() * INDIVIDUAL_LENGTH) ;


	        double[] child1 = new double[INDIVIDUAL_LENGTH];
	        double[] child2 = new double[INDIVIDUAL_LENGTH];
	        
	        for (int i = 0; i < point; i++) {
	            child1[i] = parent1[i];
	            child2[i] = parent2[i];
	        }
	        for (int i = point; i < INDIVIDUAL_LENGTH; i++) {
	            child1[i] = parent2[i];
	            child2[i] = parent1[i];
	        }

	        children.add(child1);
	        children.add(child2);
	        return children;
	    }

	    private void mutate(double[] child){
	        int point = (int) (Math.random() * INDIVIDUAL_LENGTH);
	        double value = (Math.random() *2 ) - 1;
	        if(Math.random() < MUTATION_RATE)
	        	child[point] = value;   
	    }

	    
	    
	    private double getFitness(double[] individual) {
	        NeuralNetwork nn = new NeuralNetwork(Commons.BREAKOUT_STATE_SIZE, NeuralNetwork.HIDDEN_DIM , Commons.BREAKOUT_NUM_ACTIONS , individual);
	        BreakoutBoard b = new BreakoutBoard(nn, false, 2);
	        b.setSeed(2);
	        b.runSimulation();
	        return b.getFitness();
	    }
	    
	    
    
	
	    public static double[] createRandomIndividual() {
			double[] individual = new double[INDIVIDUAL_LENGTH];
			for (int i = 0; i < INDIVIDUAL_LENGTH; i++) {
				individual[i] = (Math.random() *2 ) -1;
			}
		
			return individual;
		}
	    
	    
	    
		
		 public void initialPopulation(){
		        for (int i = 0; i < POPULATION_SIZE; i++) {
		            population.add(createRandomIndividual());
		        }
		    }
		 
		 public static void valuesToTxt(String file, double[] individual) {
				String filePath = file;

		        // Write the array to the file
		        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
		            for (double value : individual) {
		                writer.write(String.valueOf(value));
		                writer.newLine();
		            }
		            System.out.println("Individuo Escrito no ficheiro.");
		        } catch (IOException e) {
		            System.err.println("Error writing to the file: " + e.getMessage());
		        }
			}
		 

		 
		 private double[] getBestSolution(ArrayList<double []> population) {
		        double[] bestSolution = population.get(0);
		        double bestFitness = getFitness(bestSolution);

		        for (double[] individual : population) {
		        	double currentFitness = getFitness(individual);
		            if (currentFitness > bestFitness) {
		                bestFitness = currentFitness;
		                bestSolution = individual;
		            }
		        }

		        return bestSolution;
		    }
	
}


