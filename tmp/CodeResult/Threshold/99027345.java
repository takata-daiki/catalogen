package com.gnosis.anncraun.engine;

/**
 * @author Brendan McGloin
 *
 */
public class Threshold {

    private static final byte ACTIVATION = 1;

    private double weight, previousIncrement;

    public Threshold(double weight) {
        this.weight = weight;
        previousIncrement = 0;
    }

    public void adjustWeight(double adjustment) {
        previousIncrement = adjustment;
        this.weight += adjustment;
    }

    public void randomize() {
        previousIncrement = 0;
        weight = .5 - Tools.RANDOM.nextDouble();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getActivation() {
        return ACTIVATION;
    }

    public String toString() {
        return String.format("%.3f", weight);
    }

    /**
     * @return the previousIncrement
     */
    public double getPreviousIncrement() {
        return previousIncrement;
    }

    /**
     * @param previousIncrement the previousIncrement to set
     */
    public void setPreviousIncrement(double previousIncrement) {
        this.previousIncrement = previousIncrement;
    }
}