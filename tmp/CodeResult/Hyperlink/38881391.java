package com.pointcliki.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Vector2f;

import com.pointcliki.core.PointClikiGame;
import com.pointcliki.core.TextEntity;
import com.pointcliki.event.IEvent;
import com.pointcliki.input.MouseEvent;

/**
 * The Hyperlink class is a clickable text component
 * 
 * @author Hugheth
 * @since 3
 */
public class Hyperlink extends UIEntity {

	/**
	 * Serial key
	 */
	private static final long serialVersionUID = -4301991704266922108L;
	
	protected TextEntity fText;
	protected String fHref;
	protected boolean fSelected;
	
	public Hyperlink(String text, String href) {
		this(text, href, PointClikiGame.resourceManager().UIFont());
	}
	
	public Hyperlink(String text, String href, UnicodeFont font) {
		this(text, href, font, false);
	}
	
	public Hyperlink(String text, String href, UnicodeFont font, boolean selected) {
		fText = new TextEntity(text, TextEntity.ALIGN_LEFT, font);
		fText.position(new Vector2f(5, 2 + font.getHeight(text) / 2));
		addChild(fText, 0);
		resize(new Vector2f(fText.span().getWidth() + 10, fText.span().getHeight() + 4));
		fHref = href;
		if (selected) select();
	}
	
	public void handleUIMouseEvent(String type, Vector2f local, MouseEvent event) {
		if (type.equals("mouse.down")) {
			select();
			fDispatcher.dispatchEvent("hyperlink", new HyperlinkEvent(this));
		}
	}
	
	@Override
	public void hoverOver() {
		super.hoverOver();
		if (!fSelected) fText.color(new Color(255, 220, 50));
	}
	
	@Override
	public void hoverOut() {
		super.hoverOut();
		if (!fSelected) fText.color(Color.white);
	}
	
	public void select() {
		fSelected = true;
		fText.color(Color.cyan);
	}
	public void deselect() {
		fSelected = false;
		fText.color(Color.white);
	}
	
	public String href() {
		return fHref;
	}
	
	public static class HyperlinkEvent implements IEvent {
		
		protected Hyperlink fH;
		
		public HyperlinkEvent(Hyperlink h) {
			fH = h;
		}
		public Hyperlink hyperlink() {
			return fH;
		}
		public String href() {
			return fH.href();
		}
	}
}

