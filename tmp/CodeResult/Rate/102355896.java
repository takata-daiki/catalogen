package com.smscoin.android.payment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A class encapsulating data about the rate.
 * 
 */
public class Rate {

    public String country; // country code
    public String country_name; // country name
    public String code; // provider code
    public String name; // provider name
    public String mcc; // international country code
    public String mnc; // international provider code
    public String number; // short number
    public String prefix; // message prefix
    public String currency;
    public String special; // special description of provider

    public double price; // price in local currency
    public double usd; // price in usd currency
    public double profit; // profit in percent

    public boolean vat; // true if include vat otherwise false

    /**
     * Construct the rate object from the rates json
     * 
     * @param rate_json
     *            input data in json format
     */
    public Rate(JSONObject rate_json) {
        try {
            number = rate_json.getString("number");
            prefix = rate_json.getString("prefix");
            currency = rate_json.getString("currency");
            special = rate_json.getString("special");

            price = rate_json.getDouble("price");
            usd = rate_json.getDouble("usd");
            profit = rate_json.getDouble("profit");

            vat = rate_json.getInt("vat") == 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
