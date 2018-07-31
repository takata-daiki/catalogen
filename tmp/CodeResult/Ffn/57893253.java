package neuralnetwork.ffn;

import java.util.ArrayList;


public class NeuronalNetwork {

	ArrayList<Layer> layers;

	@Override
	public String toString() {
		String result = "";
		result = "InputLayer: " + layers.get(0) + "\n";

		for(int i = 1; i < layers.size()-1; i++) {
			result += "Hidden Layer: " + layers.get(i) + "\n";
		}

		result += "OutputLayer: " + layers.get(layers.size()-1) + "\n";

		return result;
	}

	public void setNetworkInput(double[] input) {
		if(input.length > layers.get(0).getNeurons().size()) {
			System.err.println("Neuronal Network not compatible with input");
			return;
		}


		for (int i = 0; i < input.length; i++) {
			layers.get(0).getNeurons().get(i).setOutput(input[i]);
		}

	}
	public NeuronalNetwork(int numLayers, int numInputNeurons, int numOutputNeurons) {
		if(numLayers < 3) {
			System.err.println("Error: numLayers must be at least 3");
			return;
		}

		layers = new ArrayList<Layer>();

		/* input Layer */
		layers.add(0,new Layer(numInputNeurons, null));

		/* hidden layers */
		for(int i = 1; i < numLayers-1; i++) {
			layers.add(i,new Layer((int) (numInputNeurons * 1.337), layers.get(i-1)));
		}

		/* output layer */
		layers.add(new Layer(numOutputNeurons, layers.get(layers.size()-1)));
	}

	/**
	 * Calculates the Output for each Layer ( except of the input layer ~wrong? )
	 */
	public void calculate() {
		System.out.println("compute output for every neuron..");
		for(int i = 1; i < layers.size(); i++) {
			layers.get(i).compute();
		}
	}
	public void train(TeachingInput input) {
		setNetworkInput(input.getInput());
		calculate();
		backpropagateOutputLayer(input.getOutput());
	}


	public void calculateOutputError(double[] wantedOutput) {
		for(int i = 0; i < layers.get(0).getNeurons().size(); i++) {
			/*calculate error */
			Neuron current = layers.get(0).getNeurons().get(i);

			current.setError(current.sigmoidDeriv(current.calculateNetworkInput()) * (wantedOutput[i] - current.getOutput()));
		}
	}

	public void calculateHiddenError(Layer layer) {
		layer.getNeurons();
	}
	public void backpropagate(double[] wantedOutput) {
		for(int i = layers.size() -1; i > 0; i--) {
			for(int j = 0; j < layers.get(j).getNeurons().size(); j++) {
				Neuron curr = layers.get(i).getNeurons().get(j);
				/* error */
				double weightedErrorSum = 0;
				
				for (Edge e : curr.successors) {
					weightedErrorSum += e.getDestinationNeuron().getError()*e.getWeight();
				}
				curr.setError(curr.sigmoidDeriv(curr.calculateNetworkInput()) * weightedErrorSum);
				
				
			}
			
			/* update weights */
			
			
		}
		
		
	}
}

