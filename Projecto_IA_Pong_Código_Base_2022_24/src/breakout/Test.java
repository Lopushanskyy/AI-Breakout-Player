package breakout;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import utils.Commons;

public class Test{

	
	
	
	public static void main(String[]args) {
		
		GeneticAlgorithm ga = new GeneticAlgorithm();
		double[] individual = ga.search();
		
		
		Scanner scanner = new Scanner(System.in);

	    System.out.println(":");
	    scanner.nextLine();
	     
	    //double[] individual = GeneticAlgorithm.createRandomIndividual();
	    //double[] individual = txtToArray("indivvv.txt");
	    
		NeuralNetwork nn1 = new NeuralNetwork(Commons.BREAKOUT_STATE_SIZE, NeuralNetwork.HIDDEN_DIM,  Commons.BREAKOUT_NUM_ACTIONS, individual);
		Breakout b1 = new Breakout(nn1, 2);
		
		//valuesToTxt("individual4.txt", individual);
		
    }
	
	
	
	
	public static void valuesToTxt(String file, double[] individual) {
		String filePath = file;

        // Write the array to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (double value : individual) {
                writer.write(String.valueOf(value));
                writer.newLine();
            }
            System.out.println("Array has been written to the file.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
	}
	    
	public static double[] txtToArray(String file) {
		String filePath = file;

        // Count the number of lines in the file
        int numLines = countLines(filePath);

        // Read the file into an array
        double[] array = new double[numLines];

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                double value = Double.parseDouble(line);
                array[index++] = value;
            }
            System.out.println("File has been read into the array.");
        } catch (IOException e) {
            System.err.println("Error reading from the file: " + e.getMessage());
        }

       return array;
    }
	
	private static int countLines(String filePath) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error counting lines in the file: " + e.getMessage());
        }
        return count;
    }
	

}
