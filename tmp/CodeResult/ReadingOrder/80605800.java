/**
 * JacobGen generated file --- do not edit
 *
 * (http://www.sourceforge.net/projects/jacob-project */
package com.jacobgen.microsoft.excel;

import com.jacob.com.*;

public class TickLabels extends Dispatch {

	public static final String componentName = "Excel.TickLabels";

	public TickLabels() {
		super(componentName);
	}

	/**
	* This constructor is used instead of a case operation to
	* turn a Dispatch object into a wider object - it must exist
	* in every wrapper class whose instances may be returned from
	* method calls wrapped in VT_DISPATCH Variants.
	*/
	public TickLabels(Dispatch d) {
		// take over the IDispatch pointer
		m_pDispatch = d.m_pDispatch;
		// null out the input's pointer
		d.m_pDispatch = 0;
	}

	public TickLabels(String compName) {
		super(compName);
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type Application
	 */
	public Application getApplication() {
		return new Application(Dispatch.get(this, "Application").toDispatch());
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getCreator() {
		return Dispatch.get(this, "Creator").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type Object
	 */
	public Object getParent() {
		return Dispatch.get(this, "Parent");
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type Variant
	 */
	public Variant delete() {
		return Dispatch.call(this, "Delete");
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type Font
	 */
	public Font getFont() {
		return new Font(Dispatch.get(this, "Font").toDispatch());
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getName() {
		return Dispatch.get(this, "Name").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type String
	 */
	public String getNumberFormat() {
		return Dispatch.get(this, "NumberFormat").toString();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param numberFormat an input-parameter of type String
	 */
	public void setNumberFormat(String numberFormat) {
		Dispatch.put(this, "NumberFormat", numberFormat);
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getNumberFormatLinked() {
		return Dispatch.get(this, "NumberFormatLinked").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param numberFormatLinked an input-parameter of type boolean
	 */
	public void setNumberFormatLinked(boolean numberFormatLinked) {
		Dispatch.put(this, "NumberFormatLinked", new Variant(numberFormatLinked));
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type Variant
	 */
	public Variant getNumberFormatLocal() {
		return Dispatch.get(this, "NumberFormatLocal");
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param numberFormatLocal an input-parameter of type Variant
	 */
	public void setNumberFormatLocal(Variant numberFormatLocal) {
		Dispatch.put(this, "NumberFormatLocal", numberFormatLocal);
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getOrientation() {
		return Dispatch.get(this, "Orientation").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param orientation an input-parameter of type int
	 */
	public void setOrientation(int orientation) {
		Dispatch.put(this, "Orientation", new Variant(orientation));
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type Variant
	 */
	public Variant select() {
		return Dispatch.call(this, "Select");
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getReadingOrder() {
		return Dispatch.get(this, "ReadingOrder").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param readingOrder an input-parameter of type int
	 */
	public void setReadingOrder(int readingOrder) {
		Dispatch.put(this, "ReadingOrder", new Variant(readingOrder));
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type Variant
	 */
	public Variant getAutoScaleFont() {
		return Dispatch.get(this, "AutoScaleFont");
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param autoScaleFont an input-parameter of type Variant
	 */
	public void setAutoScaleFont(Variant autoScaleFont) {
		Dispatch.put(this, "AutoScaleFont", autoScaleFont);
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getDepth() {
		return Dispatch.get(this, "Depth").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getOffset() {
		return Dispatch.get(this, "Offset").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param offset an input-parameter of type int
	 */
	public void setOffset(int offset) {
		Dispatch.put(this, "Offset", new Variant(offset));
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type int
	 */
	public int getAlignment() {
		return Dispatch.get(this, "Alignment").changeType(Variant.VariantInt).getInt();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param alignment an input-parameter of type int
	 */
	public void setAlignment(int alignment) {
		Dispatch.put(this, "Alignment", new Variant(alignment));
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type boolean
	 */
	public boolean getMultiLevel() {
		return Dispatch.get(this, "MultiLevel").changeType(Variant.VariantBoolean).getBoolean();
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @param multiLevel an input-parameter of type boolean
	 */
	public void setMultiLevel(boolean multiLevel) {
		Dispatch.put(this, "MultiLevel", new Variant(multiLevel));
	}

	/**
	 * Wrapper for calling the ActiveX-Method with input-parameter(s).
	 * @return the result is of type ChartFormat
	 */
	public ChartFormat getFormat() {
		return new ChartFormat(Dispatch.get(this, "Format").toDispatch());
	}

}
