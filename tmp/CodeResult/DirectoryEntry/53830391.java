/**
 * Class that models a single name-number entry for the phone directory.
 *
 * @author Elliot Koffman and Paul Wolfgang
 *
 */
public class DirectoryEntry {

	private String name;	// The name of the individual in the entry
	private String number;	// The phone number for this individual
	
	/**
	 *   Creates a new DirectoryEntry with the specified name and number.
	 *   @param initialName The name of the individual
	 *   @param initialNumber The phone number for this individual
	 */
	public DirectoryEntry(String initialName, String initialNumber) {
		name = initialName;
		number = initialNumber;
	}
	
	/**
	 *  Retrieves the name of this directory entry.
	 *  @return The name of this directory entry.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 *  Retrieves the number of this directory entry.
	 *  @return The number of this directory entry.
	 */
	public String getNumber() {
		return number;
	}	

	/**
	 *  Sets the number of this directory entry to the 
	 *  specified number.
	 *  @param newNumber The new number for this directory entry.
	 */
	public void setNumber(String newNumber) {
		number = newNumber;
	}
	
}
