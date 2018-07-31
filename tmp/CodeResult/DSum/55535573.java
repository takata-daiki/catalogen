package edu.berkeley.nlp.assignments;

import edu.berkeley.nlp.assignments.ECLLMaximizer.EncodedFeatureDatum;
import edu.berkeley.nlp.math.DifferentiableFunction;
import edu.berkeley.nlp.math.DoubleArrays;
import edu.berkeley.nlp.util.Pair;

/**
 * This is the MaximumEntropy objective function: the (negative) log conditional likelihood of the training data,
 * possibly with a penalty for large weights.  Note that this objective get MINIMIZED so it's the negative of the
 * objective we normally think of.
 */
public class SumWeightedExponential<F,I,C, L> implements DifferentiableFunction {
	IndexLinearizer3D indexLinearizerCif;
	Encoding4D<F, I,C, L> encoding;
	EncodedFeatureDatum[] features;
	String[] words;
	double[][][] prevProbs;

	double lastValue;
	double[] lastDerivative;
	double[] lastX;

	public int dimension() {
		return indexLinearizerCif.getNumLinearIndexes();
	}

	public double valueAt(double[] x) {
		ensureCache(x);
		return lastValue;
	}

	public double[] derivativeAt(double[] x) {
		ensureCache(x);
		return lastDerivative;
	}

	private void ensureCache(double[] x) {
		if (requiresUpdate(lastX, x)) {
			Pair<Double, double[]> currentValueAndDerivative = calculate(x);
			lastValue = currentValueAndDerivative.getFirst();
			lastDerivative = currentValueAndDerivative.getSecond();
			lastX = x;
		}
	}

	private boolean requiresUpdate(double[] lastX, double[] x) {
		if (lastX == null) return true;
		for (int i = 0; i < x.length; i++) {
			if (lastX[i] != x[i])
				return true;
		}
		return false;
	}

	/**
	 * The most important part of the classifier learning process!  This method determines, for the given weight vector
	 * x, what the (negative) log conditional likelihood of the data is, as well as the derivatives of that likelihood
	 * wrt each weight parameter.
	 */
	private Pair<Double, double[]> calculate(double[] w) {
		double objective = 0.0;
		double[] derivatives = DoubleArrays.constantArray(0.0, dimension());
		
		// evaluate function at weight vector
		double sum = 0;
		for (int j=0; j<words.length; j++) {
			for (int pos = 1; pos <= words[j].length(); pos++) {
				for (int c = 0; c < encoding.getNumClasses(); c++) {
					sum += -getPrevProb(c, words[j].substring(0, pos), words[j].substring(pos))
							* getDotProduct(c, words[j].substring(pos), w, features[j]);

				}
			}
		}
		objective = sum;
		
		// evaluate derivatives
		for (int fic=0; fic<w.length; fic++) {
			int c = indexLinearizerCif.getClassIndex(fic);
			int i = indexLinearizerCif.getLabelIndex(fic);
			int f = indexLinearizerCif.getFeatureIndex(fic);
			String infl = (String)encoding.getInfl(i);
			double dsum = 0;
			for (int j=0; j<words.length; j++) {
				if (words[j].endsWith(infl)) {
					int len = words[j].length();
					int ii = len - infl.length();
					String lemma = words[j].substring(0,ii);
					dsum += - getPrevProb(c,lemma, infl) * features[j].getFeatureCount(f);
				}
			}
			derivatives[fic] = dsum;
		}
						
		// negate all
		objective = -objective;
		for (int i=0; i<derivatives.length; i++) {
			derivatives[i] = -derivatives[i];
		}
		return new Pair<Double, double[]>(objective, derivatives);
	}

	private double getDotProduct(int c, String infl, double[] w, EncodedFeatureDatum features) {
		if (encoding.getInflIndex((I)infl) < 0) {
			return 0;
		}
		double[] counts = features.featureCounts;
		int[] indices = features.featureIndexes;
		
		double sum = 0;
		for (int f=0; f<features.getNumActiveFeatures(); f++) {
			int linearIndex = indexLinearizerCif.getLinearIndex(indices[f], encoding.getInflIndex((I) infl), c);
			double vote = counts[f] * w[linearIndex];
			sum += vote;
		}
		return sum;
	}

	private double getPrevProb(int classIndex, String lemma, String infl) {
		// TODO: ugly hax 'cause we know L, I = String
		int lemmaIndex = encoding.getLemmaIndex((L) lemma);
		int inflIndex = encoding.getInflIndex((I) infl);
		if (lemmaIndex >= 0 && inflIndex >= 0) {
			return prevProbs[classIndex][lemmaIndex][inflIndex];
		} else {
			return 0;
		}
	}
	
	public SumWeightedExponential(Encoding4D<F, I, C, L> encoding, EncodedFeatureDatum[] data, IndexLinearizer3D indexLinearizerCif, String[] words, double[][][] prevProbs) {
		this.words = words;
		this.indexLinearizerCif = indexLinearizerCif;
		this.encoding = encoding;
		this.features = data;
		this.prevProbs = prevProbs;
	}
}
