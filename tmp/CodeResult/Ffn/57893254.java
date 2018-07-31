package neuralnetwork.ffn;

import java.util.ArrayList;

public class Neuron {

	ArrayList<Edge> predecessors;
	ArrayList<Edge> successors;

	private double output = 0;
	private double error = 0;

	
	public void setError(double error) {
		this.error = error;
	}
	
	public double getError() {
		return error;
	}
	public Neuron() {
		predecessors = new ArrayList<Edge>();
	}

	/**
	 * Calculates the weighted sum of the Inputs
	 */
	public double calculateNetworkInput() {
		double result = 0;
		for(Edge curr : predecessors) {
			result += (curr.getSourceNeuron().getOutput() * curr.getWeight());
		}

		return result;
	}

	public double sigmoidActivation(double activation) {
		return (1 / ( 1 + Math.exp(-activation)));
	}
	
	public double sigmoidDeriv(double netInput) {
		return (sigmoidActivation(netInput) * (1d - sigmoidActivation(netInput)));
	}

	public void calculateOutput() {
		output = sigmoidActivation(calculateNetworkInput());
	}

	public double getOutput() {
		return output;
	}
	
	public void setOutput(double output) {
		this.output = output;
	}
}
