/*
Copyright (C) 2009 Grant Slender

This file is part of OFCGWT.
http://code.google.com/p/ofcgwt/

OFCGWT is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

OFCGWT is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
 */
package com.rednels.ofcgwt.client.model.elements;

import java.util.Arrays;
import java.util.Collection;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.rednels.ofcgwt.client.model.JSONizable;
import com.rednels.ofcgwt.client.model.elements.dot.BaseDot;
import com.rednels.ofcgwt.client.model.elements.dot.SolidDot;

/**
 * OFC scatter chart
 */
public class ScatterChart extends Element implements JSONizable {

	public static enum ScatterStyle {

		LINE("scatter_line"), POINT("scatter");

		private String style;

		ScatterStyle(String style) {
			this.style = style;
		}

		public String getStyle() {
			return style;
		}
	}

	private String colour;
	private Integer dotSize;
	private BaseDot dotStyle = new SolidDot();

	/**
	 * Creates a new scatter chart with ScatterStyle.POINT style
	 */
	public ScatterChart() {
		this(ScatterStyle.POINT);
	}

	/**
	 * Creates a new scatter chart with provided style.
	 */
	public ScatterChart(ScatterStyle style) {
		super(style.getStyle());
	}

	/**
	 * Adds the point.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void addPoint(Number x, Number y) {
		BaseDot bd = new BaseDot(null) {};
		bd.setXY(x, y);
		addPoints(bd);
	}

	/**
	 * Adds the points.
	 * 
	 * @param points
	 *            the points
	 */
	public void addPoints(BaseDot... points) {
		getValues().addAll(Arrays.asList(points));
	}

	/**
	 * Adds the points.
	 * 
	 * @param points
	 *            the points
	 */
	public void addPoints(Collection<BaseDot> points) {
		getValues().addAll(points);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rednels.ofcgwt.client.model.elements.Element.buildJSON()
	 */
	public JSONValue buildJSON() {
		JSONObject json = (JSONObject) super.buildJSON();
		if (dotSize != null) json.put("dot-size", new JSONNumber(dotSize.doubleValue()));
		if (this.dotStyle != null) json.put("dot-style", dotStyle.buildJSON());
		if (colour != null) json.put("colour", new JSONString(colour));
		return json;
	}

	/**
	 * Gets the colour.
	 * 
	 * @return the colour
	 */
	public String getColour() {
		return colour;
	}

	/**
	 * Gets the dot size.
	 * 
	 * @return the dot size
	 */
	public Integer getDotSize() {
		return dotSize;
	}

	/**
	 * Sets the colour in HTML hex format (#ffffff)
	 * 
	 * @param colour
	 *            the new colour
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}

	/**
	 * Sets the dot size.
	 * 
	 * @param dotSize
	 *            the new dot size
	 */
	public void setDotSize(Integer dotSize) {
		this.dotSize = dotSize;
	}

	/**
	 * Sets the dot style.
	 * 
	 * @param dotStyle
	 *            the new dot style
	 */
	public void setDotStyle(BaseDot dotStyle) {
		this.dotStyle = dotStyle;
	}
}
