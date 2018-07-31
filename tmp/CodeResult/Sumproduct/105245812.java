package de.lmu.genzentrum.tresch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
	
	public static double[][] stringToArray(String initialValue){
		double[][] value = null;
		
		String[] init=initialValue.split("!");
		if (init.length==2){
			String start=init[0];
			String[] startStrings=start.split("x");
			value=new double[Integer.parseInt(startStrings[0])][Integer.parseInt(startStrings[1])];		
			
			String array=init[1];
			if (array.startsWith("{") && array.endsWith("}")) {
				String substring = array.substring(1,
						array.length() - 1);
				String[] lines=substring.split(";");

				for (int i = 0; i <lines.length; i++) {
					String line = lines[i];
					if (line.startsWith("[") && line.endsWith("]")) {
						String linesubstring = line.substring(1, line.length() - 1);
						String[] lineValues = linesubstring.split(",");
						double[] lineDoubles=new double[lineValues.length];
						for(int l=0;l<lineValues.length;l++)
							lineDoubles[l]=(Double.valueOf(lineValues[l]).doubleValue());
						value[i]=lineDoubles;
						}
					}
				}
			}
		return value;
	}
	public static String arrayToString(double[][] value){
		StringBuffer sb = new StringBuffer();
		sb.append(value.length+"x"+value[0].length+"!");
		sb.append("{");
		for (int i = 0; i < value.length; i++) {
			sb.append(Arrays.toString(value[i]));
			if (i != value.length - 1)
				sb.append(";");
		}
		sb.append("}");
		return sb.toString();
	}
	public static void main(String[] args) throws NoValueException {
		Object[] defWerte = new Object[2];
		defWerte[0] = 0;
		defWerte[1] = 1;

		Object[][] funcFA = new Object[2][2];
		funcFA[0][0] = 0;
		funcFA[1][0] = 0.4;
		funcFA[0][1] = 1;
		funcFA[1][1] = 0.6;

		Object[][] funcFB = new Object[2][2];
		funcFB[0][0] = 0;
		funcFB[1][0] = 0.7;
		funcFB[0][1] = 1;
		funcFB[1][1] = 0.3;

		String[] nodeArrayFC = new String[3];
		nodeArrayFC[0] = "x1";
		nodeArrayFC[1] = "x2";
		nodeArrayFC[2] = "x3";
		Object[][] funcFC = new Object[4][8];
		funcFC[0][0] = 0;
		funcFC[1][0] = 0;
		funcFC[2][0] = 0;
		funcFC[3][0] = 0.1;
		funcFC[0][1] = 1;
		funcFC[1][1] = 0;
		funcFC[2][1] = 0;
		funcFC[3][1] = 0.2;
		funcFC[0][2] = 0;
		funcFC[1][2] = 1;
		funcFC[2][2] = 0;
		funcFC[3][2] = 0.05;
		funcFC[0][3] = 0;
		funcFC[1][3] = 0;
		funcFC[2][3] = 1;
		funcFC[3][3] = 0.15;
		funcFC[0][4] = 1;
		funcFC[1][4] = 1;
		funcFC[2][4] = 0;
		funcFC[3][4] = 0.2;
		funcFC[0][5] = 1;
		funcFC[1][5] = 0;
		funcFC[2][5] = 1;
		funcFC[3][5] = 0.1;
		funcFC[0][6] = 0;
		funcFC[1][6] = 1;
		funcFC[2][6] = 1;
		funcFC[3][6] = 0.15;
		funcFC[0][7] = 1;
		funcFC[1][7] = 1;
		funcFC[2][7] = 1;
		funcFC[3][7] = 0.05;

		String[] nodeArrayFD = new String[2];
		nodeArrayFD[0] = "x3";
		nodeArrayFD[1] = "x4";
		Object[][] funcFD = new Object[3][4];
		funcFD[0][0] = 0;
		funcFD[1][0] = 0;
		funcFD[2][0] = 0.25;
		funcFD[0][1] = 1;
		funcFD[1][1] = 0;
		funcFD[2][1] = 0.2;
		funcFD[0][2] = 0;
		funcFD[1][2] = 1;
		funcFD[2][2] = 0.15;
		funcFD[0][3] = 1;
		funcFD[1][3] = 1;
		funcFD[2][3] = 0.4;

		String[] nodeArrayFE = new String[2];
		nodeArrayFE[0] = "x3";
		nodeArrayFE[1] = "x5";
		Object[][] funcFE = new Object[3][4];
		funcFE[0][0] = 0;
		funcFE[1][0] = 0;
		funcFE[2][0] = 0.3;
		funcFE[0][1] = 1;
		funcFE[1][1] = 0;
		funcFE[2][1] = 0.15;
		funcFE[0][2] = 0;
		funcFE[1][2] = 1;
		funcFE[2][2] = 0.2;
		funcFE[0][3] = 1;
		funcFE[1][3] = 1;
		funcFE[2][3] = 0.35;

		FactorNode f1 = new FactorNode("fA", 0, funcFA);
		FactorNode f2 = new FactorNode("fB", 2, funcFB);
		FactorNode f3 = new FactorNode("fC", 8, funcFC, nodeArrayFC);
		FactorNode f4 = new FactorNode("fD", 5, funcFD, nodeArrayFD);
		FactorNode f5 = new FactorNode("fE", 7, funcFE, nodeArrayFE);

		VariableNode v1 = new VariableNode("x1", 1, defWerte);
		VariableNode v2 = new VariableNode("x2", 3, defWerte);
		VariableNode v3 = new VariableNode("x3", 9, defWerte);
		VariableNode v4 = new VariableNode("x4", 4, defWerte);
		VariableNode v5 = new VariableNode("x5", 6, defWerte);

		LinkedList<Message> mSeq = new LinkedList<Message>();

		Message message = new Message(f1, v1);
		mSeq.add(message);

		message = new Message(f2, v2);
		mSeq.add(message);

		message = new Message(v4, f4);
		mSeq.add(message);

		message = new Message(v5, f5);
		mSeq.add(message);

		message = new Message(v1, f3);
		mSeq.add(message);

		message = new Message(v2, f3);
		mSeq.add(message);

		message = new Message(f4, v3);
		mSeq.add(message);

		message = new Message(f5, v3);
		mSeq.add(message);

		message = new Message(f3, v3);
		mSeq.add(message);

		message = new Message(v3, f3);
		mSeq.add(message);

		message = new Message(f3, v1);
		mSeq.add(message);

		message = new Message(f3, v2);
		mSeq.add(message);

		message = new Message(v3, f4);
		mSeq.add(message);

		message = new Message(v3, f5);
		mSeq.add(message);

		message = new Message(v1, f1);
		mSeq.add(message);

		message = new Message(v2, f2);
		mSeq.add(message);

		message = new Message(f4, v4);
		mSeq.add(message);

		message = new Message(f5, v5);
		mSeq.add(message);

		Graph test = new Graph(mSeq);
		SumProduct sum = new SumProduct();
		sum.doSum(test);
		test.print();

		Object[][] values = funcFC;

	//	System.out.println("initialValue: "+sb.toString());
		
		String initialValue = "3x3!{[0,1,2];[0,1,2];[0,1,2]}";
		System.out.println(initialValue);
		double[][] result= stringToArray(initialValue);
		String r=arrayToString(result);
		System.out.println(r);
		
		
		for(int y=0;y<result.length;y++)
		System.out.println("loadedValue: "+Arrays.toString(result[y]));
		

		

		/*
		 * value = new double[x][y]; double[] linearray = new double[y]; for
		 * (int l = 0; l <yString.length;l++) { double d =
		 * Double.valueOf(yString[i]).doubleValue(); value[l][i] = d; } }
		 */
		/*
		 * //System.out.println(funcFC.length); System.out.print("{"); for (int
		 * i = 0; i < funcFC.length; i++){
		 * System.out.print(Arrays.toString(funcFC[i])); for (int j = 0; j <
		 * funcFC[i].length; j++) { //System.out.println(funcFC[i][j]); } }
		 * System.out.print("}");
		 */

	}

}
