package lab1;

public class DirectoryEntry {

	String name;
	String number;
	
	public DirectoryEntry() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DirectoryEntry(String name, String phoneNumber) {
		super();
		this.name = name;
		this.number = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectoryEntry other = (DirectoryEntry) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DirectoryEntry [name=" + name + ", phoneNumber=" + number
				+ "]";
	}
	
}
