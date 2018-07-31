package org.fenrir.jcollector.dto;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * TODO v1.0 Javadoc
 * @author Antonio Archilla Nava
 * @version v0.1.20110719
 */
public interface ICell
{
	public BufferedImage getImage();
	public void setImage(BufferedImage image);
	
	public Point getPosition();
	public void setPostion(Point position);
	
	public String getText();
	
	public Object getElement();
	public void setElement(Object element);
	
	public boolean isContainerCell();
}
