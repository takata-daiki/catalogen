package joe.game.drone;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class TextBox {
	
	private static boolean isTyping;
	private static char[] currentString;
	private static int cursor;
	private static ArrayList<Character> charactersOnScreen;
	private static int typeCounter;
	private static int typeTime;
	private static Font font;
	
	public static void init(String fontType){
		font = new Font(fontType, 15, 15);
	}
	
	public static void DisplayString(String string, int timer){
		typeTime = timer;
		typeTime = timer;
		charactersOnScreen = new ArrayList<Character>();
		cursor =  0;
		currentString = string.toCharArray();
		isTyping = true;
	}
	
	/**
	 * returns true if time to type
	 * @return
	 */
	public static boolean countTimer(){
		boolean type = false;
		if(typeCounter <= 0){
			typeCounter = typeTime;
			type = true;
		}else{
			typeCounter --;
		}
		return type;
	}
	
	public static void update(Graphics g){
		if(isTyping && countTimer()){
			charactersOnScreen.add(currentString[cursor]);
			if(cursor != currentString.length - 1){
				cursor ++;
			}
			if(currentString.length == charactersOnScreen.size()){
				isTyping = false;
			}
		}
		render(g);
	}
	
	public static void render(Graphics g){
		g.setFont(font);
		if(charactersOnScreen != null){
			g.drawImage(ImageBank.getImage("textBox"), 10, 10, 400, 50, 0, 0, 500, 200, null);
			g.setColor(new Color(255, 0, 0));
			char[] string = new char[charactersOnScreen.size()];
			for(int i=0; i<charactersOnScreen.size(); i++){
				string[i] = charactersOnScreen.get(i).charValue();
			}
			g.drawString(new String(string), 20, 30);
		}
	}
}
