/*
 * This file is part of FFractal, created by Guilhelm Savin and modified
 * by Bilyan Borisov.
 * 
 * FFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FFractal.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2010
 * 	Guilhelm Savin
 * 
 * Copyright 2012
 *  Bilyan Borisov
 */
package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.ri2c.flame.gui.*;

public class FFWizardArrayTest {
	
	FFWizardArray ffwa1 = new FFWizardArray();
	FFWizard ffw1 		= new FFWizard();
	
	@Test
	public void testFFWizardArray()
	{
		String[] names =  {"ArrayFunction#2","ArrayFunction#3","ArrayFunction#4"};
		FFWizard.incCount();
		ffwa1.setNames(3);
		assertTrue("problem with constructor1",ffwa1.getTitle().equals("New Flame Function Array"));
		assertTrue("problem with constructor2",ffwa1.isVisible()==false);
		assertTrue("problem with constructor3",ffwa1.isResizable()==false);
		for(int i=0;i<3;i++)
			assertTrue("problem with setNames, length = " + names[i],ffwa1.getFFNames()[i].equals(names[i]));
	
	}
}
