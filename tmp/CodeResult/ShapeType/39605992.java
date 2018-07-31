/**
 * 
 */
package com.kjoshi.shareit.responsible.enums;

/**
 * @author kailash
 * 
 */
public enum ShapeType {
	SQUARE("square"), RECTANGLE("rectangle"), TRIANGLE("triangle"), CICRLE(
			"circle");
	public String label;

	private ShapeType(String label) {
		this.label = label;
	}

}
