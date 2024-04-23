package breakout;


import utils.Commons;

public class Test{

	public static void main(String[]args) {
		
		
		
		NeuralNetwork nn = new NeuralNetwork(7,5,2, createRandomIndividual());
		Breakout b = new Breakout(nn,  2);
		
		/*b.setSeed(2);
		b.runSimulation();
		double fitness = b.getFitness();
		System.out.println(fitness);
		*/
		
		}
	
	
	public static double[] createRandomIndividual() {
		double[] individual = new double[52];
		for (int i = 0; i < 52; i++) {
			individual[i] = Math.random();
		}
		System.out.println(individual);
		return individual;
	}
	
	
    
}
