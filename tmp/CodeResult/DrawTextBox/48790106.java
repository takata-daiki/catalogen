/**
 * Copyright 2013 briman0094 (Briman)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Amendment 1: This mod may not, under any circumstances, be included
 * in any Technic pack, whether it is an official pack or a pack distributed
 * through Technic Platform. There will be no exceptions.
 * 
 */

package com.briman0094.mineforever.client.gui;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.client.ForgeHooksClient;

import com.briman0094.mineforever.proxy.ClientProxy;
import com.briman0094.mineforever.tile.TileEntityKeypad;

public class GuiKeypadSetup extends GuiScreen
{
	private static final int	BUTTON_DOWN_ONE	= 0;
	private static final int	BUTTON_UP_ONE	= 1;
	private static final int	BUTTON_DOWN_TEN	= 2;
	private static final int	BUTTON_UP_TEN	= 3;
	private static final int	BUTTON_TOGGLE	= 4;

	private TileEntityKeypad	tileEntity;
	private int					left, top;
	private int					subWidth, subHeight;
	private GuiButton			downOne, upOne, downTen, upTen;
	private GuiButton			toggle;
	private GuiTextField		char1, char2, char3, char4;
	private GuiTextField		lTime;

	public GuiKeypadSetup(TileEntityKeypad tileEntity)
	{
		super();
		this.tileEntity = tileEntity;
		subWidth = 134;
		subHeight = 85;
		left = 0;
		top = 0;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		left = (width - subWidth) / 2;
		top = (height - subHeight) / 2;

		downOne = new GuiButton(BUTTON_DOWN_ONE, left + 29, top + 38, 20, 20, "-1");
		upOne = new GuiButton(BUTTON_UP_ONE, left + 85, top + 38, 20, 20, "+1");
		downTen = new GuiButton(BUTTON_DOWN_TEN, left + 7, top + 38, 20, 20, "+10");
		upTen = new GuiButton(BUTTON_UP_TEN, left + 107, top + 38, 20, 20, "+10");
		toggle = new GuiButton(BUTTON_TOGGLE, left + 29, top + 60, 76, 20, "Mode: Timeout");

		char1 = new GuiTextField(fontRenderer, left + 7, top + 16, 24, 20);
		char2 = new GuiTextField(fontRenderer, left + 39, top + 16, 24, 20);
		char3 = new GuiTextField(fontRenderer, left + 71, top + 16, 24, 20);
		char4 = new GuiTextField(fontRenderer, left + 103, top + 16, 24, 20);
		lTime = new GuiTextField(fontRenderer, left + 51, top + 38, 32, 20);

		buttonList.add(downOne);
		buttonList.add(upOne);
		buttonList.add(downTen);
		buttonList.add(upTen);
		buttonList.add(toggle);

		String pass = tileEntity.getPassword();
		if (pass.length() != 4)
		{
			pass = "0000";
		}

		char1.setText(Character.toString(pass.charAt(0)));
		char2.setText(Character.toString(pass.charAt(1)));
		char3.setText(Character.toString(pass.charAt(2)));
		char4.setText(Character.toString(pass.charAt(3)));
		lTime.setText(Integer.toString(tileEntity.getLockTime()));
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	public void updateScreen()
	{
		if (char1.getText().length() > 1)
			char1.setText(char1.getText().substring(1, 2));
		if (char2.getText().length() > 1)
			char2.setText(char2.getText().substring(1, 2));
		if (char3.getText().length() > 1)
			char3.setText(char3.getText().substring(1, 2));
		if (char4.getText().length() > 1)
			char4.setText(char4.getText().substring(1, 2));
		if (lTime.getText().length() > 3)
			lTime.setText(lTime.getText().substring(1, 4));

		if (char1.getText().length() <= 0)
			char1.setText("0");
		if (char2.getText().length() <= 0)
			char2.setText("0");
		if (char3.getText().length() <= 0)
			char3.setText("0");
		if (char4.getText().length() <= 0)
			char4.setText("0");
		if (lTime.getText().length() <= 0)
			lTime.setText("80");

		if (char1.getText().length() > 0 && !Character.isDigit(char1.getText().charAt(0)))
			char1.setText("0");
		if (char2.getText().length() > 0 && !Character.isDigit(char2.getText().charAt(0)))
			char2.setText("0");
		if (char3.getText().length() > 0 && !Character.isDigit(char3.getText().charAt(0)))
			char3.setText("0");
		if (char4.getText().length() > 0 && !Character.isDigit(char4.getText().charAt(0)))
			char4.setText("0");
		for (int c = 0; c < lTime.getText().length(); c++)
		{
			if (!Character.isDigit(lTime.getText().charAt(c)))
			{
				String beg = lTime.getText().substring(0, c);
				String end = lTime.getText().substring(c + 1, lTime.getText().length());
				lTime.setText(beg + end);
			}
		}

		String pass = tileEntity.getPassword();
		if (pass.length() != 4)
		{
			pass = "0000";
		}

		if (char1.getText().length() > 0 && char2.getText().length() > 0 && char3.getText().length() > 0 && char4.getText().length() > 0)
		{
			String temp = "";
			temp += char1.getText();
			temp += char2.getText();
			temp += char3.getText();
			temp += char4.getText();

			if (!temp.equals(pass))
			{
				tileEntity.setPassword(temp);
			}
		}

		int setLockTime = tileEntity.getLockTime();
		int iLockTime = Integer.parseInt(lTime.getText());
		if (setLockTime != iLockTime)
		{
			tileEntity.setLockTime(iLockTime);
		}
		
		boolean toggle = tileEntity.isToggle();
		this.toggle.displayString = "Mode: " + (toggle ? "Toggle" : "Timeout");
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button)
	{
		super.mouseClicked(mouseX, mouseY, button);
		char1.mouseClicked(mouseX, mouseY, button);
		char2.mouseClicked(mouseX, mouseY, button);
		char3.mouseClicked(mouseX, mouseY, button);
		char4.mouseClicked(mouseX, mouseY, button);
		lTime.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	protected void keyTyped(char chr, int key)
	{
		super.keyTyped(chr, key);
		if (key != Keyboard.KEY_BACK && key != Keyboard.KEY_DELETE && key != Keyboard.KEY_TAB && chr >= '0' && chr <= '9')
		{
			char1.textboxKeyTyped(chr, key);
			char2.textboxKeyTyped(chr, key);
			char3.textboxKeyTyped(chr, key);
			char4.textboxKeyTyped(chr, key);
			lTime.textboxKeyTyped(chr, key);
			keyTyped('\t', Keyboard.KEY_TAB);
		}
		if (key == Keyboard.KEY_TAB)
		{
			if (char1.isFocused())
			{
				char1.setFocused(false);
				char2.setFocused(true);
				char3.setFocused(false);
				char4.setFocused(false);
				lTime.setFocused(false);
			}
			else if (char2.isFocused())
			{
				char1.setFocused(false);
				char2.setFocused(false);
				char3.setFocused(true);
				char4.setFocused(false);
				lTime.setFocused(false);
			}
			else if (char3.isFocused())
			{
				char1.setFocused(false);
				char2.setFocused(false);
				char3.setFocused(false);
				char4.setFocused(true);
				lTime.setFocused(false);
			}
			else if (char4.isFocused())
			{
				char1.setFocused(false);
				char2.setFocused(false);
				char3.setFocused(false);
				char4.setFocused(false);
				lTime.setFocused(true);
			}
			else
			{
				char1.setFocused(true);
				char2.setFocused(false);
				char3.setFocused(false);
				char4.setFocused(false);
				lTime.setFocused(false);
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		switch (button.id)
		{
			case BUTTON_UP_ONE:
			{
				int iRange = Integer.parseInt(lTime.getText());
				iRange++;
				if (iRange > 999)
					iRange = 20;
				lTime.setText(Integer.toString(iRange));
				break;
			}
			case BUTTON_DOWN_ONE:
			{
				int iRange = Integer.parseInt(lTime.getText());
				iRange--;
				if (iRange < 20)
					iRange = 999;
				lTime.setText(Integer.toString(iRange));
				break;
			}
			case BUTTON_UP_TEN:
			{
				int iRange = Integer.parseInt(lTime.getText());
				iRange += 10;
				if (iRange > 999)
					iRange = 20;
				lTime.setText(Integer.toString(iRange));
				break;
			}
			case BUTTON_DOWN_TEN:
			{
				int iRange = Integer.parseInt(lTime.getText());
				iRange -= 10;
				if (iRange < 20)
					iRange = 990;
				lTime.setText(Integer.toString(iRange));
				break;
			}
			case BUTTON_TOGGLE:
			{
				tileEntity.setToggle(!tileEntity.isToggle());
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float scale)
	{
		//background
		ClientProxy.bindTexture("guis/gui_keypad.png");
		drawTexturedModalRect(left, top, 0, 0, subWidth, subHeight);

		super.drawScreen(mouseX, mouseY, scale);

		//foreground
		int strWidth = mc.fontRenderer.getStringWidth("Keypad");
		int drawX = (subWidth - strWidth) / 2 + left;
		mc.fontRenderer.drawString("Keypad", drawX, top + 4, 0x333333);

		char1.drawTextBox();
		char2.drawTextBox();
		char3.drawTextBox();
		char4.drawTextBox();
		lTime.drawTextBox();
	}

}
