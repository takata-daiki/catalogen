/*
 * Lucane - a collaborative platform
 * Copyright (C) 2004  Vincent Fiack <vfiack@mail15.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.lucane.applications.slideshow.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class ImageRenderer implements ListCellRenderer 
{
	private HashMap scaledImages = new HashMap();
	
	public Component getListCellRendererComponent(JList list, Object value,
		int index, boolean isSelected, boolean cellHasFocus) 
	{
				
		JPanel container = (JPanel)scaledImages.get(value);
		if(container == null)
		{
			Image img = (Image)value;			
			ImageComponent component = new ImageComponent();
			Image scaled = img.getScaledInstance(128, -1, Image.SCALE_FAST);
			component.setImage(scaled, false);	
			container = new JPanel(new BorderLayout());
			container.add(component, BorderLayout.CENTER);
			scaledImages.put(value, container);
		}		
		
		Color color = Color.LIGHT_GRAY;
		if (isSelected) 
			color = Color.ORANGE;

		container.setBorder(BorderFactory.createLineBorder(color, 2));
		return container;
	}
}
