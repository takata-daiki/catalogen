package com.praxiteles.library.field;

public class IntegerField extends Field {
	private static Integer type = new Integer(0);

	public IntegerField(String key) {
		super(key);
	}

	@Override
	public Object getType() {
		return IntegerField.type;
	}
}
