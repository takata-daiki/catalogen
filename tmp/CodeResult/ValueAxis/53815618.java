package ecf3.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * A <code>ValueAxis</code> is the vertical axis in a plot of a <A href=../model/Variable.html>Variable</A>.
 * It has a max and a min value. A plot looks like this:<p>
 * <img src="ValueAxis.gif">
 *
 * @author Lars Olert
 */
public class ValueAxis {

	double valueMin;
	double valueMax;
	double scale =1.0;
	String szNameUnit = "";

	/**
	* Constructs a ValueAxis that extends form valueMin to valueMax.
	* @param valueMin Max value to be plotted in the diagram.
	* @param valueMax Max value to be plotted in the diagram.
	*/
	public ValueAxis(double valueMin, double valueMax, String szNameUnit) {
		this.valueMin = valueMin;
		this.valueMax = valueMax;
		this.szNameUnit = szNameUnit;
	} // ValueAxis

    /** Find the scale for an axis starting at valueMin and ending at
     * valueMax. The height shall be <em>height</em> pixels.
     * @param height The height of the value axis (pixels).
     * @return valueScaleMax The value at the top of the value axis.
     */
    public double findScale(double height) {

		double factorOfTen = 1.0;
		double[] vrfactorSmallSteps = {1.0, 2.0, 5.0, 10.0};

		// Find scale
		if (valueMax>factorOfTen) {
			while (valueMax>factorOfTen*10) factorOfTen *= 10.0;
		} else {
			while (valueMax<factorOfTen) factorOfTen /= 10.0;
		} // if

		int iSmallStep = 0;
		double valueScaleMax = factorOfTen*vrfactorSmallSteps[iSmallStep];
		while (valueMax>valueScaleMax && iSmallStep<3) {
			iSmallStep++;
			valueScaleMax = factorOfTen*vrfactorSmallSteps[iSmallStep];
		} // while

        double valueDiff = valueScaleMax-valueMin;
		scale = height/valueDiff;
        return valueScaleMax;
    } // findScale

    /**
	* Gets the scale factor that converts from value to pixels in vertical direction.
	* @return The scale factor from value to pixels.
	*/
	public double getScale() { return scale; }

	/**
	* Paints the value axis.
	* @param g The graphical context.
	* @param xOrigin The x-coordiante (pixels) in the graphical context
	* for the origin of the diagram.
	* @param yOrigin The y-coordiante (pixels) in the graphical context
	* for the origin of the diagram.
	* @param height The height (pixels) of the value axis.
	*/
	public void paint(Graphics g, int xOrigin, int yOrigin, int height) {
        String szvalueScaleMax = String.valueOf(findScale(height));
        // Plot
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.black);
		g2.translate(xOrigin,yOrigin);
		// Axis line (vertical)
		g2.drawLine(0, 0, 0, -height);
		// Ticks and time labels
		int yTick = -height;
		g2.drawLine(-10, yTick, 0, yTick);
		g2.drawString(szvalueScaleMax + " " + szNameUnit, -20, yTick-1);
		g2.setTransform(new AffineTransform());  // Reset to no translation
	} // paint

    /** Test findScale(double height */
    public static void main(String[] args) {
        System.out.println("ValueAxis.main: ");
        ValueAxis valueaxis = new ValueAxis(-200, 1200, "Skr");
        double valueScaleMax = valueaxis.findScale(200);
        double scale = valueaxis.scale;
        System.out.println("ValueAxis.main: valueMin=." + valueaxis.valueMin +
                " valueMax=." + valueaxis.valueMax);
        System.out.println("ValueAxis.main: valueScaleMax=" + valueScaleMax +
                " scale=" + scale);
    } // main of Valueaxis

} // ValueAxis