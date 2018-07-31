/*
 * Index.java
 *
 * Created on 2008/12/31, 23:48
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package neuralnetwork;

import java.util.Date;

/**
 *
 * @author T2
 */
public class Index {

	public static final int nBit = 12;
	public static final double cutOff=10;
	public static final int RANGE = (int) Math.pow(2, nBit);
	private static final boolean grayCoding = true;
	private Date date;
	private int r;
	private double d;
	private boolean[] bitString;

	/** Creates a new instance of Index */
	public Index(Date date, int r) {
		this.date = date;
		this.r = r;
		this.d = r;
	//bitString=encode(r,nBit);
	}

	public boolean[] getBitString() {
		return bitString;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
		this.d = r;
	}

	public double getRd() {
		return d;
	}

	public void setRd(double d) {
		this.d = d;
		this.r = (int) d;
	}

	public Date getDate() {
		return date;
	}

	public void init() {
		bitString = encode(r, nBit);
	}

	public static long decode(boolean[] bits) {
		if (grayCoding) {
			return decodeGray(bits);
		} else {
			return decodeBinary(bits);
		}
	}

	public static long decodeGray(boolean[] bits) {
		long value = 0;
		int length = bits.length;
		boolean[] buf = new boolean[length];
		buf[length - 1] = bits[length - 1];
		for (int i = 1; i < length; i++) {
			buf[length - 1 - i] = bits[length - 1 - i] ^ buf[length - i];
		}
		for (int i = 0; i < length; i++) {
			if (buf[i]) {
				value += Math.pow(2, i);
			}
		}
		return value;
	}

	public static long decodeBinary(boolean[] bits) {
		long value = 0;
		for (int i = 0; i < bits.length; i++) {
			if (bits[i]) {
				value += Math.pow(2, i);
			}
		}
		return value;
	}

	public static boolean[] encode(int value) {
		return encode(value, nBit);
	}

	public static boolean[] encode(int value, int nBit) {
		if (grayCoding) {
			return encodeGray(value, nBit);
		} else {
			return encodeBinary(value, nBit);
		}
	}

	public static boolean[] encodeGray(int value, int nBit) {
		boolean[] bits = encodeBinary(value,nBit);
		/*String str = Long.toBinaryString(value);
		//System.out.println(value+"\t"+str+"vava");
		int length = str.length();
		//for (int i = 0; i < nBit; i++)bits[i]=false;
		for (int i = 0; i < length; i++) {
			if(str.charAt(length-i-1)=='0')bits[i]=false;
			else if (str.charAt(length - i - 1) == '1') {
				bits[i] = true;
			}
		}*/
		for (int i = 0; i < nBit - 1; i++) {
			bits[i] = bits[i] ^ bits[i + 1];
		}
		return bits;
	}

	public static boolean[] encodeBinary(int value, int nBit) {
		boolean[] bits = new boolean[nBit];
		/*String str = Long.toBinaryString(value);
		int length = str.length();
		//for (int i = 0; i < nBit; i++)bits[i]=false;
		for (int i = 0; i < length; i++) {
			if(str.charAt(i)=='0')bits[length-i-1]=false;
			else if (str.charAt(i) == '1') {
				bits[length - i - 1] = true;
			}
		}*/
		int a = value;
		int b = (int) Math.pow(2, nBit);
		for (int i = 0; i < nBit; i++) {
			if (a >= b) {
				bits[nBit-i-1] = true;
				a -= b;
			}
			b /= 2;
		}
		return bits;
	}
}
