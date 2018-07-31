package com.pointcliki.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.pointcliki.core.PointClikiGame;
import com.pointcliki.event.Dispatcher;
import com.pointcliki.event.FrameEvent;
import com.pointcliki.event.IEvent;
import com.pointcliki.event.Minion;
import com.pointcliki.input.KeyEvent;
import com.pointcliki.input.MouseEvent;

public class TextBox extends UIEntity {

	/**
	 * Serial key
	 */
	private static final long serialVersionUID = 1573570817459027921L;
	
	protected String fText;
	protected String fDefault;
	protected boolean fDefaultText = true;
	protected Color fColor;
	protected int fCaretOffset;
	protected int fCaretChar;
	protected int fCaretChar2;
	protected int fSelectOffset = 0;
	protected int fSelectWidth = 0;
	protected int fTextStart = 0;
	protected String fDisplayingText;
	protected int fDisplayChar = 0;
	protected UnicodeFont fFont;

	protected Minion<KeyEvent> fKeyMinion;
	protected Minion<IEvent> fChangedMinion;
	protected Minion<FrameEvent> fRepeatKeyMinion;
	protected long fNextRepeatKeyFrame = -1;
	protected KeyEvent fLastEvent;
	
	public TextBox(String text) {
		this(text, text);
	}
	
	public TextBox(String text, String def) {
		fSpan = new Rectangle(0, 0, 200, 28);
		fText = text;
		fDisplayingText = text;
		fDefault = def;
		fDefaultText = text.equals(def);
		fColor = new Color(80, 80, 80);
		fRepeatKeyMinion = new Minion<FrameEvent>() {
			@Override
			public long run(Dispatcher<FrameEvent> dispatcher, String type, FrameEvent event) {
				typeChar(fLastEvent.c(), fLastEvent.key());
				fNextRepeatKeyFrame = event.frame() + 2;
				return 2;
			}
		};
		fKeyMinion = new Minion<KeyEvent>() {
			@Override
			public long run(Dispatcher<KeyEvent> dispatcher, String type, KeyEvent event) {
				fLastEvent = event;
				if (type.equals("key.down")) {
					typeChar(event.c(), event.key());
					if (fNextRepeatKeyFrame >= 0) frameManager().dequeue(fRepeatKeyMinion, fNextRepeatKeyFrame);
					fNextRepeatKeyFrame = timeManager().currentFrame() + 20;
					frameManager().queue(fRepeatKeyMinion, 20);
				} else {
					frameManager().dequeue(fRepeatKeyMinion, fNextRepeatKeyFrame);
					fNextRepeatKeyFrame = -1;
				}
				return Minion.CONTINUE;
			}
		};
		fChangedMinion = new Minion<IEvent>();
		fFont = PointClikiGame.resourceManager().UIFont();
	}
	
	public void setChangedMinion(Minion<IEvent> minion) {
		fChangedMinion = minion;
	}
	
	protected void typeChar(char c, int key) {
		int min = Math.min(fCaretChar, fCaretChar2);
		int max = Math.max(fCaretChar2, fCaretChar);
		if (key == 47 && PointClikiGame.inputManager().isCtrlPressed()) {
			String copy = getClipboard();
			if (copy == null) return;
			String end;
			if (max >= fText.length()) end = "";
			else end = fText.substring(max);
			fText = fText.substring(0, Math.min(min, fText.length())) + copy + end;
			updateCaret(min + copy.length(), min + copy.length());
		} else if (isPrintableChar(c)) {
			String end;
			if (max >= fText.length()) end = "";
			else end = fText.substring(max);
			fText = fText.substring(0, Math.min(min, fText.length())) + c + end;
			updateCaret(min + 1, min + 1);
		
		// Backspace
		} else if (key == 14 && fCaretChar > 0) {
			String end;
			if (max >= fText.length()) end = "";
			else end = fText.substring(max);
			if (fCaretChar != fCaretChar2) {
				fText = fText.substring(0, Math.min(min, fText.length())) + end;
				updateCaret(min, min);
			} else {
				fText = fText.substring(0, Math.min(min - 1, fText.length())) + end;
				updateCaret(min - 1, min - 1);
			}
			
		} else if (key == Keyboard.KEY_LEFT) {
			if (PointClikiGame.inputManager().isShiftPressed()) updateCaret(fCaretChar, fCaretChar2 - 1);
			else if (fCaretChar != fCaretChar2) updateCaret(min, min);
			else updateCaret(fCaretChar2 - 1, fCaretChar2 - 1);
			
		} else if (key == Keyboard.KEY_RIGHT) {
			if (PointClikiGame.inputManager().isShiftPressed()) updateCaret(fCaretChar, fCaretChar2 + 1);
			else if (fCaretChar != fCaretChar2) updateCaret(max, max);
			else updateCaret(fCaretChar2 + 1, fCaretChar2 + 1);
			
		} else if (key == Keyboard.KEY_HOME) {
			if (PointClikiGame.inputManager().isShiftPressed()) updateCaret(fCaretChar, 0);
			else updateCaret(0, 0);
			
		} else if (key == Keyboard.KEY_END) {
			if (PointClikiGame.inputManager().isShiftPressed()) updateCaret(fCaretChar, Integer.MAX_VALUE);
			else updateCaret(Integer.MAX_VALUE, Integer.MAX_VALUE);
			
		} else if (key == Keyboard.KEY_DELETE) {
			String end;
			if (fCaretChar != fCaretChar2) {
				if (max >= fText.length()) end = "";
				else end = fText.substring(max);
				fText = fText.substring(0, Math.min(min, fText.length())) + end;
			} else {
				if (max + 1>= fText.length()) end = "";
				else end = fText.substring(max + 1);
				fText = fText.substring(0, Math.min(min, fText.length())) + end;
			}
			updateCaret(min, min);
		}
	}
	
	@Override
	public void handleUIMouseEvent(String type, Vector2f local, MouseEvent event) {
		fDispatcher.dispatchEvent(type, event);
		
		if (type.equals("mouse.down")) {
			int i = charIndex((int) (local.x - 5));
			updateCaret(i, i);
		} else if (type.equals("mouse.drag")) {
			int i = charIndex((int) (local.x - 5));
			updateCaret(fCaretChar, i);
		}
	}
	
	@Override
	public void focus() {
		super.focus();
		if (fDefaultText) {
			fDefaultText = false;
			fText = "";
		}
		PointClikiGame.inputManager().keyDispatcher().addMinion("key.down", fKeyMinion);
		PointClikiGame.inputManager().keyDispatcher().addMinion("key.up", fKeyMinion);
	}
	
	@Override
	public void blur() {
		super.blur();
		if (fText.equals("")) {
			fDefaultText = true;
			fText = fDefault;
		}
		fChangedMinion.run(null, fText.equals(fDefault) ? "" : fText, null);
		PointClikiGame.inputManager().keyDispatcher().removeMinion("key.down", fKeyMinion);
		PointClikiGame.inputManager().keyDispatcher().removeMinion("key.up", fKeyMinion);
	}
	
	public int charIndex(int x) {
		if (fText.length() == 0) return 0;
		int i = 1;
		int w = PointClikiGame.resourceManager().UIFont().getWidth(fText.substring(0, i));
		while (x > w) {
			i++;
			if (i > fText.length()) {
				i++;
				break;
			}
			w = PointClikiGame.resourceManager().UIFont().getWidth(fText.substring(0, i));
		}
		return Math.min(i - 1, fText.length());
	}
	
	public void updateCaret(int i, int i2) {
		fCaretChar = Math.max(0, Math.min(i, fText.length()));
		fCaretChar2 = Math.max(0, Math.min(i2, fText.length()));
		fCaretOffset = fFont.getWidth(fText.substring(0, fCaretChar2));
		if (fCaretChar != fCaretChar2) {
			int min = Math.min(fCaretChar, fCaretChar2);
			int max = Math.max(fCaretChar2, fCaretChar);
			fSelectOffset = PointClikiGame.resourceManager().UIFont().getWidth(fText.substring(0, min));
			fSelectWidth = PointClikiGame.resourceManager().UIFont().getWidth(fText.substring(min, max));
		} else {
			fSelectWidth = 0;
		}
		if (fCaretOffset > fSpan.getWidth() - 10) {
			int j = fCaretChar2;
			int n = 1;
			while (j - n > 1 && fFont.getWidth(fText.substring(j - n, j)) < fSpan.getWidth() - 10) n++;
			fDisplayingText = fText.substring(j - n + 1, j);
			fDisplayChar = j - n + 1;
			if (fCaretChar != fCaretChar2) {
				int min = Math.max(Math.min(Math.min(fCaretChar, fCaretChar2) - fDisplayChar, fText.length()), 0);
				int max = Math.max(Math.min(Math.max(fCaretChar2, fCaretChar) - fDisplayChar, fText.length()), 0);
				fSelectOffset = PointClikiGame.resourceManager().UIFont().getWidth(fText.substring(0, min));
				fSelectWidth = PointClikiGame.resourceManager().UIFont().getWidth(fText.substring(min, max));
			}
		} else {
			fDisplayingText = fText;
			fDisplayChar = 0;
		}
		while (fFont.getWidth(fDisplayingText) > fSpan.getWidth() - 10) fDisplayingText = fDisplayingText.substring(0, fDisplayingText.length() - 1);
	}
	
	public boolean isPrintableChar( char c ) {
	    Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
	    return (!Character.isISOControl(c)) &&
	            c != java.awt.event.KeyEvent.CHAR_UNDEFINED &&
	            block != null &&
	            block != Character.UnicodeBlock.SPECIALS;
	}
	
	// If a string is on the system clipboard, this method returns it;
	// otherwise it returns null.
	public static String getClipboard() {
	    Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

	    try {
	        if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
	            String text = (String)t.getTransferData(DataFlavor.stringFlavor);
	            return text;
	        }
	    } catch (UnsupportedFlavorException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    return null;
	}

	// This method writes a string to the system clipboard.
	// otherwise it returns null.
	public static void setClipboard(String str) {
	    StringSelection ss = new StringSelection(str);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}
	
	@Override
	public void render(Graphics graphics, long currentTime) {
		super.render(graphics, currentTime);
		if (fFocused) graphics.setColor(new Color(200, 240, 255));
		else graphics.setColor(Color.white);
		graphics.fillRect(0, 0, fSpan.getWidth(), fSpan.getHeight());
		if (fFocused && fSelectWidth > 0) {
			graphics.setColor(new Color(60, 100, 120));
			graphics.fillRect(fSelectOffset + 5, 4, fSelectWidth + 1, fFont.getLineHeight() + 1);
			// Text 
			int min = Math.min(fCaretChar, fCaretChar2) - fDisplayChar;
			int max = Math.max(fCaretChar2, fCaretChar) - fDisplayChar;
			if (min > 0) fFont.drawString(5, 5, fDisplayingText, Color.black, 0, min);
			fFont.drawString(5, 5, fDisplayingText, Color.white, Math.max(min, 0), Math.min(max, fDisplayingText.length()));
			if (max < fDisplayingText.length()) fFont.drawString(5, 5, fDisplayingText, Color.black, max, fDisplayingText.length());
		} else {
			// Text
			if (!fFocused && fHovering) fFont.drawString(5, 5, fText, new Color(0, 80, 120));
			else if (fDefaultText) fFont.drawString(5, 5, fText, fColor);
			else fFont.drawString(5, 5, fDisplayingText, Color.black);
		}
		// Caret
		if (fFocused && currentTime % 1000 > 500) {
			graphics.setColor(Color.black);
			graphics.fillRect(Math.min(fCaretOffset, fFont.getWidth(fDisplayingText)) + 5, 4, 1, fFont.getLineHeight() + 1);
		}
	}

	public String value() {
		return fText;
	}
	
	@Override
	public void cleanup() {
		super.cleanup();
		if (fNextRepeatKeyFrame >= 0) frameManager().dequeue(fRepeatKeyMinion, fNextRepeatKeyFrame);
		fNextRepeatKeyFrame = -1;
		fLastEvent = null;
		fColor = null;
		fFont = null;
	}
}
