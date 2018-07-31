/*-
 * $Id: BorderStyle.java 4 2010-11-04 07:39:56Z andrewbass $
 */

package com.google.code.getwrong.terminalui;

/**
 * @author Andrew ``Bass'' Shcheglov (andrewbass@gmail.com)
 * @author $Author: andrewbass $
 * @version $Revision: 4 $, $Date:: 2010-11-04 08:39:56 +0100 #$
 */
public final class BorderStyle {
	private final BorderStyleAtom horizontalStyle;

	private final BorderStyleAtom verticalStyle;

	/**
	 * @param horizontalStyle
	 * @param verticalStyle
	 */
	public BorderStyle(final BorderStyleAtom horizontalStyle,
			final BorderStyleAtom verticalStyle) {
		this.horizontalStyle = horizontalStyle;
		this.verticalStyle = verticalStyle;
	}

	public BorderStyleAtom getHorizontalStyle() {
		return this.horizontalStyle;
	}

	public BorderStyleAtom getVerticalStyle() {
		return this.verticalStyle;
	}
}
