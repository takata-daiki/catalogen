package org.minicraft.manager.client.apigui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatAllowedCharacters;

import org.minicraft.manager.client.MIHandler;

public class MITextField extends GuiTextField implements MIWidget {
	
	private static int currentId = 100;
	private FontRenderer fontRenderer;
	private int x, y, width, height, color;
	
	private boolean multiLine = false;
	private boolean disableDraw = false;
	
	private int posCursorline;
	private int cursorline;
	
	private List<String> lines = new ArrayList<String>();
	private List<Integer> lineslenght = new ArrayList<Integer>();
	
	public MITextField(String text,int x, int y, int width, int height) {
		super(currentId++, MIHandler.mc.fontRendererObj, x, y, width, height);
		setText(text);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.fontRenderer = MIHandler.mc.fontRendererObj;
		color = 0xe0e0e0;
	}
	@Override
	public void drawWidget(int x, int y, int mouseX, int mouseY){
		setXPos(this.x + x);
		setYPos(this.y + y);
		if(multiLine){ 
			if (!disableDraw){
	            drawRect(this.x + x - 3, this.y + y - 3, this.x + x + width + 3, this.y + y + height + 3, 0xffa0a0a0);
	            drawRect(this.x + x - 2, this.y + y - 2, this.x + x + width + 2, this.y + y + height + 2, 0xff000000);
	        }
			
			renderSplitString(getText(), this.x + x +3, this.y + y, width, color, false);
			
			if(isFocused() && isEnabled()){
				try{	
				int yyy = this.y + y -1 + (cursorline * fontRenderer.FONT_HEIGHT);
				String sub = "";
				if(cursorline > lines.size()-1)
					sub = "";
				else if(posCursorline >= 0 && lines.get(cursorline).length() > posCursorline)
					sub = (lines.isEmpty())? "": lines.get(cursorline).substring(0, posCursorline);
				int xxx = fontRenderer.drawStringWithShadow(sub, this.x + x +3, yyy+1, 0xe0e0e0) -1; // drawStringWithShadow
				
				Gui.drawRect(xxx, yyy, xxx + 1, yyy + fontRenderer.FONT_HEIGHT, 0xffd0d0d0);
				
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			drawTextBox();
		}
	}
	
	public void drawTextBox(){ super.drawTextBox(); }
	
	public void setXPos(int xpos){ xPosition = xpos; }
	
	public void setYPos(int ypos){ yPosition = ypos; }
	
	private boolean newLinebtn = false;
	private boolean keypush = false;
	
	public boolean textboxKeyTyped(char par1, int par2){ return super.textboxKeyTyped(par1, par2); }
	
	public void keyTyped(char par1, int par2){
		textboxKeyTyped(par1, par2);
		try{
		if(isFocused() && multiLine && par2 == 28){ // Touche entrer
			newLinebtn = true;
			
		}
		if(isFocused() && multiLine && par2 == 205){ // Touche droite
			if(posCursorline < lines.get(cursorline).length()-1)
				posCursorline++;
			else{
				if(cursorline < lines.size()-1){
					posCursorline = 0;
					cursorline++; 
				}
			}
		}
		if(isFocused() && multiLine && par2 == 208){ // Touche bas
			if(cursorline < lines.size()-1){
				cursorline++;
				
				if(lines.get(cursorline).length()-1 < posCursorline)
					posCursorline = lines.get(cursorline).length()-1;	
				setCursorPosition(posCursorline);
				for(int i = 0 ; i < cursorline ; i++)
					setCursorPosition(getCursorPosition() + lineslenght.get(i));
			}
		}		
		if(isFocused() && multiLine && par2 == 203){ // Touche gauche
			if(posCursorline > 0)
				posCursorline--;
			else{
				if(cursorline > 0){
					cursorline--;
					posCursorline = lines.get(cursorline).length()-1;
				}
			}
		}
		if(isFocused() && multiLine && par2 == 200){ // Touche haut
			if(cursorline > 0)
				cursorline--;
			if(lines.get(cursorline).length()-1 < posCursorline)
				posCursorline = lines.get(cursorline).length()-1;
			setCursorPosition(posCursorline);
			for(int i = 0 ; i < cursorline ; i++)
				setCursorPosition(getCursorPosition() + lineslenght.get(i));
			
		}
		if(isFocused() && multiLine && par2 == 14){ // Touche return
			keypush = true;
		}
		if(isFocused() && multiLine && par2 == 211){ // Touche suppr
			keypush = true;
		}
		
		if (isFocused() && multiLine && ChatAllowedCharacters.isAllowedCharacter(par1)){ // Touche clavier
			keypush = true;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getText() { return super.getText(); }

	public void setText(String text) { super.setText(text); }
	
	public void setMaxStringLength(int max) { super.setMaxStringLength(max); }
	
	public boolean isFocused() { return super.isFocused(); }
	
	public void setFocused(boolean focus) { super.setFocused(focus); }
	
	public boolean isEnabled() { return this.isEnabled; }

	public void setEnabledW(boolean enable) { this.isEnabled = enable; }

	public boolean isVisible() { return super.getVisible(); }

	public void setVisibleW(boolean visible) { super.setVisible(visible); }

	public boolean isMultiLine() { return multiLine; }

	public void setMultiLine(boolean multiLine) { this.multiLine = multiLine; }
	
	public void setDisableDraw(boolean b){
		disableDraw = b;
		setEnableBackgroundDrawing(b);
	}
	public void setEnableBackgroundDrawing(boolean b){ super.setEnableBackgroundDrawing(b); }
	@Override
	public int getId() { return 0; }
	@Override
	public int getX() { return x; }
	@Override
	public int getY() { return y; }
	@Override
	public void setX(int x) { this.x = x; }
	@Override
	public void setY(int y) { this.y = y; }
	@Override
	public void setColor(int color){ this.color = color; }
	@Override
	public int getColor(){ return color; }
	
	public int getCursorPosition(){ return super.getCursorPosition(); }
	
	public void mouseClicked(int par1, int par2, int par3){ super.mouseClicked(par1, par2, par3); }
	
	public void setCursorPosition(int pos){ super.setCursorPosition(pos); }
	
	public void setCursorPositionZero(){ super.setCursorPositionZero(); }
	@Override
	public void mouseClicked(int x, int y, int mouseX, int mouseY, int clic) {
		try{
		mouseClicked(mouseX, mouseY, clic);
		if(isFocused() && isMultiLine() && isEnabled() && !lines.isEmpty()){
			boolean inLine = false;
			cursorline = -1;
			setCursorPosition(0);
			int i = 0; 
			for(String line : lines){
				cursorline++;
				if(mouseY < this.y + y + (cursorline*fontRenderer.FONT_HEIGHT) + fontRenderer.FONT_HEIGHT){
					inLine = true;
					break;
				}
				setCursorPosition(getCursorPosition() + lineslenght.get(i));
				i++;
			}
			if(!inLine){
				setCursorPosition(getCursorPosition() - lines.get(lines.size()-1).length()+1);
			}

			posCursorline = fontRenderer.trimStringToWidth(lines.get(cursorline), mouseX - this.x + 5 -x).length()-1;
		
			if(posCursorline < 0) posCursorline = 0;
			if(posCursorline > lines.get(cursorline).length()-1) 
				posCursorline = lines.get(cursorline).length()-1;
		
			setCursorPosition(getCursorPosition() + posCursorline);
			
		}
		}catch (Exception e) {
			System.out.println("error textfield mouse clicked.");
			//e.printStackTrace();
		}
	}
	
	private void renderSplitString(String par1Str, int par2, int par3, int width, int par5, boolean par6)
    {
		int preNbline = lines.size();

		if(newLinebtn){
			int currentpos = getCursorPosition();
			par1Str = new StringBuilder(par1Str).insert(getCursorPosition(), "\n").toString();
			setText(par1Str);
			
			setCursorPosition(++currentpos);
			
			preNbline--;
		}
		par1Str += "\nend";
		
		lines.clear();
		lineslenght.clear();
        String as[] = par1Str.split("\n");

        for (int i = 0; i < as.length-1; i++)
        {     
	        String as1[] = (as[i]+" endline").split(" ");
	        int j = 0;
	        String s = "";
	        par3 -= fontRenderer.FONT_HEIGHT;
	
	        do
	        {
	            if (j >= as1.length-1)
	            {
	                break;
	            }
	
	            String s1;
	            par3 += fontRenderer.FONT_HEIGHT;
	
	            for (s1 = (new StringBuilder()).append(s).append(as1[j++]).append(" ").toString(); 
	            	j < as1.length-1 && fontRenderer.getStringWidth((new StringBuilder()).append(s1).append(as1[j]).toString()) < width;
	            	s1 = (new StringBuilder()).append(s1).append(as1[j++]).append(" ").toString()) { }
	
	            if (fontRenderer.getStringWidth(s1.trim()) > 0)
	            {
	                if (s1.lastIndexOf("\247") >= 0)
	                {
	                    s = (new StringBuilder()).append("\247").append(s1.charAt(s1.lastIndexOf("\247") + 1)).toString();
	                }
	
	                fontRenderer.drawString(s1, par2, par3, par5);

	            }
	            lines.add(s1);
                if(i < as.length)
                	lineslenght.add(s1.length());
                else
                	lineslenght.add(s1.length()+1);
	        }
	        while (true);
	        par3 += fontRenderer.FONT_HEIGHT;
        }
        
        if(lines.size() > preNbline){
        	int lenght = 0;
        	for(int i = 0 ; i <= cursorline  && i < lines.size(); i++){
        		lenght += lines.get(i).length();
        	}
        	posCursorline = getCursorPosition() - lenght;
        	cursorline++;
        }
        if(newLinebtn){
        	lines.add("");
        	posCursorline = 0;
        	newLinebtn = false;
        }
        if(keypush){
        	int lenght = 0;
        	cursorline = 0;
        	for(String line : lines){
        		lenght += line.length();
        		if(lenght > getCursorPosition())
        			break;
        		cursorline++;
        	}
        	posCursorline = lines.get(cursorline).length() - (lenght - getCursorPosition()); 
        	keypush = false;
        }
    }
	@Override
	public boolean actionPerformed(MIButton button) { return false; }
	@Override
	public void setWidthW(int width) {
		this.width = width;
		super.width = width;
	}
	@Override
	public int getWidthW() { return super.getWidth(); }
	@Override
	public int getHeight() { return height; }
	@Override
	public void setHeight(int height) {
		this.height = height;
		super.height = height;
	}
}