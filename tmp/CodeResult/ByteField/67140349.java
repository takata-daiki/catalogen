package safrain.nodelet.structure.fieldsetter;

public class ByteField extends AbstractValueField {
	public byte value;

	@Override
	protected void set0(Object obj) throws Exception {
		field.setByte(obj, value);
	}

	@Override
	public void setValue(Object value) {
		this.value = (Byte) value;

	}

}