package com.ciphertext.encrypt;

import com.ciphertext.input.StringIO;
import com.ciphertext.interfaces.CipherInterface;

public class Encryptor implements CipherInterface {

	private StringIO stringIO;
	private char[][] matrix;
	private int matrixSize = 0;
	private int extraRows = 0;

	public Encryptor(StringIO inputString) {
		this.stringIO = inputString;
	}

	public StringIO getInput() {
		return stringIO;
	}

	public void setInput(StringIO stringIO) {
		this.stringIO = stringIO;
	}

	public char[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(char[][] matrix) {
		this.matrix = matrix;
	}

	public int getMatrixSize() {
		return matrixSize;
	}

	public void setMatrixSize(int matrixSize) {
		this.matrixSize = matrixSize;
	}

	public int getExtraRows() {
		return extraRows;
	}

	public void setExtraRows(int extraRows) {
		this.extraRows = extraRows;
	}

	public void constructMatrix() {
		final int STRING_LENGTH = stringIO.getInput().length();
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
		for (int i = 0; i < matrixSize + extraRows; i++) {
			for (int j = 0; j < matrixSize; j++) {
				if (k < STRING_LENGTH) {
					matrix[i][j] = stringIO.getInput().charAt(k++);
				} else {
					matrix[i][j] = '*';
				}

			}
		}
	}

	public void getResultFromMatrix() {
		char[] outputString = new char[matrixSize * (matrixSize + extraRows)];
		int k = 0;
		for (int j = 0; j < matrixSize; j++) {
			for (int i = 0; i < matrixSize + extraRows && k < (matrixSize + extraRows) * matrixSize; i++) {
				outputString[k++] = matrix[i][j];
			}

		}
		stringIO.setOutput(new String(outputString));
	}
}
