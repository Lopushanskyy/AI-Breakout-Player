package breakout;



import utils.GameController;
import utils.Commons;

public class NeuralNetwork implements GameController{

	@Override
	public int nextMove(int[] currentState) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static void main(String[]args) {
		NeuralNetwork nn = new NeuralNetwork();
		Breakout b = new Breakout(nn, 3);
		
		
	}

    
}
