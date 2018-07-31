/**
 * Match specifies a bijection between proteins and drugs such that 
 * the sum of all bindings affinities across pairs is maximized.
 * 
 * written by Michael Kirschbaum
 */

package backendProblem1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Match {
	public static void main(String[] args) {
		Scanner input = null;
		BufferedReader in = null;
		Protein[] proteins;
		Drug[] drugs;
		Pair[] maxMatches;
		int pairs_count = 0;
		float maximumBA = 0;
		String name;
		
		if (args.length != 2) {		// check that two files are provided
			System.out.println("Usage: backendProblem1.Match <path to protein names file> <path to drug names file>");
			System.exit(0);
		}
		
		try {						// count number of proteins for array allocation
			input = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		while (input.hasNext()) {
			++pairs_count;
			input.next();
		}
		input.close();
		
		try {
			in = new BufferedReader(new FileReader(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		proteins = new Protein[pairs_count];	// read protein names
		int i = 0;
		try {
			while ((name = in.readLine()) != null) {
				proteins[i] = new Protein(name);
				++i;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			in = new BufferedReader(new FileReader(args[1]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		drugs = new Drug[pairs_count];		// read drug names
		i = 0;		
		try {
			while ((name = in.readLine()) != null) {
				drugs[i] = new Drug(name);
				++i;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Affinities BA = new Affinities(proteins, drugs); // create map of protein-drug affinities
		
		maxMatches = BA.getMaxMatches(); 				 // get matches that maximize total binding affinity

		System.out.print("Maximum Binding Affinity: ");	 // display maximum binding affinity
		for (i = 0; i < maxMatches.length; i++)
			maximumBA += maxMatches[i].getAffinity();
		System.out.println(maximumBA + "\n");
														 // list protein-drug pairs
		System.out.println("Protein                                                  Drug                            BA");
		System.out.println("-------------------------------------------------------- ---------------------------- ------");
		for (i = 0; i < maxMatches.length; i++)
			maxMatches[i].display();
	}
}
