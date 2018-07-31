/* Function.java
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

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

public class Function extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The font used to display the function
	 */
	private Font funcFont;
	
	/**
	 * The value of the function (or name the function that is currently set to)
	 */
	private String value;
	
	/**
	 * Constructor - simply sets function to be visible
	 */
	public Function() {
		setVisible(true);
	}
	
	/**
	 * Adjusts the value to the new given parameter
	 * 
	 * @param val
	 *            the new function value to be set to
	 */
	public void changeValue(String val) {
		value = val;
		repaint();
	}
	
	/**
	 * Performs the repainting
	 * 
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	/**
	 * paints
	 */
	protected void paintComponent(Graphics g) {
		
		// set up graphics
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g; // initialize box and name graphic
		
		funcFont = new Font("Times New Roman", Font.BOLD,
				DisplayProperties.FUNCTION_FONT_SIZE);// set the default font
														// size
		g2d.setFont(funcFont);
		if (value != null) {
			g2d.drawString(value, DisplayProperties.FUNCTION_WIDTH_PADDING,
					DisplayProperties.FUNCTION_NAME_HEIGHT);
		}
	}
	
}
