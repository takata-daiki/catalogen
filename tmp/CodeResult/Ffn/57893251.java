package neuralnetwork.ffn;

/**
 * Test class
 * @author gerrit
 *
 */
public class Test {
	/**
	 * main function for testing
	 * @param args
	 */
	public static void main(String args[]) {
		NeuronalNetwork nn = new NeuronalNetwork(5, 5, 4);

		System.out.println(nn);

		nn.calculate();
		System.out.println(nn);

		double[] input = {1,2,3,4,5};
		System.out.println("set Outputs of input neurons");
		nn.setNetworkInput(input);
		
		nn.calculate();
		System.out.println(nn);
	}
}