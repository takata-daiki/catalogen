/* Array.java
 * Copyright (C) 2012 Alex "HolyCause" Mair (holy.cause@gmail.com)
 * Copyright (C) 2012 Ivan Vendrov (ivendrov@gmail.com)
 * Copyright (C) 2012 Joey Eremondi (jse313@mail.usask.ca)
 * Copyright (C) 2012 Joanne Traves (jet971@mail.usask.ca)
 * Copyright (C) 2012 Logan Cool (coollogan88@gmail.com)
 * 
 * This file is a part of Giraffe.
 * 
 * Giraffe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Giraffe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Giraffe.  If not, see <http://www.gnu.org/licenses/>.
 */

package ca.usask.cs.giraffe.ui.anim;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import ca.usask.cs.giraffe.compiler.anim.AnimationDescriptor.AnimationType;

public class Array extends DisplayObject implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Stores the array of primitives
	 */
	private Primitive[] elements; // Stores
									// the
									// array
									// elements
	
	/**
	 * Image paths for images
	 */
	private String topPath = "images/atop.png";
	private String middlePath = "images/amiddle.png";
	private String bottomPath = "images/abottom.png";
	
	/**
	 * Images for each piece of the array image - for piecing together
	 */
	private Image topImage;
	private Image middleImage;
	private Image bottomImage;
	
	private final static int TOP_BOTTOM_IMAGE_HEIGHT = 18; // represents
															// the
															// value
															// of
															// top
															// and
															// bottom
															// images
															// height
	
	/**
	 * time for current animation
	 */
	private double time;
	Timer aTimer;
	
	/**
	 * Constructor
	 * 
	 * @param namey
	 *            the name of the array box
	 * @param arrayLength
	 *            the length of it
	 * @param width
	 *            the width of the array
	 * @param height
	 *            the height of the array
	 */
	public Array(String namey, int arrayLength, Primitive[] elems, int width,
			int height) {
		name = namey;
		elements = elems;
		
		// set display properties
		setSize(new Dimension(width, height));
		// load images
		try {
			Image image = ImageIO.read(new File(topPath));// try to read file
			topImage = image.getScaledInstance(getWidth(),
					TOP_BOTTOM_IMAGE_HEIGHT, java.awt.Image.SCALE_SMOOTH);
			image = ImageIO.read(new File(bottomPath));// try to read file
			bottomImage = image.getScaledInstance(getWidth(),
					TOP_BOTTOM_IMAGE_HEIGHT, java.awt.Image.SCALE_SMOOTH);
			image = ImageIO.read(new File(middlePath));// try to read file
			middleImage = image.getScaledInstance(getWidth(), getHeight() -
					TOP_BOTTOM_IMAGE_HEIGHT * 2, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.computeNameFontAndOffset(DisplayProperties.NAME_WIDTH);
	}
	
	/**
	 * @return the length of the array
	 */
	public int getLength() {
		if (elements == null)
			return 0;
		else
			return elements.length;
	}
	
	/**
	 * Gets the element of the array
	 * 
	 * @param index
	 *            of element wanted
	 * @return element wanted at index
	 */
	public Primitive getElemAtIndex(int index) {
		return elements[index];
	}
	
	/**
	 * Appear the outer shell array box and name
	 * 
	 * @param animTime
	 * @param elems
	 *            are the elements inside the array
	 */
	public void appear(double animTime) {
		time = animTime;
		type = AnimationType.APPEAR;
		initAnimation(animTime / 4);
		setVisible(true);
	}
	
	/**
	 * calls primitive appear animation for every element in the array
	 */
	public void appearArrayContent() {
		// appear all elements
		for (int i = 0; i < elements.length; i++) {
			elements[i].setVisible(true);
			elements[i].appear(3 * time / 4);
		}
	}
	
	/**
	 * paints
	 */
	protected void paintComponent(Graphics g) {
		
		// set up graphics
		super.paintComponent(g);
		Graphics2D arrg = (Graphics2D)g; // initialize boxandname graphic
		arrg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON); // set render methods
		
		double opacity = 1;
		Paint nColor = computeGrayscale(NAME_END_GRAYSCALE);
		switch (type) {
		
		case APPEAR:
			opacity = Math.min((percent / FADE_IN_PERCENT), 1);
			nColor = computeGrayscale(namePercentVal(percentAfterFade()));
		default:
		}
		
		arrg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				(float)opacity));
		arrg.drawImage(topImage, 0, 0, null);
		arrg.drawImage(middleImage, 0, TOP_BOTTOM_IMAGE_HEIGHT, null);
		arrg.drawImage(bottomImage, 0, getHeight() - TOP_BOTTOM_IMAGE_HEIGHT,
				null);
		
		arrg.setPaint(nColor);
		arrg.setFont(nameFont); // find size
		arrg.drawString(name, nameOff.x, nameOff.y);
	}
	
	/**
	 * Calls to primitive appear for a quick stop;
	 */
	@Override
	public void stopAnimation() {
		super.stopAnimation();
		appearArrayContent();
	}
}
