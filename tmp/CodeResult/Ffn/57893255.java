package neuralnetwork.ffn;

import java.util.ArrayList;

public class Layer {
	private ArrayList<Neuron> neurons;
	public Layer prevLayer;

	public Layer(int numNeurons, Layer prevLayer) {
		neurons = new ArrayList<Neuron>();

		for( int i = 0; i < numNeurons; i++ ) {
			Neuron n = new Neuron();
			if(prevLayer != null) {
				for(Neuron m : prevLayer.getNeurons()) {
					Edge e = new Edge(m,n,0.5);
					n.predecessors.add(e);
					m.successors.add(e);
				}
			}
			neurons.add(n);
		}
	}

	public void compute() {
		for(Neuron curr : neurons) {
			curr.calculateOutput();
		}
	}

	public ArrayList<Neuron> getNeurons() {
		return neurons;
	}

	@Override
	public String toString() {
		String result = "[";
		boolean first = true;

		for(Neuron n : neurons) {
			if(!first) {
				result += "|";
			} else {
				first = false;
			}
			double temp = n.getOutput() * 1000d;
			result += (Math.round(temp)/1000d);
		}
		result += "]";
		return result;
	}
}
