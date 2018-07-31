/*
 * Copyright (c) 2002 Widespace, OU.  All rights reserved.
 *
 * $Id: Currency.java,v 1.6 2007/08/15 17:01:59 igor Exp $
 * Version: $Name:  $
 */
package ee.widespace.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;


/**
 * @deprecated REVISIT: Use java.util.Currency?
 *
 * @author: Dmitri Silinski
 */
@Deprecated
public class Currency implements Comparable<Currency> {
	private final String alphabeticCode;
	private final String numericCode;
	private final int subdivision;
	private final int minorUnit;
	private final char separator;
	private final String symbol;
	private final String pattern;
	private final int round;

	private static Hashtable<String, Currency[]> currencies;

	static {
		initialaze();
	}

	// REVISIT: default construtor/access private fields

	/**
	 * Currency constructor.
	 * @param alphabeticCode  Currency ISO alphabetic code
	 * @param numericCode  Currency ISO numeric code
	 * @param symbol  Currency symbol
	 * @param minorUnit
	 * @param subdivision
	 * @param pattern  Currency pattern
	 * @param separator  Currency pattern separator
	 * @param round  Currency rounding
	 */
	protected Currency(final String alphabeticCode,
				final String numericCode, final String symbol,
				final int minorUnit, final int subdivision,
				final String pattern, final char separator, final int round)
	{
		this.alphabeticCode = alphabeticCode;
		this.numericCode = numericCode;
		this.subdivision = subdivision;
		this.minorUnit = minorUnit;
		this.separator = separator;
		this.symbol = symbol;
		this.pattern = pattern;
		this.round = round;
	}

	public String getAlphabeticCode() {
		return alphabeticCode;
	}

	/**
	 * Returns all available currencies
	 */
	public static Currency[] getAvailableCurrencies() {
		final Currency[] c = new Currency[currencies.size()];

		// REVISIT: value set -> array

		int i = 0;
		for (final Currency[] cs : currencies.values()) {
			if (cs != null && cs.length != 0) {
				c[i] = cs[0];
				++i;
			}
		}

		if (c.length != 0) {
			return c;
		}

		return null;
	}

	/**
	 * Return local currency
	 */
	public static Currency getInstance() {
		return getInstance(Locale.getDefault().getCountry());
	}

	/**
	 * Return currency, by currency ISO alphabetic symbol or ISO country code.
	 */
	public static Currency getInstance(final String code) {
		if (code == null) {
			return null;
		}

		switch (code.length()) {
			case 2: {
				// country code
				final Currency[] c = currencies.get(code);
				if (c == null || c.length == 0) {
					return null;
				}

				return c[0];
			}

			case 3:
				// currency code
				final Properties prop = new Properties();

				final InputStream in = Currency.class.getResourceAsStream(
					"/ee/widespace/util/resources/Currency.properties");
				try {
					try {
						prop.load(in);
					} finally {
						in.close();
					}
				} catch (final IOException e) {
					// ignore
				}

				final String prefix = "currency." + code;

				final String numeric = prop.getProperty(prefix + ".numeric");
				if (numeric == null) {
					return null;
				}

				final String symbol = prop.getProperty(prefix + ".symbol");
				final int subDivision = Integer.parseInt(prop.getProperty(prefix + ".subdivision").trim());
				final int minor = Integer.parseInt(prop.getProperty(prefix + ".minor").trim());
				final char separator =  prop.getProperty(prefix + ".separator").charAt(0);
				final String pattern = prop.getProperty(prefix + ".pattern");
				final int round = Integer.parseInt(prop.getProperty(prefix + ".round").trim());

				final Currency currency = new Currency(code, numeric, symbol, minor,
					subDivision, pattern, separator, round);

				return currency;

			default:
				throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns ISO alphabetic codes of local currencies.
	 */
	public static String[] getISOCurrencies() {
		return getISOCurrencies(Locale.getDefault().getCountry());
	}

	/**
	 * Returns ISO alphabetic codes of currencies by ISO country code.
	 */
	public static String[] getISOCurrencies(final String country) {
		final Currency[] c = currencies.get(country);
		if (c == null || c.length == 0) {
			return null;
		}

		final String[] str = new String[ c.length ];
		for (int i = 0; i < c.length; i++) {
			str[ i ] = c[ i ].getAlphabeticCode();
		}

		return str;
	}

	public int getMinorUnit() {
		return minorUnit;
	}

	public String getNumericCode() {
		return numericCode;
	}

	public String getPattern() {
		return pattern;
	}

	public int getRound () {
		return round;
	}

	public char getSeparator() {
		return separator;
	}

	public int getSubdivision() {
		return subdivision;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public int hashCode() {
		return alphabeticCode.hashCode();
	}

	private static void initialaze() {
		currencies = new Hashtable<String, Currency[]>();

		final Properties prop = new Properties();

		final InputStream in = Currency.class.getResourceAsStream(
			"/ee/widespace/util/resources/Currency.properties");
		try {
			try {
				prop.load(in);
			} finally {
				in.close();
			}
		} catch (final IOException e) {
			// ignore
		}

		int i = 0;

		String str = prop.getProperty("country." + Integer.toString(i));

		while (str != null) {
			final StringTokenizer st = new StringTokenizer(str);

			final String country = st.nextToken();

			final Currency[] c = new Currency[ st.countTokens() ];

			int j = 0;

			while (st.hasMoreTokens()) {
				final String code = st.nextToken();

				final String prefix = "currency." + code;

				final String numeric = prop.getProperty(prefix + ".numeric");
				if (numeric == null) {
					break;
				}

				final String symbol = prop.getProperty(prefix + ".symbol");
				final int subDivision = Integer.parseInt(prop.getProperty(prefix + ".subdivision").trim());
				final int minor = Integer.parseInt(prop.getProperty(prefix + ".minor").trim());
				final char separator =  prop.getProperty(prefix + ".separator").charAt(0);
				final String pattern = prop.getProperty(prefix + ".pattern");
				final int round = Integer.parseInt(prop.getProperty(prefix + ".round").trim());

				c[ j ] = new Currency(code, numeric, symbol, minor,
					subDivision, pattern, separator, round);

				++j;
			}

			if (j != 0) {
				currencies.put(country, c);
			}

			++i;

			str = prop.getProperty("country." + Integer.toString(i));
		}
	}

	public BigInteger toBigInteger(final String str) {
		if (subdivision > 0) {
			final int pos = str.indexOf(separator);

			if (str.length() - pos - 1 != minorUnit) {
				final StringBuffer buf = new StringBuffer(str);
				buf.deleteCharAt(pos);

				return new BigInteger(buf.toString());
			}

			if (pos > 0) {
				BigInteger result = new BigInteger(str.substring(0, pos));

				// REVISIT: valueOf()
				result = result.multiply(
					new BigInteger(String.valueOf(subdivision)));

				if (result.compareTo(BigInteger.ZERO) < 0) {
					result = result.subtract(
						new BigInteger(str.substring(pos+1)));
				} else {
					result = result.add(
						new BigInteger(str.substring(pos+1)));
				}

				return new BigInteger(String.valueOf(result));
			}

			return null;
		}

		return new BigInteger(str);
	}

	public String toString(final BigInteger count) {
		if (subdivision > 0) {
			// REVISIT: valueOf()
			final BigInteger first = count.divide(
				new BigInteger(String.valueOf(subdivision)));

			BigInteger second = count.remainder(
				new BigInteger(String.valueOf(subdivision)));

			if (second.compareTo(BigInteger.ZERO) < 0) {
				second = second.negate();
			}

			final StringBuffer buf = new StringBuffer();

			buf.append(second.toString());

			if (buf.length() < minorUnit) {
				for (int i = buf.length(); i < minorUnit; i++) {
					buf.insert(0, '0');
				}
			}

			return first.toString() + separator + buf.toString();
		}

		return count.toString();
	}

	public int compareTo(final Currency c) {
		return alphabeticCode.compareTo(c.alphabeticCode);
	}

	@Override
	public boolean equals(@SuppressWarnings("unused") final Object value) {
		return false;
	}
}
