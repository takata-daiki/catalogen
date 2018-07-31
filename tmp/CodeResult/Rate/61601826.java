/*
 * Copyright (c) 2009 - 2012, Shotaro Uchida
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.squilla.net.wlan;

import java.util.BitSet;

/**
 * IEEE802.15.4 Transfer Rate
 *
 * @author Shotaro Uchida <fantom@xmaker.mx>
 */
public class Rate {

    public static final int RATE_1M = 2;
    public static final int RATE_2M = 4;
    public static final int RATE_5M5 = 11;
    public static final int RATE_6M = 12;
    public static final int RATE_9M = 18;
    public static final int RATE_11M = 22;
    public static final int RATE_12M = 24;
    public static final int RATE_18M = 36;
    public static final int RATE_22M = 44;
    public static final int RATE_24M = 48;
    public static final int RATE_33M = 66;
    public static final int RATE_36M = 72;
    public static final int RATE_48M = 96;
    public static final int RATE_54M = 108;
    public static final int[] DEFAULT_OPERATIONAL_RATES = {
            RATE_1M,
            RATE_2M,
            RATE_5M5,
            RATE_11M,
            RATE_6M,
            RATE_9M,
            RATE_12M,
            RATE_18M,
            RATE_24M,
            RATE_36M,
            RATE_48M,
            RATE_54M
    };

    private BitSet flags;
    private int bitRate;

    public static Rate toRate(int rate) {
        Rate r = new Rate();
        r.setBitRate(rate);
        // TODO Flag
        return r;
    }

    public static Rate[] toRate(int[] rate) {
        Rate[] rates = new Rate[rate.length];
        for (int i = 0; i < rate.length; i++) {
            rates[i] = toRate(rate[i]);
        }
        return rates;
    }

    /**
     * @return the flags
     */
    public BitSet getFlags() {
        return flags;
    }

    /**
     * @param flags the flags to set
     */
    public void setFlags(BitSet flags) {
        this.flags = flags;
    }

    /**
     * @return the bitRate
     */
    public int getBitRate() {
        return bitRate;
    }

    /**
     * @param bitRate the bitRate to set
     */
    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public static class Flags {
        
	public static final int RATE_SHORT_PREAMBLE	= 0;
	public static final int RATE_MANDATORY_A	= 1;
	public static final int RATE_MANDATORY_B	= 2;
	public static final int RATE_MANDATORY_G	= 3;
	public static final int RATE_ERP_G		= 4;
    }
}
