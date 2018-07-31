package de.marco.property;

import java.util.LinkedList;
import java.util.List;

public class PropertyValue {

	private final List<ValueEntry> entryList = new LinkedList<ValueEntry>();

	protected PropertyValue(final String propertyValue) {
		parse(propertyValue);
	}

	public PropertyValue() {

	}

	private void parse(final String propertyValue) {
		final String[] values = propertyValue.split(" ");
		for (final String value : values) {
			entryList.add(new ValueEntry(value.trim()));
		}
	}

	public List<ValueEntry> getEntryList() {
		return entryList;
	}

	public String getPlain(final boolean formatted) {
		final StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (final ValueEntry entry : entryList) {
			if (!first) {
				builder.append(" ");
			}
			builder.append(entry.getValue());
			first = false;
		}
		return builder.toString();
	}

	public void clear() {
		entryList.clear();
	}

	public void addEntry(final ValueEntry value) {
		entryList.add(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entryList == null) ? 0 : entryList.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PropertyValue other = (PropertyValue) obj;
		if (entryList == null) {
			if (other.entryList != null) {
				return false;
			}
		} else if (!entryList.equals(other.entryList)) {
			return false;
		}
		return true;
	}

}
