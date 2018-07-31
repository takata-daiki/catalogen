/**
 * Copyright (c) 2012, http://www.yissugames.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yissugames.gui;

import java.awt.Rectangle;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.jdom2.Element;

import com.yissugames.blocklife.BlockLife;
import com.yissugames.blocklife.RenderSystem;
import com.yissugames.blocklife.ContentLoader;

public class TextBox implements GUI, Inputable {

	private Texture texture = ContentLoader.getTexture("txtBox");
	private int posX, posY;
	private int maxLength = 16;
	private String content = "";
	private TrueTypeFont font = ContentLoader.getFont("Times New Roman", 0, 24);
	private boolean selected = false;
	
	private String allowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	
	private boolean cursor = false;
	private long lastChange;
	
	public TextBox(int posX, int posY)
	{
		this.posX = posX;
		this.posY = posY;
	}
	
	public void setMaxLength(int maxlength)
	{
		maxLength = maxlength;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public String getContent()
	{
		return this.content;
	}
	
	@Override
	public void render() {
		Rectangle bounds = new Rectangle(posX, posY, texture.getImageWidth(), texture.getImageHeight());
		if(bounds.contains(Mouse.getX(), BlockLife.DisplayHeight - Mouse.getY() - 1) && Mouse.isButtonDown(0))
		{
			selected = true;
			lastChange = System.currentTimeMillis();
			cursor = false;
		}
		else if(Mouse.isButtonDown(0))
			selected = false;
		
		RenderSystem.renderTexture(texture, posX, posY);
		
		int tPosY = texture.getImageHeight() / 2 - font.getHeight() / 2;
		
		String toDraw = content;
		if(selected && lastChange + 500 <= System.currentTimeMillis())
		{
			cursor = !cursor;
			lastChange = System.currentTimeMillis();
		}
		
		if(selected && cursor)
			toDraw += "|";
		font.drawString(posX + 2, posY + tPosY, toDraw, Color.black);
	}
	
	@Override
	public void sendInput(char c)
	{
		if(selected)
		{
			if(allowed.contains("" + c))
				if(!IsFullWith(c))
					content += c;
			
			if((int) c == 8 && content.length() > 0)
				content = content.substring(0, content.length() - 1);
		}
	}
	
	private boolean IsFullWith(char c)
	{
		if(content.length() == maxLength)
			return true;
		if(font.getWidth(content + c) >= texture.getImageWidth())
			return true;
		
		return false;
	}
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
	
	public static TextBox createByXML(Element e)
	{
		int posX = -1;
		if(!e.getAttributeValue("positionX").equals("center"))
			posX = Integer.parseInt(e.getAttributeValue("positionX"));
		int posY = -1;
		if(!e.getAttributeValue("positionY").equals("center"))
			posY = Integer.parseInt(e.getAttributeValue("positionY"));
		TextBox result = new TextBox(posX, posY);
		try {
			result.setMaxLength(Integer.parseInt(e.getAttributeValue("maxlength")));
		} catch(Exception ex) {
			
		}
		
		result.setContent(e.getValue());
		
		return result;
	}

}
