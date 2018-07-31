package com.ciphertext.decrypt;

import com.ciphertext.input.StringIO;
import com.ciphertext.interfaces.CipherInterface;

public class Decryptor implements CipherInterface {

	private StringIO input;
	private char[][] matrix;
	private int matrixSize = 0;
	private int extraRows = 0;

	public StringIO getInput() {
		return input;
	}

	public void setInput(StringIO input) {
		this.input = input;
	}

	public char[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(char[][] matrix) {
		this.matrix = matrix;
	}

	public int getMATRIX_SIZE() {
		return matrixSize;
	}

	public void setMATRIX_SIZE(int mATRIX_SIZE) {
		matrixSize = mATRIX_SIZE;
	}

	public int getExtraRows() {
		return extraRows;
	}

	public void setExtraRows(int extraRows) {
		this.extraRows = extraRows;
	}

	public Decryptor(StringIO input) {
		this.input = input;
	}

	public void constructMatrix() {

		final int STRING_LENGTH = input.getInput().length();
		int iter = 1;
		boolean found = false;
		while (!found) {
			if ((STRING_LENGTH >= iter * iter)
					&& (STRING_LENGTH < (iter + 1) * (iter + 1))) {
				matrixSize = iter;
				found = true;
			} else {
				iter++;
			}
		}

		extraRows = (int) Math.ceil((double) (STRING_LENGTH - (matrixSize * matrixSize))/ (matrixSize));

		matrix = new char[matrixSize + extraRows][matrixSize];

		// Inserting Characters into matrix
		int k = 0;
		for (int j = 0; j < matrixSize; j++) {
			for (int i = 0; i < matrixSize + extraRows; i++) {
				if (k < input.getInput().length()) {
					matrix[i][j] = input.getInput().charAt(k++);
				}
			}
		}
	}

	public void getResultFromMatrix() {
		char[] outputString = new char[input.getInput().length()];
		int k = 0;
		try {
			for (int i = 0; i < matrixSize + extraRows && k < input.getInput().length(); i++) {
				for (int j = 0; j < matrixSize ; j++) {
					outputString[k++] = matrix[i][j];
				}
			}
			String decryptedString = new String(outputString);
			decryptedString = decryptedString.replace("*", "");
			input.setOutput((decryptedString));
		} catch (ArrayIndexOutOfBoundsException e) {
			input.setOutput("Enter a valid String..!!");
		}
	}

}
