package com.sirma.itt.javacourse.annotations;

/**
 * The parent class.
 * 
 * @author Nikolay Ch
 * 
 */
@Layout(priority=0)
public class Parent {
	private int number;

	public Parent(int number) {
		this.number = number;
	}

	public Parent() {
		this.number = 0;
	}

	/**
	 * Getter for the number field.
	 * 
	 * @return the value of the field
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * Setter for the field number.
	 * 
	 * @param number
	 *            the value
	 */
	public void setNubmer(int number) {
		this.number = number;
	}
}

/**
 * The first class which extends the parent.
 */
@Layout(priority=1)
class Child1 extends Parent {
	public String name;

	public Child1() {
		super();
		name = "No name";
	}

	/**
	 * Constructor for the class. Initializes the parent field number and the
	 * child's field name.
	 * 
	 * @param number
	 *            the value for the parent
	 * @param name
	 *            the string for the name
	 */
	public Child1(int number, String name) {
		super(number);
		this.name = name;
	}
}
@Layout(priority=2)
class Child2 extends Parent {
	public String name1;
	public String name2;

	/**
	 * Initializes the variables.
	 * 
	 * @param number
	 *            the value for the field number
	 * @param name1
	 *            the value for the field name1
	 * @param name2
	 *            the value for the field name2
	 */
	public Child2(int number, String name1, String name2) {
		super(number);
		this.name1 = name1;
		this.name2 = name2;
	}
}
@Layout(priority=3)
class Child3 extends Parent {
	public String name1;
	public String name2;
	public String name3;

	/**
	 * Initializes the variables.
	 * 
	 * @param number
	 *            the value for the field number
	 * @param name1
	 *            the value for the field name1
	 * @param name2
	 *            the value for the field name2
	 * @param name3
	 *            the value for the field name3
	 */
	public Child3(int number, String name1, String name2, String name3) {
		super(number);
		this.name1 = name1;
		this.name2 = name2;
		this.name3 = name3;
	}

}