package com.grgtvs.dailyxrates.db;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Rate implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 5150332942517472898L;
    public static String TABLE = XRatesDataHelper.TABLE_RATE;
    private DecimalFormat mFormatter;
    private Currency currency;
    private Double currentRate = 0.00;
    private Double previousRate = 0.00;
    private Double amount = 0.00;
    private Date lastupdate;
    private Date fetchDate;

    public Rate() {
        mFormatter = new DecimalFormat("造#,###.#####");
    }

    public Rate changeBase(Rate rate) {
        Double c = getCurrentRate() / rate.getCurrentRate();
        Double p = getPreviousRate() / rate.getPreviousRate();

        Rate newRate = new Rate();
        newRate.setCurrency(currency);
        newRate.setCurrentRate(c);
        newRate.setPreviousRate(p);
        newRate.setLastupdate(lastupdate);
        newRate.setFetchDate(fetchDate);
        newRate.setAmount(amount);

        return newRate;
    }

    /**
     * @return
     */
    public Double getAmount() {
        return currentRate * amount;
    }
    
    public String getAmountFormatted()
    {
        return mFormatter.format(this.getAmount());
    }

    /**
     * @return the currency instance
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @return the currentRate
     */
    public Double getCurrentRate() {
        return currentRate;
    }
    
    public String getCurrentRateFormatted()
    {
        return mFormatter.format(getCurrentRate());
    }

    /**
     * Return the percentge difference of the current rate with the previous one
     * 
     * @return
     */
    public double getDiffPercent() {
        double logDiff = Math.log(getCurrentRate() / getPreviousRate()) * 100;
        return logDiff; // Double.parseDouble(String.format("%.2f", logDiff));
    }

    /**
     * @return the fetchDate
     */
    public Date getFetchDate() {
        return fetchDate;
    }

    /**
     * @return the lastupdate
     */
    public Date getLastupdate() {
        return lastupdate;
    }

    /**
     * @return the previousRate
     */
    public Double getPreviousRate() {
        return previousRate;
    }

    /**
     * @param amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCurrency(Currency code) {
        currency = code;

        mFormatter.setCurrency(currency.getCurrency());
        mFormatter.setMaximumFractionDigits(currency.getFractionDigits());
        mFormatter.setMinimumFractionDigits(currency.getFractionDigits());

        if (currency.getCurrency().getDefaultFractionDigits() > 0) {
            mFormatter.setDecimalSeparatorAlwaysShown(true);
        }  else {
            mFormatter.setDecimalSeparatorAlwaysShown(false);
            mFormatter.setMultiplier(100);
        }
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCurrency(String code) {
        this.setCurrency(new Currency(code));
    }

    /**
     * @param currentRate
     *            the currentRate to set
     */
    public void setCurrentRate(Double currentRate) {
        if (amount == 0.00) {
            amount = currentRate;
        }

        this.currentRate = currentRate;
    }

    /**
     * @param fetchDate
     *            the fetchDate to set
     */
    public void setFetchDate(Date fetchDate) {
        this.fetchDate = fetchDate;
    }

    /**
     * @param lastupdate
     *            the lastupdate to set
     */
    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    /**
     * @param previousRate
     *            the previousRate to set
     */
    public void setPreviousRate(Double previousRate) {
        this.previousRate = previousRate;
    }

    @Override
    public String toString() {
        return getCurrentRateFormatted();
    }
}
