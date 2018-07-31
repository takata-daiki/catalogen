package de.lmu.genzentrum.tresch;

import java.util.Hashtable;
import java.util.LinkedList;

public class SumProduct {
	private LinkedList<Message> mSeq;
	private Hashtable<Node, Hashtable<Node, Hashtable<Object, Double>>> finalHash;
	private Graph graph;

	
	public SumProduct() {
	}

	/*
	 * Methode berechnet den Value der Messages die von Faktor-Knoten zu
	 * Variablen-Knoten geht
	 */
	protected void calcFactorToVariable(int index) throws NoValueException {
		Message m = mSeq.get(index);
		FactorNode from = (FactorNode) m.getFrom(); // from und to werte werden
													// vorher schon mal
													// definiert
		VariableNode to = (VariableNode) m.getTo();
		Object function[][] = from.getFunction();
		Hashtable<Node, Hashtable<Object, Double>> toHash = finalHash.get(from);

		if (toHash.size() <= 1) { // nur fόr Kanten die aus Blδttern kommen
			Object toArray[] = toHash.keySet().toArray();
			for (int i = 0; i < toArray.length; i++) {
				Node toNode = (Node) toArray[i];
				if (toNode == to) {
					Hashtable<Object, Double> valueHash = toHash.get(toNode);
					valueHash.clear(); // die values mit -1 muessen zunaechst
										// geleert werden
					VariableNode variable = (VariableNode) m.getTo();
					for (int j = 0; j < this.graph.getDefSize(m); j++) {
						toHash.get(toNode).put(variable.getValues()[j],
								(Double) function[1][j]); // man fόgt zum
															// valueHash fόr
															// jedes Def die
															// Funktionswerte
															// hinzu;
					}
				}
			}
		} else { // nur fόr inner Kanten
			int i = 0;
			String nameArray[] = from.getNodeArray();
			// im nameArray stehen die Namen in der richtigen Reihenfolge drin
			// Bsp(x1, x2, x3)

			for (int j = 0; j < nameArray.length; j++) {
				if (from.getNodeArray()[j].equals(to.getName())) {
					i = j; // i gibt die Spalte an, die genauer betrachtet
				}
			}
			Hashtable<Object, Double> result = new Hashtable<Object, Double>(); // result
																				// ist
																				// ein
																				// neuer
																				// hashtable
																				// den
																				// man
																				// komplet
																				// in
																				// den
																				// Hash
																				// einsetzen
																				// kann

			for (int y = 0; y < function[0].length; y++) { // die Tabelle wird
															// zeilen-weise
															// durchlaufen
				double wert = 1;
				for (int x = 0; x < function.length; x++) {
					if (x <= function.length - 2 && x != i) { // man
																// multipliziert
																// die Values
																// der
																// eingehenden
																// Messages
						double value = this.getValue(this.getFromNode(
								nameArray[x], from), from, function[x][y]);
						if (value > 0) {
							wert = wert * value;
						} else
							throw new NoValueException(
									"Kann nicht berechnet werden");
					} else if (x == function.length - 1) { // der funktionswert
															// fόr die
															// bestimmten werte
															// wird
															// dazumultipliziert
						wert = wert * (Double) function[x][y];
					}
				}

				double res = 0;
				if (!result.containsKey(function[i][y])) { // falls fόr den
															// funktionswert
															// bereits ein
															// eitrag existiet
					res = wert;
					result.put(function[i][y], res);
				} else { // falls noch kein funktionswert exitiert
					res = result.get(function[i][y]) + wert;
					result.remove(function[i][y]);
					result.put(function[i][y], res);
				}
			}
			finalHash.get(from).get(to).clear(); // alte Werte aus dem
													// Value-Hash werden
													// entfernt (-1)
			finalHash.get(from).put(to, new Hashtable<Object, Double>(result)); // result
																				// wird
																				// als
																				// neuer
																				// Value-Hash
																				// hinzugefόgt
		}
	}

	/*
	 * Methode berechnet die Werte der einzelnen Variablen-Knoten
	 * (Randverteilungen)
	 */
	protected void calcMarginals() {
		Object finalArray[] = finalHash.keySet().toArray();
		for (int i = 0; i < finalArray.length; i++) {
			if (finalArray[i].getClass().getName().equals("VariableNode")) { // man
																				// sucht
																				// im
																				// fromHash
																				// die
																				// einzelnen
																				// VariablenKnoten
																				// gesucht
				Message m = mSeq.get(i);
				VariableNode node = (VariableNode) finalArray[i];

				double wert[] = new double[getGraph().getDefSize(m)]; // man
																		// braucht
																		// soviele
																		// Werte
																		// wie
																		// die
																		// Definitionsgrφίe
				for (int t = 0; t < wert.length; t++) {
					wert[t] = 1; // man muss das wert-array mit dem neutralen
									// Element der Multiplikation fόllen
				}

				for (int t = 0; t < wert.length; t++) {
					Object def = node.getValues()[t]; // man braucht den
														// definitionswert fόr
														// den eine berechnung
														// stattfindet
					Object fromArray[] = finalHash.keySet().toArray();
					for (int j = 0; j < fromArray.length; j++) {
						Node from = (Node) fromArray[j];
						Object toArray[] = finalHash.get(from).keySet()
								.toArray();
						for (int k = 0; k < toArray.length; k++) {
							Node to = (Node) toArray[k];
							if (to.equals(node)) {
								double value = (finalHash.get(from).get(to)
										.get(def));
								wert[t] = wert[t] * value; // wert wird
															// berechnet in dem
															// man alle
															// eingehenden
															// kannten fόr einen
															// defwert
															// multipliziert
							}
						}
					}
					System.out.println("Node " + node.getName()
							+ " hat bei Def " + def + " den Wert " + wert[t]); // rδnder
																				// werden
																				// ausgegeben
				}
			}
		}
	}

	/*
	 * Methode berechnet die Values fόr die Messages die von einem
	 * Variablen-Knoten zu einem Faktor-Knoten laufen
	 */
	protected void calcVariableToFactor(int index) throws NoValueException {
		Message m = mSeq.get(index);
		VariableNode from = (VariableNode) m.getFrom(); // from und to werden im
														// vorraus aufgerufen,
														// damit diese im Laufe
														// der folgenden
														// Berechnungen
		FactorNode to = (FactorNode) m.getTo(); // nicht jedes mal neu
												// aufgerufen werden mόssen
		Hashtable<Node, Hashtable<Object, Double>> toHash = finalHash.get(from); // toHash
																					// wird
																					// fόr
																					// das
																					// bestimmte
																					// from
																					// aufgerufen

		if (toHash.size() <= 1) { // nur fόr Kanten die aus Blδttern kommen
			Object toArray[] = toHash.keySet().toArray();
			for (int i = 0; i < toArray.length; i++) {
				FactorNode toNode = (FactorNode) toArray[i];
				if (toNode == to) {
					Hashtable<Object, Double> valueHash = toHash.get(toNode);
					valueHash.clear(); // die values mit -1 muessen zunaechst
										// geleert werden
					VariableNode variable = (VariableNode) m.getFrom();
					for (int j = 0; j < this.graph.getDefSize(m); j++) {
						toHash.get(toNode).put(variable.getValues()[j], 1.0); // man
																				// fόgt
																				// zum
																				// valueHash
																				// fόr
																				// jedes
																				// Def
																				// 1
																				// hinzu;
					}
				}
			}
		}

		else { // fόr inner Kanten
			Object toArray[] = toHash.keySet().toArray();
			Hashtable<Object, Double> messageValues[] = getMessageValues(m);

			for (int i = 0; i < toArray.length; i++) {
				FactorNode toNode = (FactorNode) toArray[i];
				if (toNode == to) {

					Hashtable<Object, Double> valueHash = toHash.get(toNode);
					valueHash.clear(); // die values mit -1 muessen zunaechst
										// geleert werden

					double result = 1.0;
					for (int j = 0; j < graph.getDefSize(m); j++) {

						for (int x = 0; x < messageValues.length; x++) {
							Object def = from.getValues()[j];
							if (messageValues[x].get(def) != -1) {
								result = result * messageValues[x].get(def); // Def
																				// als
																				// Key
							} else if ((messageValues[x].get(def) == -1)) {
								throw new NoValueException(
										"Kann nicht berechnet werden");
							}
						}
						toHash.get(toNode).put(from.getValues()[j], result); // neu
																				// berechnete
																				// Values
																				// werden
																				// in
																				// finalHash
																				// hinzugefόgt
						result = 1.0;
					}
				}
			}
		}
	}

	/*
	 * DoSum-Algorithmus der fόr die jeweiligen Messages den Value berechnet und
	 * anschlieίend dieRandverteilungen berechnet
	 */
	public void doSum(Graph graph) throws NoValueException {
		this.graph = graph;
		this.mSeq = graph.getMSeq();
		finalHash = graph.getFinalHash();

		for (int i = 0; i < getMSeq().size(); i++) {
			if (getMSeq().get(i).isVariableToFactor()) {
				try {
					calcVariableToFactor(i);
				} catch (NoValueException e) {
					System.out.println(e.getMessage());
				}
			} else {
				try {
					calcFactorToVariable(i);
				} catch (NoValueException e) {
					System.out.println(e.getMessage());
				}

			}
		}
		calcMarginals();
	}

	protected Hashtable<Node, Hashtable<Node, Hashtable<Object, Double>>> getFinalHash() {
		return finalHash;
	}

	protected Node getFromNode(String s, Node to) { // gibt den FromNode einer
													// Message zurόck, wenn man
													// nur den Namen des
													// FromNodes hat und den
													// toNode der Message hat
		Object[] fromKey = finalHash.keySet().toArray();
		for (int i = 0; i < fromKey.length; i++) {
			Node fromNode = (Node) fromKey[i];
			if (fromNode.getName().equals(s)) {
				Object[] toKey = finalHash.get(fromNode).keySet().toArray();
				for (int j = 0; j < toKey.length; j++) {
					Node toNode = (Node) toKey[j];
					if (toNode.getName().equals(to.getName())) {
						return fromNode;
					}
				}
			}
		}
		return null;
	}

	protected Node[] getFromNodes(Message m) { // man bekommt die Nodes die όber
											// eine Message in den fromNode von
											// m fόhren und nicht toNode von m
											// sind
		Hashtable<Node, Hashtable<Object, Double>> to = new Hashtable<Node, Hashtable<Object, Double>>();
		Object keyFrom[] = finalHash.keySet().toArray();
		int length = 0;
		for (int i = 0; i < keyFrom.length; i++) { // Sucht toHash mit dem
													// gegeben From der Message
													// m
			Node nodeFrom = (Node) keyFrom[i];
			to = finalHash.get(nodeFrom);
			Object keyTo[] = to.keySet().toArray();
			for (int j = 0; j < keyTo.length; j++) {
				Node nodeTo = (Node) keyTo[j];
				if (nodeTo.getName().equals(m.getFrom().getName())
						&& !nodeFrom.getName().equals(m.getTo().getName())) {
					length++;
				}
			}
		}
		Node[] toNodeArray = new Node[length];

		int k = 0;
		for (int i = 0; i < keyFrom.length; i++) { // Sucht toHash mit dem
													// gegeben From der Message
													// m
			Node nodeFrom = (Node) keyFrom[i];
			to = finalHash.get(nodeFrom);
			Object keyTo[] = to.keySet().toArray();
			for (int j = 0; j < keyTo.length; j++) {
				Node nodeTo = (Node) keyTo[j];
				if (!nodeFrom.getName().equals(m.getTo().getName())
						&& nodeTo.getName().equals(m.getFrom().getName())) {
					toNodeArray[k] = nodeFrom;
					k++;
				}
			}
		}
		return toNodeArray;

	}

	protected Graph getGraph() {
		return graph;
	}

	/*
	 * Getters & Setters:
	 */

	protected Hashtable<Object, Double>[] getMessageValues(Message m) { // gibt
																		// Array
																		// zurόck,
																		// der
																		// die
																		// einzelnen
																		// DefHash
																		// enthδlt
		Hashtable<Node, Hashtable<Object, Double>> to = new Hashtable<Node, Hashtable<Object, Double>>();
		Object keyFrom[] = finalHash.keySet().toArray();
		int length = 0;
		for (int i = 0; i < keyFrom.length; i++) { // Sucht toHash mit dem
													// gegeben From der Message
													// m (zunδchst nur um die
													// lδnge zu bestimmen)
			Node nodeFrom = (Node) keyFrom[i];
			to = finalHash.get(nodeFrom);
			Object keyTo[] = to.keySet().toArray();
			for (int j = 0; j < keyTo.length; j++) {
				Node nodeTo = (Node) keyTo[j];
				if (!nodeFrom.getName().equals(m.getTo().getName())
						&& nodeTo.getName().equals(m.getFrom().getName())) {
					length++;
				}
			}
		}
		Hashtable<Object, Double>[] valueArray = new Hashtable[length];

		int k = 0;
		for (int i = 0; i < keyFrom.length; i++) { // Sucht toHash mit dem
													// gegeben From der Message
													// m(um eintrδge zu machen)
			Node nodeFrom = (Node) keyFrom[i];
			to = finalHash.get(nodeFrom);
			Object keyTo[] = to.keySet().toArray();
			for (int j = 0; j < keyTo.length; j++) {
				Node nodeTo = (Node) keyTo[j];
				if (!nodeFrom.getName().equals(m.getTo().getName())
						&& nodeTo.getName().equals(m.getFrom().getName())) {
					Hashtable<Object, Double> valueHash = finalHash.get(
							nodeFrom).get(nodeTo);
					valueArray[k] = valueHash;
					k++;
				}
			}
		}
		return valueArray;
	}

	protected LinkedList<Message> getMSeq() {
		return mSeq;
	}

	protected double getValue(Node from, Node to, Object def) {
		double value = finalHash.get(from).get(to).get(def);
		return value;
	}

	protected void setFinalHash(
			Hashtable<Node, Hashtable<Node, Hashtable<Object, Double>>> finalHash) {
		this.finalHash = finalHash;
	}

	protected void setGraph(Graph graph) {
		this.graph = graph;
	}

	protected void setMSeq(LinkedList<Message> seq) {
		mSeq = seq;
	}

}
