package breakout;



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
	
	
	@Override
	public int nextMove(int[] currentState) {
		double[]outputValues = forward(currentState);
		if(outputValues[0]>outputValues[1]) return BreakoutBoard.LEFT;
		else return BreakoutBoard.RIGHT;
	}
	
	

    
}
