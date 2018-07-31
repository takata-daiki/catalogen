package de.lmu.genzentrum.tresch;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Properties;

import org.apache.commons.collections.MapUtils;

public class Main {

	public static void main(String[] args) throws NoValueException {
		double[] defWerte=new double[2];
		defWerte[0]=0;
		defWerte[1]=1;
		
		
		double[][] funcFA=new double[2][2];
		funcFA[0][0]=0;
		funcFA[1][0]=0.4;
		funcFA[0][1]=1;
		funcFA[1][1]=0.6;
		
		double[][] funcFB=new double[2][2];
		funcFB[0][0]=0;
		funcFB[1][0]=0.7;
		funcFB[0][1]=1;
		funcFB[1][1]=0.3;
		
		int[] nodeArrayFC=new int[3];
		nodeArrayFC[0]=1;
		nodeArrayFC[1]=3;
		nodeArrayFC[2]=9;		
		double[][] funcFC=new double[4][8];
		funcFC[0][0]=0;
		funcFC[1][0]=0;
		funcFC[2][0]=0;
		funcFC[3][0]=0.1;
		funcFC[0][1]=1;
		funcFC[1][1]=0;
		funcFC[2][1]=0;
		funcFC[3][1]=0.2;
		funcFC[0][2]=0;
		funcFC[1][2]=1;
		funcFC[2][2]=0;
		funcFC[3][2]=0.05;
		funcFC[0][3]=0;
		funcFC[1][3]=0;
		funcFC[2][3]=1;
		funcFC[3][3]=0.15;
		funcFC[0][4]=1;
		funcFC[1][4]=1;
		funcFC[2][4]=0;
		funcFC[3][4]=0.2;
		funcFC[0][5]=1;
		funcFC[1][5]=0;
		funcFC[2][5]=1;
		funcFC[3][5]=0.1;
		funcFC[0][6]=0;
		funcFC[1][6]=1;
		funcFC[2][6]=1;
		funcFC[3][6]=0.15;
		funcFC[0][7]=1;
		funcFC[1][7]=1;
		funcFC[2][7]=1;
		funcFC[3][7]=0.05;
		
		int[] nodeArrayFD=new int[2];
		nodeArrayFD[0]=9;
		nodeArrayFD[1]=4;
		double[][] funcFD=new double[3][4];
		funcFD[0][0]=0;
		funcFD[1][0]=0;
		funcFD[2][0]=0.25;
		funcFD[0][1]=1;
		funcFD[1][1]=0;
		funcFD[2][1]=0.2;
		funcFD[0][2]=0;
		funcFD[1][2]=1;
		funcFD[2][2]=0.15;
		funcFD[0][3]=1;
		funcFD[1][3]=1;
		funcFD[2][3]=0.4;
		
		int[] nodeArrayFE=new int[2];
		nodeArrayFE[0]=9;
		nodeArrayFE[1]=6;
		double[][] funcFE=new double[3][4];
		funcFE[0][0]=0;
		funcFE[1][0]=0;
		funcFE[2][0]=0.3;
		funcFE[0][1]=1;
		funcFE[1][1]=0;
		funcFE[2][1]=0.15;
		funcFE[0][2]=0;
		funcFE[1][2]=1;
		funcFE[2][2]=0.2;
		funcFE[0][3]=1;
		funcFE[1][3]=1;
		funcFE[2][3]=0.35;
		
		FactorNode f1=new FactorNode("fA",0,funcFA);
		FactorNode f2=new FactorNode("fB",2,funcFB);
		FactorNode f3=new FactorNode("fC",8, funcFC, nodeArrayFC);
		FactorNode f4=new FactorNode("fD",5, funcFD, nodeArrayFD);
		FactorNode f5=new FactorNode("fE",7, funcFE, nodeArrayFE);
		
		VariableNode v1=new VariableNode("x1",1, defWerte, false);
		VariableNode v2=new VariableNode("x2",3, defWerte, false);
		VariableNode v3=new VariableNode("x3",9, defWerte, false);	
		VariableNode v4=new VariableNode("x4",4, defWerte, true);
		VariableNode v5=new VariableNode("x5",6, defWerte, true);
		
		LinkedList<Message> mSeq= new LinkedList<Message>();
		
		Message message=new Message(f1, v1);
		mSeq.add(message);
		
		message=new Message(f2, v2);
		mSeq.add(message);
		
		message=new Message(v4, f4);
		mSeq.add(message);
		
		message=new Message(v5, f5);
		mSeq.add(message);
		
		message=new Message(v1, f3);
		mSeq.add(message);
		
		message=new Message(v2, f3);
		mSeq.add(message);
		
		message=new Message(f4, v3);
		mSeq.add(message);
		
		message=new Message(f5, v3);
		mSeq.add(message);
		
		message=new Message(f3, v3);
		mSeq.add(message);
		
		message=new Message(v3, f3);
		mSeq.add(message);
		
		message=new Message(f3, v1);
		mSeq.add(message);
		
		message=new Message(f3, v2);
		mSeq.add(message);
		
		message=new Message(v3, f4);
		mSeq.add(message);
		
		message=new Message(v3, f5);
		mSeq.add(message);
		
		message=new Message(v1, f1);
		mSeq.add(message);
		
		message=new Message(v2, f2);
		mSeq.add(message);
		
		message=new Message(f4, v4);
		mSeq.add(message);
		
		message=new Message(f5, v5);
		mSeq.add(message); 
	

		Graph test = new Graph(mSeq);
		SumProduct sum = new SumProduct(test);
		sum.doSum();
		test.print();
		System.out.println("----------------------- max product");
		MaxProduct max= new MaxProduct(test, v5);
		/*	max.calcFactorToVariable(0);
			max.calcFactorToVariable(1);
			max.calcVariableToFactor(2);
			max.calcVariableToFactor(3);
			max.calcVariableToFactor(4);
			max.calcVariableToFactor(5);
			max.calcFactorToVariable(6);
			max.calcFactorToVariable(7);
			max.calcFactorToVariable(8);
			max.calcVariableToFactor(9);
			max.calcFactorToVariable(10);
			max.calcFactorToVariable(11);
			max.calcVariableToFactor(12);
			max.calcVariableToFactor(12);
			max.calcVariableToFactor(13);
			max.calcVariableToFactor(14);
			max.calcVariableToFactor(15);
			max.calcFactorToVariable(16);
			max.calcFactorToVariable(17); */
			max.doMax();
			System.out.println("");
			//test.print();	
	}

}
