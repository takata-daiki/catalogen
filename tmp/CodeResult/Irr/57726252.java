/*  
 *  Client-Bank: System For Electronic Funds Transfer.
 *  (C) 2011 Serious Corporation <https://bitbucket.org/crome/client-bank>.
 *  This program is free software: you can redistribute and/or modify
 *  it under the terms of the GNU General Public License version 3.
 */

package com.sc.clientbank.client.model.directory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class Currencies {

    public static final Currency AED = new Currency("AED", "784");
    public static final Currency AFA = new Currency("AFA", "004");
    public static final Currency ALL = new Currency("ALL", "008");
    public static final Currency AMD = new Currency("AMD", "051");
    public static final Currency ARS = new Currency("ARS", "032");
    public static final Currency AUD = new Currency("AUD", "036");
    public static final Currency AZM = new Currency("AZM", "031");
    public static final Currency BGN = new Currency("BGN", "975");
    public static final Currency BRL = new Currency("BRL", "986");
    public static final Currency BYR = new Currency("BYR", "974");
    public static final Currency CAD = new Currency("CAD", "124");
    public static final Currency CHF = new Currency("CHF", "756");
    public static final Currency CLP = new Currency("CLP", "152");
    public static final Currency CNY = new Currency("CNY", "156");
    public static final Currency COP = new Currency("COP", "170");
    public static final Currency CUP = new Currency("CUP", "192");
    public static final Currency CYP = new Currency("CYP", "196");
    public static final Currency CZK = new Currency("CZK", "203");
    public static final Currency DKK = new Currency("DKK", "208");
    public static final Currency DZD = new Currency("DZD", "012");
    public static final Currency EEK = new Currency("EEK", "233");
    public static final Currency EGP = new Currency("EGP", "818");
    public static final Currency ETB = new Currency("ETB", "230");
    public static final Currency EUR = new Currency("EUR", "978");
    public static final Currency GBP = new Currency("GBP", "826");
    public static final Currency GEL = new Currency("GEL", "981");
    public static final Currency HUF = new Currency("HUF", "348");
    public static final Currency IDR = new Currency("IDR", "360");
    public static final Currency ILS = new Currency("ILS", "376");
    public static final Currency INR = new Currency("INR", "356");
    public static final Currency IQD = new Currency("IQD", "368");
    public static final Currency IRR = new Currency("IRR", "364");
    public static final Currency ISK = new Currency("ISK", "352");
    public static final Currency JOD = new Currency("JOD", "400");
    public static final Currency JPY = new Currency("JPY", "392");
    public static final Currency KES = new Currency("KES", "404");
    public static final Currency KGS = new Currency("KGS", "417");
    public static final Currency KRW = new Currency("KRW", "410");
    public static final Currency KWD = new Currency("KWD", "414");
    public static final Currency KZT = new Currency("KZT", "398");
    public static final Currency LAK = new Currency("LAK", "418");
    public static final Currency LBP = new Currency("LBP", "422");
    public static final Currency LKR = new Currency("LKR", "144");
    public static final Currency LTL = new Currency("LTL", "440");
    public static final Currency LVL = new Currency("LVL", "428");
    public static final Currency LYD = new Currency("LYD", "434");
    public static final Currency MAD = new Currency("MAD", "504");
    public static final Currency MDL = new Currency("MDL", "498");
    public static final Currency MNT = new Currency("MNT", "496");
    public static final Currency MXN = new Currency("MXN", "484");
    public static final Currency NGN = new Currency("NGN", "566");
    public static final Currency NOK = new Currency("NOK", "578");
    public static final Currency NPR = new Currency("NPR", "524");
    public static final Currency NZD = new Currency("NZD", "554");
    public static final Currency PEN = new Currency("PEN", "604");
    public static final Currency PHP = new Currency("PHP", "608");
    public static final Currency PKR = new Currency("PKR", "586");
    public static final Currency PLN = new Currency("PLN", "985");
    public static final Currency ROL = new Currency("ROL", "642");
    public static final Currency RUB = new Currency("RUB", "643");
    public static final Currency SAR = new Currency("SAR", "682");
    public static final Currency SDD = new Currency("SDD", "736");
    public static final Currency SEK = new Currency("SEK", "752");
    public static final Currency SGD = new Currency("SGD", "702");
    public static final Currency SIT = new Currency("SIT", "705");
    public static final Currency SKK = new Currency("SKK", "703");
    public static final Currency SYP = new Currency("SYP", "760");
    public static final Currency THB = new Currency("THB", "764");
    public static final Currency TJS = new Currency("TJS", "972");
    public static final Currency TMM = new Currency("TMM", "795");
    public static final Currency TND = new Currency("TND", "788");
    public static final Currency TRL = new Currency("TRL", "792");
    public static final Currency TWD = new Currency("TWD", "901");
    public static final Currency UAH = new Currency("UAH", "980");
    public static final Currency USD = new Currency("USD", "840");
    public static final Currency UYU = new Currency("UYU", "858");
    public static final Currency UZS = new Currency("UZS", "860");
    public static final Currency VND = new Currency("VND", "704");
    public static final Currency XDR = new Currency("XDR", "960");
    public static final Currency YUM = new Currency("YUM", "891");
    public static final Currency ZAR = new Currency("ZAR", "710");

    public static final List<Currency> LIST = Arrays.asList(
        AED, AFA, ALL, AMD, ARS, AUD, AZM, BGN, BRL, BYR, CAD, CHF,
        CLP, CNY, COP, CUP, CYP, CZK, DKK, DZD, EEK, EGP, ETB, EUR,
        GBP, GEL, HUF, IDR, ILS, INR, IQD, IRR, ISK, JOD, JPY, KES,
        KGS, KRW, KWD, KZT, LAK, LBP, LKR, LTL, LVL, LYD, MAD, MDL,
        MNT, MXN, NGN, NOK, NPR, NZD, PEN, PHP, PKR, PLN, ROL, RUB,
        SAR, SDD, SEK, SGD, SIT, SKK, SYP, THB, TJS, TMM, TND, TRL,
        TWD, UAH, USD, UYU, UZS, VND, XDR, YUM, ZAR
    );

    @SuppressWarnings("serial")
    public static final Map<String, Currency> MAP = new HashMap<String, Currency>() {
        {
            for (Currency c : LIST) {
                put(c.getAcronym(), c);
            }
        }
    };
}
