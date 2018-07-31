package net.lojjic.xul.css.value.css2;

import org.apache.batik.css.engine.value.IdentifierManager;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.URIValue;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.util.SVGTypes;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.dom.DOMException;
import net.lojjic.xul.css.CSS2Constants;

/**
 * Manager for CSS2 'list-style-image' property
 */
public class ListStyleImageManager extends IdentifierManager {

	protected static final StringMap values = new StringMap();
	static {
		values.put(CSS2Constants.CSS_NONE_VALUE, CSS2ValueConstants.NONE_VALUE);
	}

	/**
	 * Returns the map that contains the name/value mappings for each
	 * possible identifiers.
	 */
	public StringMap getIdentifiers() {
		return values;
	}

	public Value getDefaultValue() {
		return CSS2ValueConstants.NONE_VALUE;
	}

	public boolean isInheritedProperty() {
		return true;
	}

	public boolean isAnimatableProperty() {
		return false;
	}

	public boolean isAdditiveProperty() {
		return false;
	}

	public int getPropertyType() {
		return SVGTypes.TYPE_URI_OR_IDENT;
	}
	
	/**
	 * Returns the name of the property handled.
	 */
	public String getPropertyName() {
		return CSS2Constants.CSS_LIST_STYLE_IMAGE_PROPERTY;
	}

	/**
	 * Implements {@link org.apache.batik.css.engine.value.ValueManager#createValue(org.w3c.css.sac.LexicalUnit,org.apache.batik.css.engine.CSSEngine)}.
	 */
	public Value createValue(LexicalUnit lu, CSSEngine engine) throws DOMException {
		if(lu.getLexicalUnitType() == LexicalUnit.SAC_URI) {
			return new URIValue(lu.getStringValue(), resolveURI(engine.getCSSBaseURI(), lu.getStringValue()));
		}
		return super.createValue(lu, engine);
	}
}
