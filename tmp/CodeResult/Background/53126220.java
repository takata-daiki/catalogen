package prosjekt.models;

import prosjekt.Config;

/**
 * 
 * A background is an object defined by an image, and modifiers.
 * @author H?kon Erichsen
 *
 */
public class Background extends Entity {
	
	private static final long serialVersionUID = -5905644459935975877L;
	
	// Used to clone
	private String imageFileName;
	private String modifiers;
	
	public Background (char symbol, String imageFileName, String modifiers) {
		/* Currently, modifiers can only be two letters:
		 * c = collidable, a = overinteractable
		 * 
		 * If something is overinteractable, it has to be collidable as well.
		 * Thus, the logic here is pretty simple, unless we have to add more modifiers:
		 */
		
		if ("c".equals(modifiers))
			this.setCollidable(true);
		
		if ("a".equals(modifiers)) {
			this.setCollidable(true);
			this.setOverInteractable(true);
		}
		
		this.imageFileName = imageFileName;
		this.modifiers = modifiers;
		this.setSymbol(symbol);
		this.setImage(Config.item("EntityGraphicsPath") + imageFileName);
	}
	
	/**
	 * Clonage
	 */
	public Background clone () {
		return new Background (this.getSymbol(), this.imageFileName, this.modifiers);
	}
}
