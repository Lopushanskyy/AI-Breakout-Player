package breakout;



import utils.GameController;

import java.util.Arrays;

import utils.Commons;

public class NeuralNetwork implements GameController, Comparable<NeuralNetwork>{
	
	public static final int HIDDEN_DIM = 7;
	private int inputDim;
    private int hiddenDim;
    private int outputDim;
    private double[][] hiddenWeights;
    private double[] hiddenBiases;
    private double[][] outputWeights;
    private double[] outputBiases;  
    
    
	public NeuralNetwork(int inputDim, int hiddenDim, int outputDim, double[] values) {

        this.inputDim = inputDim;
        this.hiddenDim = hiddenDim;
        this.outputDim = outputDim;
        hiddenWeights = new double [inputDim][hiddenDim];
        outputWeights = new double [hiddenDim][outputDim];
        hiddenBiases = new double [hiddenDim];
        outputBiases = new double [outputDim];
        
        int valuesIndex = 0;
        for(int i=0; i < inputDim; i++){
            for(int j=0; j < hiddenDim; j++){
                hiddenWeights[i][j] = values[valuesIndex++];
            }
        }
        for(int i=0;i<hiddenDim;i++){
            hiddenBiases [i] = values[valuesIndex++];
        }
        
        for(int i=0;i<hiddenDim;i++){
            for(int j=0; j<outputDim;j++){
                outputWeights[i][j] = values[valuesIndex++];
            }
        }
        for(int i =0;i<outputDim;i++){
            outputBiases[i] = values[valuesIndex++];
        }
	}
	
	public double[] forward(int[] inputValues) {

	    double[] hiddenOutputs = new double[hiddenDim];
	    for (int j = 0; j < hiddenDim; j++) {
	        double sum = 0;
	        for (int i = 0; i < inputDim; i++) {
	            sum += inputValues[i] * hiddenWeights[i][j];
	        }
	        hiddenOutputs[j] = (sum + hiddenBiases[j]);
	    }

	    double[] outputValues = new double[outputDim];
	    for (int j = 0; j < outputDim; j++) {
	        double sum = 0;
	        for (int i = 0; i < hiddenDim; i++) {
	            sum += hiddenOutputs[i] * outputWeights[i][j];
	        }
	        outputValues[j] = myF(sum + outputBiases[j]);
	    }
	    return outputValues;
	}
	
	public static double sigmoid(double x){
        return 1/(1+Math.exp(-x));
    }
	
	public static double expReLU(double x) {
		if (x > 0) return x;
		else return 0.1*(Math.exp(x) - 1);
	}
	
	
	 public static double relu(double x) {
	    return Math.max(0, x);
	  }
	 
	 public static double myF(double x) {
		    if(x<0) return 0;
		    else return 1;
		  }
	 
	 
	
	@Override
	public int nextMove(int[] currentState) {
//		double[] newCurrentState = calculeCurrentState(currentState); 
		//System.out.println(Arrays.toString(currentState));
		
		double[]outputValues = forward(currentState);
		
		//System.out.println(Arrays.toString(outputValues));
		
		//if(outputValues[0] > outputValues[1]) return BreakoutBoard.LEFT;
		//else return BreakoutBoard.RIGHT;
		
		if(outputValues[0] == 0.0 && outputValues[1] == 0.0) return BreakoutBoard.LEFT;
		else if (outputValues[0] == 1.0 && outputValues[1] == 1.0) return BreakoutBoard.RIGHT;
		else if(outputValues[0] == 0.0 && outputValues[1] == 1.0) return BreakoutBoard.RIGHT;
		else if(outputValues[0] == 1 && outputValues[1] == 0.0) return BreakoutBoard.LEFT;
		else return BreakoutBoard.RIGHT;
			
	}

	private double[] calculeCurrentState(int[] currentState) {
		double[] newCS = new double[inputDim]; 
		for(int i = 0; i < inputDim; i++)
			newCS[i] = currentState[i]/1000.0;
		return newCS;
	}

	

	@Override
	public int compareTo(NeuralNetwork other) {
		return 0;
	}
}
	
	


/*package breakout;

import java.util.Arrays;

import utils.GameController;
import utils.Commons;

public class NeuralNetwork implements GameController{

	private int inputDim;
    private int hiddenDim;
    private int outputDim;
    private double[][] hiddenWeights;
    private double[] hiddenBiases;
    private double[][] outputWeights;
    private double[] outputBiases;
    
    
	public NeuralNetwork(int inputDim, int hiddenDim, int outputDim, double[] values) {

        this.inputDim = inputDim;
        this.hiddenDim = hiddenDim;
        this.outputDim = outputDim;
        hiddenWeights = new double [inputDim][hiddenDim];
        outputWeights = new double [hiddenDim][outputDim];
        hiddenBiases = new double [hiddenDim];
        outputBiases = new double [outputDim];
        int valuesIndex = 0;
        for(int i=0;i<inputDim;i++){
            for(int j=0;j<hiddenDim;j++){
                hiddenWeights[i][j] = values[valuesIndex++];
            }
        }
        for(int i=0;i<hiddenDim;i++){
            hiddenBiases [i] = values[valuesIndex++];
        }
        for(int i=0;i<hiddenDim;i++){
            for(int j=0; j<outputDim;j++){
                outputWeights[i][j] = values[valuesIndex++];
            }
        }
        for(int i =0;i<outputDim;i++){
            outputBiases[i] = values[valuesIndex++];
        }
	}
	
	public double[] forward(int[] inputValues) {

        double [] hiddenOutputs = new double[hiddenDim];
       for(int hiddenWeightsIndex = 0;hiddenWeightsIndex<hiddenDim; hiddenWeightsIndex++){
           double valorAcum=0;
           for(int inputValuesIndex=0; inputValuesIndex<inputDim; inputValuesIndex++){
               valorAcum+=inputValues[inputValuesIndex]*hiddenWeights[inputValuesIndex][hiddenWeightsIndex];
           }
           hiddenOutputs[hiddenWeightsIndex] = sigmoid(valorAcum+hiddenBiases[hiddenWeightsIndex]);
       }

       double[] outputValues = new double[outputDim];
       for(int outputWeightsIndex=0; outputWeightsIndex<outputDim; outputWeightsIndex++){
           double valorAcum=0;
           for(int hiddenInputsIndex=0; hiddenInputsIndex<hiddenDim; hiddenInputsIndex++){
               valorAcum+=hiddenOutputs[outputWeightsIndex]*outputWeights[hiddenInputsIndex][outputWeightsIndex];
           }
           outputValues[outputWeightsIndex] = sigmoid(valorAcum+outputBiases[outputWeightsIndex]);
       }
       return outputValues;


    }
	
	public double sigmoid(double x){
        return 1/(1+Math.exp(-x));
    }
	
	public double relu(double x) {
	    return Math.max(0, x);
	}
	
	
	@Override
	public int nextMove(int[] currentState) {
		
		//System.out.println(Arrays.toString(currentState));
		
		double[]outputValues = forward(currentState);
		//System.out.println(Arrays.toString(outputValues));
		
		if(outputValues[0]>outputValues[1]) return BreakoutBoard.LEFT;
		else return BreakoutBoard.RIGHT;
	}
	
	double[] tryforw(int[]inputvalues) {
		return forward(inputvalues);
	}

    
}
*/
