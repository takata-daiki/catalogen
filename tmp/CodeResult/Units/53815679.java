package ecf3.model;

import ecf3.XMLParser;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.ArrayList;

/**
 * A Units object is a set of units that can be used for a
 * <a href="Quantity.html">quantity</a>.
 * The first is the base unit, the other are multiples of the base unit. <br>
 * <code> Unit unitBase = new Unit('CU',1.0,'Currency base unit'); </code><br>
 * <code> Units unitsCurrency = new Units('Currency',unitBase); </code><br>
 * <code> Unit unit1 = new Unit('kCU',0.001,'Thousand CU'); </code><br>
 * <code> unitsCurrency.add(unit1); </code><br>
 * @author Lars Olert
 * @version revised 1, 2008-12-10, Lars Olert
 *                  Index of ilayer replaced by the layer itself.
 *      2010-10-30 listXML and decodeXML updated
 */
public class Units {

    /** The ECF model*/
    Model model = Model.getInstance();
    /** The units of a quantity type.*/
    protected ArrayList<Unit> vrunit = new ArrayList<Unit>();
    /** The unit to be displayed in output.*/
    protected Unit unitDisplay;
    /** The short name of the Units */
    protected String szName;
    /** Long description of the Units. */
    protected String szDescription;
    /** The layer to which the Units is associated. */
    protected Layer layer = null;
    /** Max value in base units for pixels scale. */
    protected double valueMaxBase = 1.0;
    /** Index of display unit. */
    protected int iunitDisplay = 0;
    /** Scaling factor for balancing coefficients of equation system. */
    protected double scale = 1.0;

    public static ArrayList<Units> generateStandardUnits() {
        ArrayList<Units> vrunits = new ArrayList<Units>();
        Units units;

        // Make currency units
        units = new Units("Currency",
                new Unit("CU", 1.0, "Currency base unit"));
        units.add(new Unit("kCU", 0.001, "Thousand CU"));
        units.add(new Unit("MCU", 0.000001, "Million CU"));
        units.add(new Unit("GCU", 0.000000001, "Billion CU"));
        units.setDisplayUnit("kCU");
        // Pixel scale
        units.setScaleToPixelsMax(1000.0);
        vrunits.add(units);

        // Make swedish kronor currency units
        String szKr = "Kr";
        units = new Units("Swedish SKR",
                new Unit(szKr, 1.0, "Kronor base unit"));
        units.add(new Unit("k" + szKr, 0.001, "Thousand Kr"));
        units.add(new Unit("M" + szKr, 0.000001, "Million Kr"));
        units.add(new Unit("G" + szKr, 0.000000001, "Billion Kr"));
        units.setDisplayUnit("k" + szKr);
        // Pixel scale
        units.setScaleToPixelsMax(1000.0);
        vrunits.add(units);

        // Make euro currency units
//        String szEuro = "Eu"; // The euro font "\u20A0" is not found.
        String szEuro = "\u20AC";
        units = new Units("Euro",
                new Unit(szEuro, 1.0, "Euro base unit"));
        units.add(new Unit("k" + szEuro, 0.001, "Thousand Eu"));
        units.add(new Unit("M" + szEuro, 0.000001, "Million Eu"));
        units.add(new Unit("G" + szEuro, 0.000000001, "Billion Eu"));
        units.setDisplayUnit("k" + szEuro);
        // Pixel scale
        units.setScaleToPixelsMax(1000.0);
        vrunits.add(units);

        // Make Deutsche mark currency units
        String szDM = "DM";
        units = new Units("Deutsche mark",
                new Unit(szDM, 1.0, "Euro base unit"));
        units.add(new Unit("k" + szDM, 0.001, "Thousand DM"));
        units.add(new Unit("M" + szDM, 0.000001, "Million DM"));
        units.add(new Unit("G" + szDM, 0.000000001, "Billion DM"));
        units.setDisplayUnit("k" + szDM);
        // Pixel scale
        units.setScaleToPixelsMax(1000.0);
        vrunits.add(units);

        // Make dollar currency units
        String szDollar = "\u0024";
        units = new Units("Dollars",
                new Unit(szDollar, 1.0, "Dollars base unit"));
        units.add(new Unit("k" + szDollar, 0.001, "Thousand Dollars"));
        units.add(new Unit("M" + szDollar, 0.000001, "Million Dollars"));
        units.add(new Unit("G" + szDollar, 0.000000001, "Billion Dollars"));
        units.setDisplayUnit("k" + szDollar);
        // Pixel scale
        units.setScaleToPixelsMax(1000.0);
        vrunits.add(units);

        // Make population units
        units = new Units("Population",
                new Unit("persons", 1.0, "Persons"));
        units.add(new Unit("kpers", 0.001, "Thousand persons"));
        units.add(new Unit("Mpers", 0.000001, "Million persons"));
        units.setDisplayUnit("Mpers");
        // Pixel scale
        units.setScaleToPixelsMax(10.0);
        vrunits.add(units);

        // Make work units
        units = new Units("Work",
                new Unit("wmy", 1.0, "Worked man year"));
        units.add(new Unit("kwmy", 0.001, "Thousand wmy"));
        units.add(new Unit("Mwmy", 0.000001, "Million wmy"));
        units.setDisplayUnit("kwmy");
        // Pixel scale
        units.setScaleToPixelsMax(10.0);
        vrunits.add(units);

        // Make product units
        units = new Units("Products",
                new Unit("pmy", 1.0, "Product man year"));
        units.add(new Unit("kpmy", 0.001, "Thousand pmy"));
        units.add(new Unit("Mpmy", 0.000001, "Million pmy"));
        units.setDisplayUnit("kpmy");
        // Pixel scale
        units.setScaleToPixelsMax(10.0);
        vrunits.add(units);

        // Make energy units
        units = new Units("Energy",
                new Unit("kWh", 1.0, "Kilo Watt hours"));
        units.add(new Unit("MWh", 0.001, "Mega Watt hours"));
        units.add(new Unit("GWh", 0.000001, "Giga Watt hours"));
        units.add(new Unit("TWh", 0.000000001, "Tera Watt hours"));
        units.setDisplayUnit("kWh");
        // Pixel scale
        units.setScaleToPixelsMax(1000.0);
        vrunits.add(units);

        // Make mass units
        units = new Units("Mass",
                new Unit("kg", 1.0, "Kilogram"));
        units.add(new Unit("ton", 0.001, "Tons"));
        units.add(new Unit("Mton", 0.000001, "Mega tons"));
        units.add(new Unit("Gton", 0.000000001, "Giga tons"));
        units.setDisplayUnit("kg");
        // Pixel scale
        units.setScaleToPixelsMax(1000.0);
        vrunits.add(units);

        return vrunits;
    } // generateStandardUnits

    /** Creates a Units set for a quantity type.
     * @param szName The name of the units.
     * @param unitBase The base unit.*/
    public Units(String szName, Unit unitBase) {
        this.szName = szName;
        vrunit.add(unitBase);
        unitDisplay = unitBase;
        szDescription = szName;
    } // Units

    /** Creates a Units set for a quantity type.*/
    public Units() {
    } // Units

    /** Add a unit to the set of units.
     * @param unit The unit to be added. */
    public void add(Unit unit) {
        vrunit.add(unit);
    } // add

    public void clearLayerAssignment() {
        layer = null;
    } // 

    /** Make a copy of the units.
    @return A copy (clone). */
    @Override
    public Object clone() {
        Units clone = new Units();
        ArrayList<Unit> vrunitClone = new ArrayList<Unit>();
        Iterator<Unit> iter = vrunit.iterator();
        while (iter.hasNext()) {
            vrunitClone.add(iter.next());
        }
        clone.vrunit = vrunitClone;
        clone.unitDisplay = (Unit) unitDisplay.clone();
        clone.szName = szName;
        clone.szDescription = szDescription;
        clone.layer = layer;
        clone.valueMaxBase = valueMaxBase;
        clone.iunitDisplay = iunitDisplay;
        return clone;
    } // clone

    /**
     * Decode units from XML code.
     * @param szXMLCode The xml description of this units.
     */
    public void decodeXML(String szXMLCode) {
        XMLParser xml = null;
        // Decode XML code
        try {
            while (szXMLCode.length() > 0) {
                xml = new XMLParser(szXMLCode);
                String szTag = xml.tag();
                String szSub = xml.sub();
                if (szTag.equals("szName")) {
                    szName = szSub;
                }
                if (szTag.equals("szDescription")) {
                    szDescription = szSub;
                }
                if (szTag.equals("ilayer")) {
                    int ilayer = Integer.parseInt(szSub);
                    if (ilayer>=0)
                    layer = Model.getInstance().getLayer(ilayer);
                } // ilayer
                if (szTag.equals("vrunit")) {
                    decodeXMLUnits(szSub);
                } // vrunit
                if (szTag.equals("unitDisplay")) {
                    setDisplayUnit(szSub);
                }
                if (szTag.equals("valueMaxBase")) {
                    valueMaxBase = Double.parseDouble(szSub);
                }
                setScaleForCalc(valueMaxBase);
                szXMLCode = xml.remainder();
            } // while
        } catch (Exception e) {
        } // try/catch
    } // decodeXML

    /**
     * Decode model units from XML code.
     * @param szXMLCode The xml description of the units.
     */
    public void decodeXMLUnits(String szXMLCode) {
        XMLParser xml = null;
        vrunit.clear();
        // Decode XML code
        try {
            while (szXMLCode.length() > 0) {
                xml = new XMLParser(szXMLCode);
                String szTag = xml.tag();
                String szSub = xml.sub();
                if (szTag.equals("Unit")) {
                    Unit unit = new Unit();
                    unit.decodeXML(szSub);
                    vrunit.add(unit);
                }
                szXMLCode = xml.remainder();
            } // while
        } catch (Exception e) {
            System.out.println("Units.decodeXMLUnits: " + e +
                    "\n  " + szXMLCode);
        } // try/catch
    } // decodeXMLUnits

    /** Get the description of the Units.
     * @return The description. */
    public String getDescription() {
        return szDescription;
    }

    /** Get the display unit.
     * @return The display unit.*/
    public Unit getDisplayUnit() {
        return unitDisplay;
    }

    /** Get the conversion factor of the display unit.
     * @return Conversion factor from base to display units.*/
    public double getFactor() {
        try {
        return unitDisplay.getFactor();
        } catch(Exception e) {
            System.out.println("Units.getFactor: " + this +
                    "  unitDisplay=" + unitDisplay + "  " + e);
        }
        return 1;
    } // getFactor

    /** Get the index of the display unit.
     * @return The index.*/
    public int getIndexOfDisplayUnit() {
        return iunitDisplay;
    }

    /** Get the the associated layer.
     * @return The layer to which the units belong. */
    public Layer getLayer() {
        return layer;
    }

    /** Get the name of the display unit. */
    public String getNameOfDisplayUnit() {
        return unitDisplay.getName();
    } // getNameOfDisplayUnit

    /** Get the name of the units. */
    public String getName() {
        return szName;
    }

    /** Get the scale from base value to pixels.
     * @param cpixelMax Maximum pixels on scale.
     * @return The scale.*/
    public double getScaleToPixels(int cpixelMax) {
        return cpixelMax / valueMaxBase;
    }

    /** Get the scaling factor for balancing coefficients of equation system.
     * @return The calculation scale.*/
    public double getScaleForCalc() {
        return scale;
    }

    /** Get a string for maximum value in display units on pixel scale.
     * @return A string for max value.*/
    public String getStringMaxValue() {
        return getStringValue(valueMaxBase * unitDisplay.getFactor());
    } // getStringMaxValue

    /** Get the string with value and display unit. */
    public String getStringValue(double value) {
        DecimalFormat decfmt = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decfmt.applyPattern("#0");
        if (value <= 10.0) {
            decfmt.applyPattern("#0.00");
        }
        if (value <= 0) {
            decfmt.applyPattern("#0.0000");
        }
        if (value <= 0.01) {
            decfmt.applyPattern("#0.000000");
        }
        return decfmt.format(value);
    } // getStringValue

    /** Get a ArrayList of <a href="Unit.html">Unit</a> objects.
     * @return A ArrayList of Unit objects. */
    public ArrayList<Unit> getUnits() {
        return vrunit;
    }

    /** Get a unit iterator.
     * @return The iterator for unit. */
    public Iterator<Unit> iterator() {
        return vrunit.iterator();
    }

    /** List XML code for this units.
     * @return XML code. */
    public String listXML(String szBlanks) {
        model = Model.getInstance();
        String szXML = "";
        szXML += szBlanks + "<Units>\n";
        szXML += szBlanks + "  <szName>" + szName + "</szName>\n";
        szXML += szBlanks + "  <szDescription>" + szDescription + "</szDescription>\n";
        int ilayer = -1;
        if (layer != null) {
            ilayer = model.getLayerIndex(layer);
        }
        szXML += szBlanks + "  <ilayer>" + ilayer + "</ilayer>\n";
        // Units
        szXML += szBlanks + "  <vrunit>\n";
        Iterator iter = vrunit.iterator();
        while (iter.hasNext()) {
            szXML += szBlanks + "    <Unit>\n";
            Unit unit = (Unit) iter.next();
            szXML += unit.listXML("    " + szBlanks);
            szXML += szBlanks + "    </Unit>\n";
        } // while layers
        szXML += szBlanks + "  </vrunit>\n";
        szXML += szBlanks + "  <unitDisplay>" + unitDisplay.getName() + "</unitDisplay>\n";
        szXML += szBlanks + "  <valueMaxBase>" + valueMaxBase + "</valueMaxBase>\n";
        szXML += szBlanks + "</Units>\n";
        return szXML;
    } // listXML

    /** Set the description of the Units.
     * @param szDescription The description. */
    public void setDescription(String szDescription) {
        this.szDescription = szDescription;
    } // setDescription

    /** Set the display unit.
     * @param iunit The index of the display unit.*/
    public void setDisplayUnit(int iunit) {
        unitDisplay = (Unit) vrunit.get(iunit);
    } // setDisplayUnit

    /** Set the display unit.
     * @param szUnit The name of the display unit.*/
    public void setDisplayUnit(String szUnit) {
        Iterator iter = vrunit.iterator();
        int iunit = 0;
        while (iter.hasNext()) {
            Unit unit = (Unit) iter.next();
            if (unit.getName().compareTo(szUnit) == 0) {
                unitDisplay = unit;
                iunitDisplay = iunit;
                return;
            } // if
            iunit++;
        } // while
        unitDisplay = (Unit) vrunit.get(0);
    } // setDisplayUnit

    /** Set the layer associated to the Units.
     * @param layer The layer.*/
    public void setLayer(Layer layer) {
        this.layer = layer;
    } // setLayerIndex

    /** Set the name of the Units.
     * @param szName The name. */
    public void setName(String szName) {
        this.szName = szName;
    }

    /** Set the scale from display value to pixels.
     * @param valueMax Max display value. */
    public void setScaleToPixelsMax(double valueMax) {
        valueMaxBase = valueMax / unitDisplay.getFactor();
    } // setScaleToPixelsMax

    /** Set the scaling denominator for balancing coefficients of equation system.
     * @param scale Scale for display value. */
    public void setScaleForCalc(double scale) {
        this.scale = valueMaxBase; // scale;
    } // setScaleForCalc

    /** Get the string representation of the Units.
     * @return The string representation. */
    @Override
    public String toString() {
        String vrszNameUnit = " vrszNameUnit: ";
        Iterator iter = vrunit.iterator();
        while (iter.hasNext()) {
            Unit unit = (Unit) iter.next();
            System.out.println("Units.toString: unit=" + unit);
            vrszNameUnit += unit.getName() + " ";
        } // while
        return "Units: " + szName + " =" + vrszNameUnit;
    } // toString
} // Units