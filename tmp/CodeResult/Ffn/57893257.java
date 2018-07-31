package neuralnetwork.ffn;

public class Edge {
	/* the weight between sourceNeuron and destinationNeuron */
	private double weight;

	private Neuron sourceNeuron;
	private Neuron destinationNeuron;

	public Edge(Neuron sourceNeuron, Neuron destinationNeuron, double weight) {
		this.sourceNeuron = sourceNeuron;
		this.destinationNeuron = destinationNeuron;

		/* initial weight */
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Neuron getSourceNeuron() {
		return sourceNeuron;
	}

	public Neuron getDestinationNeuron() {
		return destinationNeuron;
	}
}
