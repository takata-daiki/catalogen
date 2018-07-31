package cyfronet.gs2.ew.base.shared.beans;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DirectoryEntry implements IsSerializable,
		Comparable<DirectoryEntry> {

	public static final String SEPARATOR = "/";
	public static final String EXPERIMENT_EXTENSION = ".exp.xml";

	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isDirectory() {
		return this.name.endsWith(SEPARATOR);
	}

	public boolean isFile() {
		return !this.name.endsWith(SEPARATOR);
	}

	@Override
	public int hashCode() {
		return this.name == null ? 0 : this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.name == null
				|| !(obj instanceof DirectoryEntry)) {
			return false;
		} else {
			return this.name.equals(((DirectoryEntry) obj).name);
		}
	}

	public int compareTo(final DirectoryEntry o) {
		if (this.isDirectory() && !o.isDirectory()) {
			return -1;
		} else if (!this.isDirectory() && o.isDirectory()) {
			return 1;
		} else {
			return this.name.toLowerCase().compareTo(o.name.toLowerCase());
		}
	}

}
