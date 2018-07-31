package edu.berkeley.nlp.assignments;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.berkeley.nlp.util.CommandLineUtils;
import edu.berkeley.nlp.util.Counter;
import edu.berkeley.nlp.util.CounterMap;
import edu.berkeley.nlp.util.Pair;
import edu.berkeley.nlp.util.PriorityQueue;

public class Decryptor {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String code = "ttttttttttttooooooooooeeeeeeeeaaaaaaallllllnnnnnnuuuuuuiiiiisssssdddddhhhhhyyyyyIIIrrrfffbbwwkcmvg";
		Counter<String> chars = new Counter<String>();
		for (int i=0; i<code.length(); i++) {
			chars.incrementCount(code.charAt(i) + "", 1.0);
		}

		// read vocab in from file
		Set<String> vocab = new HashSet<String>();
		Map<String,String> argMap = CommandLineUtils.simpleCommandLineParser(args);
		String filename = argMap.get("-path") + "\\qwantzcorpus.txt";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line = "";
		while (in.ready()) {
			line = in.readLine();
			String[] parts = line.split(" ");
			String word = parts[1];
			vocab.add(word);
		}

		decrypt(vocab, chars);
	}

	public static void decrypt(Set<String> vocab, Counter<String> chars) {
		String[] knowns = {"fundamental", "I", "I", "I"};
		Counter<String> reverseChars = new Counter<String>(chars);
		for (String c : reverseChars.keySet()) {
			reverseChars.setCount(c, -reverseChars.getCount(c));
		}
		PriorityQueue<String> pq = chars.asPriorityQueue();



		CounterMap<String, String> wordsByChar = new CounterMap<String, String>();
		for (String word : vocab) {
			for (int i=0; i<word.length(); i++) {
				String c = Character.toString(word.charAt(i));
				wordsByChar.incrementCount(c, word, 1.0);
			}
		}



		List<String> baseSet = new ArrayList<String>();
		for (int i=0; i<knowns.length; i++) {
			baseSet.add(knowns[i]);
		}
		Set<List<String>> wordSets = new HashSet<List<String>>();
		wordSets.add(baseSet);



		while (pq.hasNext()) {
			String c = pq.removeFirst();
			double totalCount = chars.getCount(c);

			Set<Pair<List<String>, Integer>> workingSet = new HashSet<Pair<List<String>, Integer>> ();
			Set<List<String>> finalSet = new HashSet<List<String>> ();
			for (List<String> prevSet : wordSets) {
				int charCount = countChars(prevSet, c);
				//				System.out.println(prevSet + " " + charCount);
				if (charCount < totalCount) {
					workingSet.add(new Pair<List<String>, Integer>(new ArrayList<String>(prevSet), charCount));
				} else if (charCount == totalCount) {
					finalSet.add(new ArrayList<String>(prevSet));
					//					System.out.println(prevSet);
				}
			}
			System.out.println("*** " + c + " working set established ***");

			for (String word : wordsByChar.getCounter(c).keySet()) {
				//				System.out.println(word);
				double charCount = wordsByChar.getCount(c, word);

				for (Pair<List<String>, Integer> combo : workingSet) {
					if (combo.getSecond() + charCount < totalCount) {	// include
						List<String> newWordSet = new ArrayList<String>(combo.getFirst());
						newWordSet.add(word);
						int newCount = (int)(combo.getSecond() + charCount);
						Pair<List<String>, Integer> newPair = new Pair<List<String>, Integer>(newWordSet, newCount);
						workingSet.add(newPair);												// hopefully means that we can have duplicates of this word?
						//						System.out.println(newPair.getFirst());
					} else if (combo.getSecond() + charCount == totalCount) {	// include but skip some steps
						List<String> newWordSet = new ArrayList<String>(combo.getFirst());
						newWordSet.add(word);
						finalSet.add(newWordSet);
						//						System.out.println(newWordSet);
					} else {		// exclude
						; // do nothing, set without this word is already in the working set.
					}
				}

			}

			cleanMap(wordsByChar, c);
			System.out.println("Final Set: 	" + c + " " + finalSet.size());
			printHead(finalSet);

			wordSets = new HashSet<List<String>>(finalSet);
		}

	}

	static int countChars(List<String> words, String c) {
		int count = 0;

		for (String word : words) {
			for (int i=0; i<word.length(); i++) {
				if (Character.toString(word.charAt(i)).equals(c)) {
					count ++;
				}
			}
		}
		return count;
	}

	static void printHead(Set<List<String>> bigSet) {
		Set<List<String>> headSet = new HashSet<List<String>>();

		Iterator<List<String>> iter = bigSet.iterator();
		int count = 0;
		int max = 100;
		while (iter.hasNext() && count < max) {
			List<String> list = iter.next();
			headSet.add(list);
			count++;
		}

		System.out.println(headSet);
	}

	static void cleanMap(CounterMap<String, String> map, String c) {
		Set<String> words = new HashSet<String>(map.getCounter(c).keySet());

		for (String d : map.keySet()) {
			for (String word : words) {
				map.getCounter(d).removeKey(word);
			}
		}
		System.out.println("Removed " + words.size() + " words for " + c);
	}
}

