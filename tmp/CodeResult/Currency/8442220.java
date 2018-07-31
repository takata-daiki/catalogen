/**
 * jaolt - Java Auction Organisation, Listing Tool
 * Copyright (C) 2006-2012 Stefan Handschuh
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version. 
 * 
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110, USA
 *
 */

package de.shandschuh.jaolt.core.auction;

public class Currency implements Cloneable {
	/** Euro */
	public static final Currency EUR = new Currency("EUR");
	
	/** US Dollar */
	public static final Currency USD = new Currency("USD", '.');
	
	/** Indian Rupee */
	public static final Currency INR = new Currency("INR", '.');
	
	/** Canadian Dollar */
	public static final Currency CAD = new Currency("CAD", '.');
	
	/** Chinese Yuan */
	public static final Currency CHY = new Currency("CNY", '.');
	
	/** Hongkong Dollar */
	public static final Currency HKD = new Currency("HKD", '.');
	
	/** Australian Dollar */
	public static final Currency AUD = new Currency("AUD", '.');
	
	/** Swiss Franc */
	public static final Currency CHF = new Currency("CHF");
	
	/** Malaysian Ringgit */
	public static final Currency MYR = new Currency("MYR", '.');
	
	/** Philippine Peso */
	public static final Currency PHP = new Currency("PHP", '.');
	
	/** Polish Zloty */
	public static final Currency PLN = new Currency("PLZ");
	
	/** Singapoore Dollar */
	public static final Currency SGD = new Currency("SGD", '.');
	
	/** Swedish Krona */
	public static final Currency SEK = new Currency("SEK");
	
	/** Taiwanese Dollar */
	public static final Currency TWD = new Currency("TWD", '.');
	
	/** British Pound */
	public static final Currency GBP = new Currency("GBP");

	/** Japanese Yen */
	public static final Currency JPY = new Currency("YEN", '.');;
	
	/** United Arab Emirates Dirham */
	public static final Currency AED = new Currency("AED", '.');

	/** Jordanian dinar */
	public static final Currency JOD = new Currency("JOD", '.');

	/** Saudi riyal */
	public static final Currency SAR = new Currency("SAR", '.');

	/** Danish Krone */
	public static final Currency DKK = new Currency("DKK", ',');

	/** Norwegian Krone */
	public static final Currency NOK = new Currency("NOK", ',');
	
	/** Turkish Lira */
	public static final Currency TRY = new Currency("TRY",  '.');
	
	private String shortcut;

	private char dotSymbol;

	private Currency(String shortcut, char dotSymbol) {
		this.dotSymbol = dotSymbol;
		this.shortcut = shortcut;
	}

	private Currency(String kuerzel) {
		this(kuerzel, ',');
	}

	private Currency() {
		this("EUR");
	}

	public char getDotSymbol() {
		return dotSymbol;
	}

	public String getShortcut() {
		return shortcut;
	}

	public void setDotSymbol(char dotSymbol) {
		this.dotSymbol = dotSymbol;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}
	
	public String toString(double value) {
		return toString(value, true);
	}

	public String toString(double value, boolean addShortcut) {
		value = Math.round(value * 100d) / 100d;
		return Double.toString(value).replace('.', dotSymbol)
				+ (Math.round(value * 10d) != (value * 10d) ? "" : "0")
				+ (addShortcut ? " " + shortcut : "");
	}

	public double toDouble(String text) {
		return Math.round(Double.parseDouble(text.trim().replace(getDotSymbol(),
				'.')) * 100d) / 100d;
	}
	
	public boolean equals(Currency currency) {
		return currency == this;
	}
	
	public static Currency create(String shortCut) {
		if ("EUR".equals(shortCut)) {
			return EUR;
		} else if ("USD".equals(shortCut)) {
			return USD;
		} else if ("INR".equals(shortCut)) {
			return INR;
		} else if ("CAD".equals(shortCut)) {
			return CAD;
		} else if ("CHY".equals(shortCut)) {
			return CHY;
		} else if ("AUD".equals(shortCut)) {
			return AUD;
		} else if ("HKD".equals(shortCut)) {
			return HKD;
		} else if ("CHF".equals(shortCut)) {
			return CHF;
		} else if ("PHP".equals(shortCut)) {
			return PHP;
		} else if ("PLN".equals(shortCut)) {
			return PLN;
		} else if ("SGD".equals(shortCut)) {
			return SGD;
		} else if ("SEK".equals(shortCut)) {
			return SEK;
		} else if ("TWD".equals(shortCut)) {
			return TWD;
		} else if ("GBP".equals(shortCut)) {
			return GBP;
		} else if ("JPY".equals(shortCut)) {
			return JPY;
		} else if ("AED".equals(shortCut)){
			return AED;
		} else if ("SAR".equals(shortCut)){
			return SAR;
		} else if ("JOD".equals(shortCut)){
			return JOD;
		} else if ("NOK".equals(shortCut)){
			return NOK;
		} else if ("DKK".equals(shortCut)){
			return DKK;
		} else {
			return USD;
		}
	}
}
