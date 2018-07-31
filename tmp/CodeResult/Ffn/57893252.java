package neuralnetwork.ffn;

public class TeachingInput {
	private double[] input, output;
	
	public TeachingInput(int numInputNeurons, int numOutputNeurons) {
		input = new double[numInputNeurons];
		output = new double[numOutputNeurons];
	}
	
	public double[] getInput() {
		return input;
	}
	
	public double[] getOutput() {
		return output;
	}
	
	public void setInput(double[] input) {
		this.input = input;
	}
	
	public void setOutput(double[] output) {
		this.output = output;
	}
	/**
	 * Reads a teaching Input from File 
	 * @param filename The file from which to read the teaching input
	 */
	public void readTeachingInput(String filename) {
		
	}
	
}
