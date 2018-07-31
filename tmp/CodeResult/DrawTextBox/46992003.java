package net.minecraft.src;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class CJB_GuiWaypoint extends GuiScreen
{
	final private GuiScreen parentScreen;
	final private CJB_Data wp;
	final private List<CJB_Data> wps;
	final private int mw = 176;
	final private int mh = 166;
	final private String title;
	private int mx = 0;
	private int my = 0;
	private int col;
	
	private CJB_TextField name;
	private CJB_NumField x;
	private CJB_NumField y;
	private CJB_NumField z;
	private GuiButton ok;
	private CJB_ColorChart chart;
	
	
    public CJB_GuiWaypoint(GuiScreen guiscreen, CJB_Data data)
    {
    	this.parentScreen = guiscreen;
    	this.wp = data;
    	this.wps = CJB.mmwaypoints;
    	this.title = "Edit Waypoint";
    }
    
    public CJB_GuiWaypoint(GuiScreen guiscreen, EntityPlayer plr)
    {
    	this.parentScreen = guiscreen;
    	this.wps = CJB.mmwaypoints;
    	this.title = "Add Waypoint";
    	
    	this.wp = new CJB_Data();
    	this.wp.Name = "New Waypoint";
    	this.wp.posx = MathHelper.floor_double(plr.posX);
    	this.wp.posy = MathHelper.floor_double(plr.posY);
    	this.wp.posz = MathHelper.floor_double(plr.posZ);
    	this.wp.data = plr.dimension;
    	this.wp.color = 0xff000000 + plr.rand.nextInt(0xffffff);
    	
    	addWaypoint(wp);
    }

    @Override
    public void initGui()
    {
    	mx = (width - mw) / 2;
    	my = (height - mh) / 2;
    	
    	Keyboard.enableRepeatEvents(true);
    	
    	col = wp.color;
    	
    	name = new CJB_TextField(fontRenderer, mx+50, my+20, 116, 12);
    	name.setText(wp.Name);
    	name.setMaxStringLength(20);
    	
    	x = new CJB_NumField(fontRenderer, mx+50, my+40, 116, 12);
    	x.setMaxStringLength(6);
    	x.setNumber((int) wp.posx);
    	y = new CJB_NumField(fontRenderer, mx+50, my+54, 116, 12);
    	y.setMaxStringLength(6);
    	y.setNumber((int) wp.posy);
    	z = new CJB_NumField(fontRenderer, mx+50, my+68, 116, 12);
    	z.setMaxStringLength(6);
    	z.setNumber((int) wp.posz);
    	
    	ok = new GuiButton(0, mx + 10, my + 140, 156, 20, "OK");
    	
    	chart = new CJB_ColorChart(mc, mx+123, my+87);
    	
    }
    
    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void mouseClicked(int i, int j, int k) {
    	name.mouseClicked(i, j, k);
    	x.mouseClicked(i, j, k);
    	y.mouseClicked(i, j, k);
    	z.mouseClicked(i, j, k);
    	if (chart.isMouseOver)
    		wp.color = chart.color;
    	
    	if (k == 0 && ok.mousePressed(mc, i, j)) {
    		CJB_Settings.saveData(CJB.mmwaypoints, "waypoints", true);
    		mc.displayGuiScreen(parentScreen);
    	}
    }
        
    public void actionPerformed(CJB_Button cjbbutton)
    {
    }
    
    public void actionPerformedMainMenus(CJB_Button cjbbutton)
    {
    }
    
    public void actionPerformedMenus(CJB_Button cjbbutton)
    {   	
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
    	if (CJB.darkenbg)
    		drawDefaultBackground();
    	
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/cjb/menuwaypoint.png"));
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	drawTexturedModalRect(mx,my,0,0, mw, mh);
    	
    	this.drawCenteredString(fontRenderer, this.title, mx + mw/2, my+7, 0xffffff);
    	
    	this.drawString(fontRenderer, "Name:", mx + 10, my+22, 0xffffff);
    	
    	this.drawString(fontRenderer, "X:", mx + 10, my+42, 0xffffff);
    	this.drawString(fontRenderer, "Y:", mx + 10, my+56, 0xffffff);
    	this.drawString(fontRenderer, "Z:", mx + 10, my+70, 0xffffff);
    	this.drawString(fontRenderer, "Color:", mx + 10, my+104, 0xffffff);
    	
    	if (chart.isMouseOver)
    		col = chart.color;
    	
    	
    	int k = mx + 49;
    	int l = my + 87;
    	this.drawRect(k, l, k+60, l+20, 0xff444444);
    	this.drawRect(k+1, l+1, k+60-1, l+20-1, 0xff000000 + wp.color);
    	
    	k = mx + 49;
    	l = my + 110;
    	this.drawRect(k, l, k+60, l+20, 0xff444444);
    	this.drawRect(k+1, l+1, k+60-1, l+20-1, 0xff000000 + col);
    	
    	name.drawTextBox();
    	x.drawTextBox();
    	y.drawTextBox();
    	z.drawTextBox();
    	ok.drawButton(mc, i, j);
    	chart.drawScreen(i, j);
    }
    
    @Override
    public void handleMouseInput()
    {
    	super.handleMouseInput();
    	if (x.getIsFocused()) {
    		x.handleMouseInput();
    		wp.posx = x.getNumber();
    	}
    	if (y.getIsFocused()) {
    		y.handleMouseInput();
    		wp.posy = y.getNumber();
    	}
    	if (z.getIsFocused()) {
    		z.handleMouseInput();
    		wp.posz = z.getNumber();
    	}
    }
    
    @Override
    public void updateScreen()
    {
    	name.updateCursorCounter();
    	x.updateCursorCounter();
    	y.updateCursorCounter();
    	z.updateCursorCounter();
    }

    @Override
    public void keyTyped(char c, int i)
    {
    	if(i == 1)
        {
    		CJB_Settings.loadData(CJB.mmwaypoints, "waypoints", true);
            mc.displayGuiScreen(parentScreen);
            return;
        }
    	if (name.getIsFocused()) {
    		name.textboxKeyTyped(c, i);
    		wp.Name = name.getText();
    		return;
    	}
    	
    	if (x.getIsFocused()) {
    		x.textboxKeyTyped(c, i);
    		wp.posx = x.getNumber();
    		return;
    	}
    	if (y.getIsFocused()) {
    		y.textboxKeyTyped(c, i);
    		wp.posy = y.getNumber();
    		return;
    	}
    	if (z.getIsFocused()) {
    		z.textboxKeyTyped(c, i);
    		wp.posz = z.getNumber();
    		return;
    	}
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }
    
    private void addWaypoint(CJB_Data waypoint) {
    	
    	for (int i = 0 ; i < wps.size() ; i++) {
    		
    		CJB_Data way = wps.get(i);
    		
    		if (way.equals(waypoint))
    			wps.remove(i);
    	}
    	wps.add(waypoint);
    }
}
