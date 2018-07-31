/*
 * Copyright 2012 Shotaro Uchida <fantom@xmaker.mx>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.squilla.ralink.chip;

import org.squilla.ralink.PhyConfiguration;
import org.squilla.ralink.RTException;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;
import org.squilla.io.ByteUtil;

/**
 *
 * @author Shotaro Uchida <fantom@xmaker.mx>
 */
public class Configurator {

    private Logger logger = LoggerFactory.getLogger(Configurator.class);

    private AsicInfo asicInfo;
    private EEPROM eeprom;

    public Configurator(AsicInfo asicInfo, EEPROM eeprom) {
        this.asicInfo = asicInfo;
        this.eeprom = eeprom;
    }

    public static PhyConfiguration readEEPROM(AsicInfo asicInfo, EEPROM eeprom) throws RTException {
        PhyConfiguration wlanConfig = new PhyConfiguration();
        Configurator configurator = new Configurator(asicInfo, eeprom);
        configurator.readROMVersion();
        configurator.readMACAddress(wlanConfig);
        configurator.readVendorBBPSettings(wlanConfig);
        configurator.readVendorRFSettings(wlanConfig);
        configurator.readLEDOperatingMode(wlanConfig);
        configurator.readAntennaInfo(wlanConfig);
        configurator.readNICConfiguration(wlanConfig);
        configurator.readPowerSettings(wlanConfig);
        configurator.readPowerCompensation(wlanConfig);
        configurator.readRSSIOffsetsAndLNAGains(wlanConfig);
        return wlanConfig;
    }

    public static int b4inc(int b32, int delta) {
        int b4;
	for (int i = 0; i < 8; i++) {
            b4 = b32 & 0xf;
            b4 += delta;
            if (b4 < 0) {
                b4 = 0;
            } else if (b4 > 0xf) {
                b4 = 0xf;
            }
            b32 = b32 >> 4 | b4 << 28;
	}
	return b32;
    }

    public void readROMVersion() throws RTException {
        /* read ROM version */
        int eepromVer = eeprom.read(EEPROM.VERSION);
        logger.trace("EEPROM rev=" + (eepromVer & 0xFF) + ", FAE=" + (eepromVer >> 8));
    }

    public void readMACAddress(PhyConfiguration wlanConfig) throws RTException {
        /* read MAC address */
        byte[] bssid = new byte[6];
	int mac01 = eeprom.read(EEPROM.MAC01);
	bssid[0] = (byte) (mac01 & 0xff);
	bssid[1] = (byte) (mac01 >> 8);
	int mac23 = eeprom.read(EEPROM.MAC23);
	bssid[2] = (byte) (mac23 & 0xff);
	bssid[3] = (byte) (mac23 >> 8);
	int mac45 = eeprom.read(EEPROM.MAC45);
	bssid[4] = (byte) (mac45 & 0xff);
	bssid[5] = (byte) (mac45 >> 8);
        wlanConfig.setBSSID(bssid);
        logger.trace("EEPROM MAC: "
                + ByteUtil.toHexString(bssid[0]) + "-"
                + ByteUtil.toHexString(bssid[1]) + "-"
                + ByteUtil.toHexString(bssid[2]) + "-"
                + ByteUtil.toHexString(bssid[3]) + "-"
                + ByteUtil.toHexString(bssid[4]) + "-"
                + ByteUtil.toHexString(bssid[5]));
    }

    public void readVendorBBPSettings(PhyConfiguration wlanConfig) throws RTException {
        /* read vender BBP settings */
        byte[][] bbp = new byte[10][2];
        for (int offset = 0; offset < 10; offset++) {
            int val = eeprom.read((short) (EEPROM.BBP_BASE + offset));
            bbp[offset][0] = (byte) (val & 0xFF);
            bbp[offset][1] = (byte) (val >> 8);
            logger.trace("EEPROM BBP[" + offset + "]: "
                    + ByteUtil.toHexString(bbp[offset][0]) + " = "
                    + ByteUtil.toHexString(bbp[offset][1]));
        }
        wlanConfig.setBBPRegister(bbp);
    }

    public void readVendorRFSettings(PhyConfiguration wlanConfig) throws RTException {
        if (asicInfo.getMacVersion() >= 0x3071) {
            /* read vendor RF settings */
            byte[][] rf = new byte[10][2];
            for (int offset = 0; offset < 10; offset++) {
                int val = eeprom.read((short) (EEPROM.RF_BASE + offset));
                rf[offset][0] = (byte) (val & 0xFF);
                rf[offset][1] = (byte) (val >> 8);
                logger.trace("EEPROM RF[" + offset + "]: "
                        + ByteUtil.toHexString(rf[offset][0]) + " = "
                        + ByteUtil.toHexString(rf[offset][1]));
            }
            wlanConfig.setRFRegister(rf);
        }
    }

    public void readLEDOperatingMode(PhyConfiguration wlanConfig) throws RTException {
        int freqLeds = eeprom.read(EEPROM.FREQ_LEDS);
        wlanConfig.setFrequencyOffset(freqLeds & 0xFF);
        logger.trace("EEPROM Freq Offset = " + wlanConfig.getFrequencyOffset());

        int ledMode = freqLeds >> 8;
        int[] led = new int[3];
        if (ledMode != 0xFF) {
            /* read LEDs operating mode */
            led[0] = eeprom.read(EEPROM.LED1);
            led[1] = eeprom.read(EEPROM.LED2);
            led[2] = eeprom.read(EEPROM.LED3);
        } else {
            /* broken EEPROM, use default settings */
            ledMode = 0;
            led[0] = 0x5555;
            led[1] = 0x2221;
            led[2] = 0x5627;	/* differs from RT2860 */
        }
        wlanConfig.setLedOperatingMode(ledMode);
        wlanConfig.setLed(led);
        logger.trace("EEPROM LED mode=0x" + Integer.toHexString(wlanConfig.getLedOperatingMode())
                + ", LEDs=0x" + Integer.toHexString(led[0]) + "/"
                + "0x" + Integer.toHexString(led[1]) + "/"
                + "0x" + Integer.toHexString(led[2]));
    }

    public void readAntennaInfo(PhyConfiguration wlanConfig) throws RTException {
        int antenna = eeprom.read(EEPROM.ANTENNA);
        AntennaInfo antennaInfo = new AntennaInfo();
        if (antenna == 0xFFFF) {
            logger.trace("Invalid EEPROM anttena info, using default");
            if (asicInfo.getMacVersion() == 0x3572) {
                /* default to RF3052 2T2R */
                antennaInfo.setRFICType(AntennaInfo.RT3070_RF_3052);
                antennaInfo.setTXPath(2);
                antennaInfo.setRXPath(2);
            } else if (asicInfo.getMacVersion() >= 0x3070) {
                /* default to RF3020 1T1R */
                antennaInfo.setRFICType(AntennaInfo.RT3070_RF_3020);
                antennaInfo.setTXPath(1);
                antennaInfo.setRXPath(1);
            } else {
                /* default to RF2820 1T2R */
                antennaInfo.setRFICType(AntennaInfo.RT2860_RF_2820);
                antennaInfo.setTXPath(1);
                antennaInfo.setRXPath(2);
            }
        } else {
            antennaInfo.setRFICType((antenna >> 8) & 0xF);
            antennaInfo.setTXPath((antenna >> 4) & 0xF);
            antennaInfo.setRXPath(antenna & 0xF);
        }
        wlanConfig.setAntennaInfo(antennaInfo);
        logger.trace("EEPROM RF rev=0x" + Integer.toHexString(antennaInfo.getRFICType())
                + " chains=" + antennaInfo.getTXPath() + "T" + antennaInfo.getRXPath() + "R");
    }

    public void readNICConfiguration(PhyConfiguration wlanConfig) throws RTException {
        /* check if RF supports automatic Tx access gain control */
        int config = eeprom.read(EEPROM.CONFIG);
        logger.trace("EEPROM CFG 0x" + Integer.toHexString(config));

        wlanConfig.setPatchDAC((config & 0x8000) > 0);
        wlanConfig.setExternalLNAForA((config & 0x8) > 0);
        wlanConfig.setExternalLNAForG((config & 0x4) > 0);
        wlanConfig.setDynamicTxAgcControl((config & 0x2) > 0);
        wlanConfig.setHardwareRadioControl((config & 0x1) > 0);
    }

    private void readPowerSettings(byte[] tx1, byte[] tx2, int off, int len, int base1, int base2) throws RTException {
        for (int i = 0; i < len; i += 2) {
            int val1 = eeprom.read((short) (base1 + i / 2));
            tx1[off + i + 0] = (byte) (val1 & 0xFF);
            tx1[off + i + 1] = (byte) (val1 >> 8);

            int val2 = eeprom.read((short) (base2 + i / 2));
            tx2[off + i + 0] = (byte) (val2 & 0xFF);
            tx2[off + i + 1] = (byte) (val2 >> 8);
        }

        /* fix broken Tx power entries */
        for (int i = 0; i < len; i++) {
            if (tx1[off + i] < 0 || tx1[off + i] > 31) {
                tx1[off + i] = 5;
            }
            if (tx2[off + i] < 0 || tx2[off + i] > 31) {
                tx2[off + i] = 5;
            }
        }
    }

    public void readPowerSettings(PhyConfiguration wlanConfig) throws RTException {
        final byte[] txPowerSetting1 = new byte[PhyConfiguration.TX_POWER_2GHZ_LEN + PhyConfiguration.TX_POWER_5GHZ_LEN];
        final byte[] txPowerSetting2 = new byte[PhyConfiguration.TX_POWER_2GHZ_LEN + PhyConfiguration.TX_POWER_5GHZ_LEN];

        /* read power settings for 2GHz channels */
        readPowerSettings(
                txPowerSetting1,
                txPowerSetting2,
                PhyConfiguration.TX_POWER_2GHZ_OFF,
                PhyConfiguration.TX_POWER_2GHZ_LEN,
                EEPROM.PWR2GHZ_BASE1,
                EEPROM.PWR2GHZ_BASE2);

        /* read power settings for 5GHz channels */
        readPowerSettings(
                txPowerSetting1,
                txPowerSetting2,
                PhyConfiguration.TX_POWER_5GHZ_OFF,
                PhyConfiguration.TX_POWER_5GHZ_LEN,
                EEPROM.PWR5GHZ_BASE1,
                EEPROM.PWR5GHZ_BASE2);


        wlanConfig.setTxPowerSetting1(txPowerSetting1);
        wlanConfig.setTxPowerSetting2(txPowerSetting2);
    }

    public void readPowerCompensation(PhyConfiguration wlanConfig) throws RTException {
        int delta2Ghz = 0;
        int delta5Ghz = 0;

        /* read Tx power compensation for each Tx rate */
        int delta = eeprom.read(EEPROM.DELTAPWR);
        if ((delta & 0x80) > 0) {
            delta2Ghz = delta & 0xF;
            /* negative number */
            if ((delta & 0x40) == 0) {
                delta2Ghz = -delta2Ghz;
            }
        }

        delta >>= 8;
        if ((delta & 0x80) > 0) {
            delta5Ghz = delta & 0xF;
            /* negative number */
            if ((delta & 0x40) == 0) {
                delta5Ghz = -delta5Ghz;
            }
        }

        logger.trace("EEPROM Power compensation=" + delta2Ghz + " (2GHz), "
                + delta5Ghz + " (5GHz)");

        int[] txPower20MHz = new int[5];
        int[][] txPower40MHz = new int[PhyConfiguration.FREQ_SIZE][5];
        for (int ridx = 0; ridx < 5; ridx++) {
            int reg = eeprom.read((short) (EEPROM.RPWR + ridx * 2));
            reg |= (eeprom.read((short) (EEPROM.RPWR + ridx * 2 + 1))) << 16;
            txPower20MHz[ridx] = reg;
            txPower40MHz[PhyConfiguration.FREQ_INDEX_2GHZ][ridx] = b4inc(reg, delta2Ghz);
            txPower40MHz[PhyConfiguration.FREQ_INDEX_5GHZ][ridx] = b4inc(reg, delta5Ghz);
            logger.trace("ridx[" + ridx + "]: power 20MHz=0x" + Integer.toHexString(txPower20MHz[ridx])
                    + ", 40MHz/2GHz=0x" + Integer.toHexString(txPower40MHz[PhyConfiguration.FREQ_INDEX_2GHZ][ridx])
                    + ", 40MHz/5GHz=0x" + Integer.toHexString(txPower40MHz[PhyConfiguration.FREQ_INDEX_5GHZ][ridx]));
        }

        wlanConfig.setTxPower20MHz(txPower20MHz);
        wlanConfig.setTxPower40MHz(txPower40MHz);
    }

    public void readRSSIOffsetsAndLNAGains(PhyConfiguration wlanConfig) throws RTException {
        byte[][] rssi = new byte[PhyConfiguration.FREQ_SIZE][];
        int[] lna = new int[4];
        byte[] txMixerGain = new byte[PhyConfiguration.FREQ_SIZE];

        byte[] rssi2Ghz = new byte[3];
        int val2G = eeprom.read(EEPROM.RSSI1_2GHZ);
        rssi2Ghz[0] = (byte) (val2G & 0xFF);
        rssi2Ghz[1] = (byte) (val2G >> 8);
        val2G = eeprom.read(EEPROM.RSSI2_2GHZ);
        if (asicInfo.getMacVersion() >= 0x3070) {
            /*
             * On RT3070 chips (limited to 2 Rx chains), this ROM
             * field contains the Tx mixer gain for the 2GHz band.
             */
            txMixerGain[PhyConfiguration.FREQ_INDEX_2GHZ] = (byte) (val2G & 0x7);
            logger.trace("TX Mixer gain = " + txMixerGain[PhyConfiguration.FREQ_INDEX_2GHZ] + "(2GHz)");
        } else {
            rssi2Ghz[2] = (byte) (val2G & 0xFF);
        }
        lna[2] = val2G >> 8;
        rssi[PhyConfiguration.FREQ_INDEX_2GHZ] = rssi2Ghz;

        byte[] rssi5Ghz = new byte[3];
        int val5G = eeprom.read(EEPROM.RSSI1_5GHZ);
        rssi5Ghz[0] = (byte) (val5G & 0xFF);
        rssi5Ghz[1] = (byte) (val5G >> 8);
        val5G = eeprom.read(EEPROM.RSSI2_5GHZ);
        if (asicInfo.getMacVersion() >= 0x3572) {
            /*
             * On RT3572 chips (limited to 2 Rx chains), this ROM
             * field contains the Tx mixer gain for the 5GHz band.
             */
            txMixerGain[PhyConfiguration.FREQ_INDEX_5GHZ] = (byte) (val5G & 0x7);
            logger.trace("TX Mixer gain = " + txMixerGain[PhyConfiguration.FREQ_INDEX_5GHZ] + "(5GHz)");
        } else {
            rssi5Ghz[2] = (byte) (val5G & 0xFF);
        }
        lna[3] = val5G >> 8;
        rssi[PhyConfiguration.FREQ_INDEX_5GHZ] = rssi5Ghz;

        int lnaVal = eeprom.read(EEPROM.LNA);
        lna[0] = lnaVal & 0xFF;
        lna[1] = lnaVal >> 8;

        /* fix broken 5GHz LNA entries */
        if (lna[2] == 0 || lna[2] == 0xFF) {
            logger.trace("invalid LNA for channel group 2");
            lna[2] = lna[1];
        }
        if (lna[3] == 0 || lna[3] == 0xFF) {
            logger.trace("invalid LNA for channel group 3");
            lna[3] = lna[1];
        }

        for (int ant = 0; ant < 3; ant++) {
            for (int index = 0; index < PhyConfiguration.FREQ_SIZE; index++) {
                if (rssi[index][ant] < -10 || rssi[index][ant] > 10) {
                    logger.trace("invalid RSSI" + (ant + 1) + " offser: " + rssi[index][ant]
                            + " (" + (index == PhyConfiguration.FREQ_INDEX_2GHZ ? "2GHz" : "5GHz") + ")");
                    rssi[index][ant] = 0;
                }
            }
        }

        wlanConfig.setRSSI(rssi);
        wlanConfig.setTxMixerGain(txMixerGain);
        wlanConfig.setLNA(lna);
    }
}
