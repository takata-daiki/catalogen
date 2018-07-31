/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ecf3.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * A <code>ValueAxis</code> is the vertical axis in a plot of a <A href=../model/Variable.html>Variable</A>.
 * It has a max value. A plot looks like this:<p>
 * <img src="ValueAxis.gif">
 *
 * @author Lars Olert
 */
public class ValueAxis {

	double valueMax;
	double scale =1.0;
	String szNameUnit = "";

	/**
	* Constructs a ValueAxis that extends form zero to valueMax.
	* @param valueMax Max value to be plotted in the diagram.
	*/
	public ValueAxis(double valueMax, String szNameUnit) {
		this.valueMax = valueMax;
		this.szNameUnit = szNameUnit;
	} // ValueAxis

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
	* @param height The height (pixels) of the value axis.
	* @param yOrigin The y-coordiante (pixels) in the graphical context
	* for the origin of the diagram.
	*/
	public void paint(Graphics g, int xOrigin, int height, int yOrigin) {
		double factorOfTen = 1.0;
		double[] vrfactorSmallSteps = {1.0, 2.0, 5.0, 10.0};

		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.black);
		g2.translate(xOrigin,yOrigin);

		// Axis line
		g2.drawLine(0, 0, 0, -height);

		// Find scale
		if (valueMax>factorOfTen) {
			while (valueMax>factorOfTen*10) factorOfTen *= 10.0;
		} else {
			while (valueMax<factorOfTen) factorOfTen /= 10.0;
		} // if

		int iSmallStep = 0;
		double scaleMax = factorOfTen*vrfactorSmallSteps[iSmallStep];
		while (valueMax>scaleMax) {
			iSmallStep++;
			scaleMax = factorOfTen*vrfactorSmallSteps[iSmallStep];
		} // while

		scale = height/scaleMax;

		// Ticks and time labels
		int yTick = -height;
		g2.drawLine(-10, yTick, 0, yTick);
		g2.drawString(String.valueOf(scaleMax) + " " + szNameUnit, -20, yTick-1);

		g2.setTransform(new AffineTransform());  // Reset to no translation
	} // paint

} // ValueAxis