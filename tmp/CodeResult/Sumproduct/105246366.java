package de.lmu.genzentrum.tresch;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.collections.MapUtils;

public class SumProduct {
	private Hashtable<Node, Hashtable<Node, Hashtable<Double, Double>>> finalHash;
	private Graph graph;
	private Hashtable<Node, Hashtable<Double, Double>> marginalHash;

	// SumProduct bekommt das Objekt Graph όbergeben
	public SumProduct(Graph graph) {
//		System.out.println("SumProduct");
		this.graph = graph;
		//graph.fillFinalHash();
		finalHash = graph.getFinalHash();
//		System.out.println("graph:");
//		graph.printout();
	//	MapUtils.debugPrint(System.out, "finalHash", finalHash);
		marginalHash = new Hashtable<Node, Hashtable<Double, Double>>();
	}

	/*
	 * DoSum-Algorithmus der fόr die jeweiligen Messages den Value berechnet und
	 * anschlieίend dieRandverteilungen berechnet
	 */
	public Hashtable<Node, Hashtable<Double, Double>> doSum()
			throws NoValueException {
		for (int i = 0; i < graph.getMSeqHash().size(); i++) {
			if (graph.getMSeqHash().get(i).isVariableToFactor()) {
				try {
					calcVariableToFactor(i);
				} catch (NoValueException e) {
//					System.out.println(e.getMessage());
				}
			} else {
				try {
					calcFactorToVariable(i);
				} catch (NoValueException e) {
//					System.out.println(e.getMessage());
				}
			}
		}
		calcMarginals();
//		graph.printout();
//		MapUtils.debugPrint(System.out, "Ende marginalHash", marginalHash);
		return marginalHash;
	}

	/*
	 * Methode berechnet die Values fόr die Messages die von einem
	 * Variablen-Knoten zu einem Faktor-Knoten laufen
	 *                      ->
	 *    x mal f ->  (v ) ----- [f]
	 * 
	 */
	
	/* 
	 finalHash = 
	{
	    {tresch.FactorNode: name:fA id:1 nodeArray:null function:([0.0, 1.0][0.4, 0.6])} = 
	    {
	        {tresch.VariableNode: name:x1 id:2 known:true values: 0.0 1.0} = 
	        {
	            0.0 = NaN java.lang.Double
	            1.0 = NaN java.lang.Double
	        } java.util.Hashtable
	    } java.util.Hashtable
	    {tresch.VariableNode: name:x1 id:2 known:true values: 0.0 1.0} = 
	    {
	        {tresch.FactorNode: name:fA id:1 nodeArray:null function:([0.0, 1.0][0.4, 0.6])} = 
	        {
	            0.0 = NaN java.lang.Double
	            1.0 = NaN java.lang.Double
	        } java.util.Hashtable
	    } java.util.Hashtable
	} java.util.Hashtable	
	*/
	public void calcVariableToFactor(int index) throws NoValueException {
//		System.out.println("v2f");
		
		Message m = graph.getMSeqHash().get(index);
//		System.out.println(m);
//		msg: {tresch.VariableNode: name:x1 id:2 known:true values: 0.0 1.0} -> {tresch.FactorNode: name:fA id:1 nodeArray:null function:([0.0, 1.0][0.4, 0.6])} vtf:true isMarginalEdge:false value:[NaN, NaN] de.lmu.genzentrum.tresch.Message
		VariableNode from = (VariableNode) m.getFrom(); 
		FactorNode to = (FactorNode) m.getTo(); 
		Hashtable<Node, Hashtable<Double, Double>> toHash = finalHash.get(from); 
	/*	
   {tresch.FactorNode: name:fA id:1 nodeArray:null function:([0.0, 1.0][0.4, 0.6])} = 
    {
        0.0 = NaN java.lang.Double
        1.0 = NaN java.lang.Double
    } java.util.Hashtable
*/
		
//		MapUtils.debugPrint(System.out, "1toHash", toHash);
		if (toHash.size() <= 1 && from.isKnown()) { // nur fόr Kanten die aus  Blδttern kommen
//			System.out.println("v2f blδtter");
			Object toArray[] = toHash.keySet().toArray();
			for (int i = 0; i < toArray.length; i++) {
				FactorNode toNode = (FactorNode) toArray[i];
				if (toNode == to) {
					Hashtable<Double, Double> valueHash = toHash.get(toNode);
					valueHash.clear(); 	// die values mit NaN muessen zunaechst geleert werden
//					MapUtils.debugPrint(System.out, "2valueHash", valueHash);

					for (int j = 0; j < this.graph.getDefSize(m); j++) {
						valueHash.put(from.getValues()[j], 1.0); // man fόgt zum valueHash fόr jedes Def 1 hinzu;
					}	
					toHash.put(toNode, valueHash);
//					MapUtils.debugPrint(System.out, "3valueHash", valueHash);
				}
			}
//			MapUtils.debugPrint(System.out, "4toHash", toHash);
		}
		else if(toHash.size()<=1 && !from.isKnown()){
		    throw new NoValueException("Variablen-Knoten sit nicht bekannt");
		}
	
		else { // fόr inner Kanten
//			System.out.println("innen");
			Object toArray[] = toHash.keySet().toArray();
			Hashtable<Double, Double> messageValues[] = getMessageValues(m);
			Hashtable<Double, Double> resultHash = new Hashtable<Double, Double>();

			for (int i = 0; i < toArray.length; i++) {
				FactorNode toNode = (FactorNode) toArray[i];
				if (toNode == to) {

					Hashtable<Double, Double> valueHash = toHash.get(toNode);
					valueHash.clear(); // die values mit NaN muessen zunaechst
					// geleert werden

					double result = 1.0;
					for (int j = 0; j < graph.getDefSize(m); j++) {

						for (int x = 0; x < messageValues.length; x++) {
							Double def = from.getValues()[j];
							if (!Double.isNaN(messageValues[x].get(def))) {
								result = result * messageValues[x].get(def); // Def
								// als
								// Key
							} else if (Double.isNaN(messageValues[x].get(def))) {
								throw new NoValueException(
										"Kann nicht berechnet werden");
							}
						}
						result = roundUp(result);

						resultHash.put(from.getValues()[j], result);
						// toHash.get(toNode).put(from.getValues()[j], result);
						// //neu berechnete Values werden in finalHash
						// hinzugefόgt
						result = 1.0;
					}
					resultHash = this.normalize(resultHash);
					toHash.put(toNode,
							new Hashtable<Double, Double>(resultHash));
					resultHash.clear();
				}
			}
		}
	}

	/*
	 * Methode berechnet den Value der Messages die von Faktor-Knoten zu
	 * Variablen-Knoten geht
	 *  *                  ->
	 *    x mal v ->  [f] ----- (v)
	 * 
	 */
	public void calcFactorToVariable(int index) throws NoValueException {
//		System.out.println("f2v");
		Message m = graph.getMSeqHash().get(index);
//		System.out.println(m);
		FactorNode from = (FactorNode) m.getFrom(); 
		VariableNode to = (VariableNode) m.getTo();
//		System.out.println("from: "+from);
//		System.out.println("to: "+to);
		double function[][] = from.getFunction();
		
//		for(int i=0;i<function.length;i++)
//			System.out.println(Arrays.toString(function[i]));
		
		Hashtable<Node, Hashtable<Double, Double>> toHash = finalHash.get(from);

//		MapUtils.debugPrint(System.out, "1toHash", toHash);

		if (toHash.size() <= 1) { // nur fόr Kanten die aus Blδttern kommen
//			System.out.println("Blδtter");
			Object toArray[] = toHash.keySet().toArray();
			for (int i = 0; i < toArray.length; i++) {
				Node toNode = (Node) toArray[i];
				if (toNode.equals(to)) {
					Hashtable<Double, Double> valueHash = toHash.get(toNode);
//					MapUtils.debugPrint(System.out, "2valueHash", valueHash);
					valueHash.clear(); // die values mit NaN muessen zunaechst geleert werden
//					MapUtils.debugPrint(System.out, "3valueHash", valueHash);

					for (int j = 0; j < this.graph.getDefSize(m); j++) {
//						System.out.println(to.getValues()[j]+","+(Double) function[1][j]);
						valueHash.put(to.getValues()[j],(Double) function[1][j]); // man fόgt zum valueHash fόr jedes Def die Funktionswerte hinzu;
					}
					toHash.put(toNode,valueHash);
				}
			}
			
//			MapUtils.debugPrint(System.out, "4toHash", toHash);
//			MapUtils.debugPrint(System.out, "5finalHash", finalHash);

		} else { // nur fόr inner Kanten
//			System.out.println("innen");
			int i = 0; // Spalte
			int idArray[] = from.getNodeArray();
			// im idArray stehen die id in der richtigen Reihenfolge drin Bsp(x1, x2, x3)

			for (int j = 0; j < idArray.length; j++) {
				if (from.getNodeArray()[j] == to.getId()) {
					i = j; // i gibt die Spalte an, die genauer betrachtet
				}
			}
			Hashtable<Double, Double> result = new Hashtable<Double, Double>(); // result ist ein neuer hashtable den man komplet in den Hash einsetzen kann

			for (int y = 0; y < function[0].length; y++) { // die Tabelle wird zeilen-weise durchlaufen
				double wert = 1;
				for (int x = 0; x < function.length; x++) {
					if (x <= function.length - 2 && x != i) { // man multipliziert die Values der eingehenden Messages
						double value = this.getValue(this.getNode(idArray[x]),
								from, function[x][y]);
						if (!Double.isNaN(value)) {
							wert = wert * value;
						} else if (Double.isNaN(value)) {
							throw new NoValueException(
									"Kann nicht berechnet werden, da der Value einer eingehenden Message fehlt!");
						}
					} else if (x == function.length - 1) { // der Funktionswert fόr die bestimmten werte wird dazu multipliziert
						wert = wert * (Double) function[x][y];
					}
				}

				double res = 0;
				if (!result.containsKey(function[i][y])) { // falls fόr den Funktionswert bereits ein Eintrag existiet
					res = wert;
					res = roundUp(res);
					result.put(function[i][y], res);
				} else { // falls noch kein funktionswert exitiert
					res = result.get(function[i][y]) + wert;
					res = roundUp(res);
					result.remove(function[i][y]);
					result.put(function[i][y], res);
				}
			}
			result = this.normalize(result);
//			MapUtils.debugPrint(System.out, "result", result);
			finalHash.get(from).get(to).clear(); // alte Werte aus dem Value-Hash werden entfernt (-1)
		
			finalHash.get(from).put(to, new Hashtable<Double, Double>(result)); // result wird als neuer Value-Hash hinzugefόgt
		}
	}

	/*
	 * Methode berechnet die Werte der einzelnen Variablen-Knoten
	 * (Randverteilungen)
	 */
	public void calcMarginals() {
//		System.out.println("calcMarginals");
		Hashtable<Double, Double> resultHash = new Hashtable<Double, Double>();
//		MapUtils.debugPrint(System.out, "1finalHash", finalHash);
		Object finalArray[] = finalHash.keySet().toArray();

		for (int i = 0; i < finalArray.length; i++) {
			if (finalArray[i] instanceof VariableNode) { // margins werden nur όber variablenode berechnet
				Message m = graph.getMSeqHash().get(i);
				VariableNode node = (VariableNode) finalArray[i];
//				System.out.println(m);
//				System.out.println(node);
				double wert[] = new double[graph.getDefSize(m)]; // man braucht soviele Werte wie die Definitionsgrφίe
//				System.out.println("1!"+Arrays.toString(wert));
				Arrays.fill(wert, 1);
//				System.out.println("2!"+Arrays.toString(wert));
				for (int t = 0; t < wert.length; t++) {
//					System.out.println("t:"+t);
					Double def = node.getValues()[t]; // man braucht den definitionswert fόr den eine berechnung stattfindet
//					System.out.println("def:"+def);
					Object fromArray[] = finalHash.keySet().toArray();
					
					for (int j = 0; j < fromArray.length; j++) {
//						System.out.println("j"+j);
						Node from = (Node) fromArray[j];
//						System.out.println(from);
						Object toArray[] = finalHash.get(from).keySet()
								.toArray();
//						System.out.println("toa:"+toArray.length);
						for (int k = 0; k < toArray.length; k++) {
							Node to = (Node) toArray[k];
//							System.out.println(to);
//							System.out.println(to.equals(node));
//							System.out.println(to == node);
//							System.out.println(to.getName());
//							System.out.println(node.getName());
							if (to.equals(node)) { ////TODO  hier ist irgend was faul!!!! in meinem beispiel sind die knoten nie gleich?!?!
								double value = (double) (finalHash.get(from).get(to).get(def));
//								System.out.println("wert="+wert[t]+"*"+value);
								wert[t] = wert[t] * value; 
								// wert wird berechnet in dem man alle eingehenden kannten fόr einen defwert multipliziert
							}
						}
					}
//					System.out.println("wert:"+wert[t]);
					wert[t] = roundUp(wert[t]);
//					System.out.println("roundedwert:"+wert[t]);
					resultHash.put(def, wert[t]); // Wert wird in einem HilfsHash gespeichert
				}
//				System.out.println("3!"+Arrays.toString(wert));
//				MapUtils.debugPrint(System.out, "2resultHash", resultHash);
				resultHash = this.normalize(resultHash); // der HilfsHash resultHash wird zunδchst normaliziert nur fόr die Ausgabe:
//				MapUtils.debugPrint(System.out, "3resultHash", resultHash);

				Object keyArray[] = resultHash.keySet().toArray();

				for (int k = 0; k < keyArray.length; k++) {
					System.out.println("Knoten " + node.getName() + " Def: "+ keyArray[k] + " hat den Wert "+ resultHash.get(keyArray[k]));
				}
				marginalHash.put(node, resultHash);
			}
		}
	}

	/*
	 * Hilfsfunktionen:
	 */
	public Hashtable<Double, Double> normalize(Hashtable<Double, Double> result) {// Methode die Werte normalisiert
		double sum = 0;
		Hashtable<Double, Double> resultNormalized = new Hashtable<Double, Double>();
		Object resultArray[] = result.keySet().toArray();
		for (int i = 0; i < resultArray.length; i++) {
			sum = sum + result.get(resultArray[i]);
		}
		for (int i = 0; i < resultArray.length; i++) {
			resultNormalized.put((Double) resultArray[i], roundUp(result.get(resultArray[i])/ sum));
		}
		return resultNormalized;
	}

	public Hashtable<Double, Double>[] getMessageValues(Message m) {// gibt Array zurόck, der die einzelnen DefHash enthδlt
		Node from = m.getFrom();
		Node to = m.getTo();
		Node neighbours[] = getNeighbours(from);
		int neighLength = neighbours.length;
		Hashtable<Double, Double>[] resultArray = new Hashtable[neighLength - 1];

		int k = 0;
		for (int i = 0; i < neighLength; i++) {
			if (neighbours[i] != to) {
				resultArray[k] = finalHash.get(neighbours[i]).get(from);
				;
				k++;
			}
		}
		return resultArray;
	}

	public Node[] getNeighbours(Node node) {// gibt alle Nachbarknoten zurόck
		Object keyArray[] = finalHash.get(node).keySet().toArray();
		Node nodeArray[] = new Node[keyArray.length];
		for (int i = 0; i < keyArray.length; i++) {
			nodeArray[i] = (Node) keyArray[i];
		}
		return nodeArray;
	}

	public Node getNode(int id) {// gibt den Node einer Message zurόck, wenn man
		// nur die Id des FromNodes hat
		Object[] fromKey = finalHash.keySet().toArray();
		for (int i = 0; i < fromKey.length; i++) {
			Node fromNode = (Node) fromKey[i];
			if (fromNode.getId() == id) {
				return fromNode;
			}
		}
		return null;
	}

	public double getValue(Node from, Node to, Object def) {
		double value = finalHash.get(from).get(to).get(def);
		return value;
	}

	public double roundUp(double value) {
		BigDecimal bd = new BigDecimal(value);
		BigDecimal rn = bd.setScale(10, java.math.BigDecimal.ROUND_HALF_UP);
		return rn.doubleValue();
	}
}
