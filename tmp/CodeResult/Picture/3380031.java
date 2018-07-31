// Picture.java
// Jim Sproch
// Created: April 29, 2006
// Modified: April 29, 2006
// Part of the Aforce Port
// Mac < Windows < Linux

/**
	Picture contains images
	@author Jim Sproch
	@version 0.1a beta
*/


import java.awt.*;
import java.util.*;  //for arraylist

public class Picture
{
	HashMap<Integer, Image> images = new HashMap<Integer, Image>();

	public void putImage(int direction, Image image)
	{
		images.put(new Integer(direction), image);
	}

	public Image getImage(int direction)
	{
		return images.get(new Integer(direction));
	}

	public String toString()
	{
		return images.toString();
	}

	public static void main(String[] args)
	{
		Printer.noexecute();
	}
}