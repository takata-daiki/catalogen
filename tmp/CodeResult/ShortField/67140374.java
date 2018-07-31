package safrain.nodelet.structure.fieldsetter;

public class ShortField extends AbstractValueField {
	public short value;

	@Override
	protected void set0(Object obj) throws Exception {
		field.setShort(obj, value);
	}

	@Override
	public void setValue(Object value) {
		this.value = (Short) value;
	}

}