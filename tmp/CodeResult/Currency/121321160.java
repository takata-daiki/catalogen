package model.money;

import model.transaction.abs.TransferableObject;

/**
 * The representation of a Euro currency
 * @author Simon Seyer
 */
public class Currency  implements TransferableObject<Currency>,Comparable<Currency> {

	private int currency;
    /**
     * Currency in euro
     */
    public static final int EURO = 1;
    /**
     * Currency in cent
     */
    public static final int CENT = 0;

    /**
     * Constructor of Currency
     * @param price
     * @param type
     */
    public Currency(double price, int type) {
        if (type == EURO) {
            this.currency = (int) (price * 100);
        } else {
            this.currency = (int) price;
        }
    }

    /**
     * Get the value in cent
     * @return the value
     */
    public int getCent() {
        return currency;
    }

    /**
     * Get the value in euros
     * @return the value
     */
    public double getEuro() {
        return (double) currency / 100;
    }

    /**
     * Get a new Currency object, where the value of this class is mutliplied with the given number
     * @param multiplier
     * @return the new currency
     */
    public Currency getMultipliedCopy(int multiplier) {
        return new Currency(currency * multiplier, CENT);
    }

    /**
     * Get the price formatted in euro, if it is larger then 100 Cent and in cent, if it is lower.
     * @return the formatted price
     */
    public String getFormattedPrice() {
        if (currency < 100 && currency > -100) {
            return getFormattedPrice(CENT);
        } else {
            return getFormattedPrice(EURO);
        }
    }

    /**
     * Get a formatted Price in cent or in euro
     * @param type Currency.CENT, Currency.EURO
     * @return the formatted price
     */
    public String getFormattedPrice(int type) {
        if (type == CENT) {
            return getCent() + " Cent";
        } else {
            String val = "" + getEuro();
            if (val.contains(".")) {
                String[] tmp = val.split("\\.");
                if (tmp[1].length() == 1) {
                    val += "0";
                }
            }
            return val + " âŹ";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Currency) {
            Currency currency = (Currency) obj;
            return currency.getCent() == getCent();
        }
        return false;
    }

    @Override
    public String toString() {
        return getFormattedPrice();
    }

    @Override
    public int compareTo(Currency o) {
        return getCent() - o.getCent();
    }
}
