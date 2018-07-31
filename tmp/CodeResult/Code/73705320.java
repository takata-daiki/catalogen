/**
 * Code.java
 *
 * This class represents a code. This includes the answer code
 * that the codemaker makes and the guesses that the codebreaker
 * makes.
 * 
 * @author Josh
 */

package model;

import java.util.ArrayList;

public class Code {
	
	private CodePegs[] code;
	private int pegSize;
	
	/**
	 * Constructor for a code in the game of Mastermind
	 * 
	 * @param code - The list of code pegs that make up the code
	 */
	public Code(CodePegs[] code, int pegSize) {
		
		//validate the length of the code
		if(code.length != pegSize)
			throw new IllegalArgumentException();
		
		//validate that all elements in the code are assigned
		for(int i = 0; i < pegSize; i++) {
			if(code[i] == null)
				throw new IllegalArgumentException();
		}
		
		this.code = code;
		this.pegSize = pegSize;
		
	}
	
	/**
	 * Constructor from an array list of peg names
	 */
	public Code(ArrayList<String> pegNames, int pegSize) {
		CodePegs[] guess = new CodePegs[pegSize];
		for(int i = 0; i < pegNames.size(); i++) {
			CodePegs feedback = CodePegs.valueOf(pegNames.get(i));
			guess[i] = feedback;
		}
		this.code = guess;
		this.pegSize = pegSize;
	}
	
	/**
	 * This function returns the list of code pegs
	 * 
	 * @return code - The list of code pegs that make up the code
	 */
	public CodePegs[] getCode() {
		return code;
	}
	
	/**
	 * This function creates the string representation of a code
	 * 
	 * @return result - the string representation of a code
	 */
	@Override
	public String toString() {
        String result = "{";
        for (int i = 0;i < this.pegSize ;i++)
            result += code[i] + ", ";
        result = result.substring(0, result.length() - 2);
        result += "}";
        return result;
    }
	
//	/**
//	 * This function compares two codes. This will be used when the requirements 
//	 * change so that we need to have a computer codemaker.
//	 * 
//	 * @param other - The other code that we are comparing ourselves to
//	 * @return true if the code contain the same pegs
//	 */
//	@Override
//	public boolean equals(Object other) {
//		if(other.getClass() == Code.class) {
//			Code otherCode = (Code)other;
//			for(int i = 0;i < GameController.gamePegSize;i++)
//				if(code[i] != otherCode.getCode()[i])
//					return false;
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
	
	public static ArrayList<String> makeGuessImageList(ArrayList<Code> allGuesses) {
		ArrayList<String> guessesString = new ArrayList<String>();
		//iterate the guesses
		for (int i = 0; i < allGuesses.size(); i++) {
			Code code = allGuesses.get(i);
			CodePegs[] currGuess = code.getCode();
			//iterate each guesses' pegs
			for (int j = 0; j < currGuess.length; j++ ) {
				String colorFileName = currGuess[j].name() + ".png";
				guessesString.add(colorFileName);
			}
		}
		return guessesString;		
	}
	
}
