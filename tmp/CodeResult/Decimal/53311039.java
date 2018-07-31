package nu.mazzer.calculator;

/**
 * Decimal representation.
 *
 * @author Mikko Puustinen (mikko.puustinen@gmail.com)
 * @version 0.2, 2009-sep-18
 */
public class Decimal implements NumberPart {
	/**
	 * String representing the decimal sign.
	 */
	public static final String DECIMAL = ".";

	/**
	 * Regex string to be able to find the decimal.
	 */
	public static final String DECIMAL_REGEX = "\\.";

	/**
	 * Returns false because this is not a Digit.
	 *
	 * @return false.
	 */
	@Override
	public boolean isDigit() {
		return false;
	}

	@Override
	public int getDigit() throws UnsupportedOperationException {
		//XXX: Strange to throw something when this is not exceptional...
		throw new UnsupportedOperationException("Decimal is not a digit");
	}

	@Override
	public NumberPart getDeepCopy() {
		return new Decimal();
	}

	@Override
	public String toString() {
		return DECIMAL;
	}
}
