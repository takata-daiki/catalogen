package lipstone.joshua.parser.exceptions;

import java.util.ArrayList;

import lipstone.joshua.parser.plugin.ParserPlugin;
import lipstone.joshua.parser.util.ConsCell;

public class InvalidOperationException extends ParserException {
	
	private String operation;
	private ArrayList<ConsCell> parameters;
	
	/**
	 * Basic Constructor - does not include a general message about the error
	 * 
	 * @param thrower
	 *            the plugin that threw this exception
	 * @param operation
	 *            the operation that was called
	 * @param parameters
	 *            the parameters in the operation
	 */
	public InvalidOperationException(ParserPlugin thrower, String operation, ArrayList<ConsCell> parameters) {
		super(thrower);
		String message = "The operation, " + operation;
		if (parameters.size() > 0) {
			message = message + ", with parameters, ";
			for (ConsCell parameter : parameters)
				message = message + parameter.toString() + ", ";
			if (message.substring(message.length() - 2).equals(", "))
				message = message.substring(0, message.length() - 2);
		}
		else
			message = "The parameterless operation, " + operation;
		message = message + ", failed because ";
		if(thrower == null)
			message = message + "it is not mapped to any plugin.";
		else
			message = message + "the plugin, " + thrower.getID() + ", does not recognize this operation and parameter combination.";
		setMessage(message);
		this.operation = operation;
		this.parameters = parameters;
	}
	
	/**
	 * Complete constructor - includes a general message about the error
	 * 
	 * @param message
	 *            the message for this plugin to include
	 * @param thrower
	 *            the plugin that threw this exception
	 * @param operation
	 *            the operation that was called
	 * @param parameters
	 *            the parameters in the operation
	 */
	public InvalidOperationException(String message, ParserPlugin thrower, String operation, ArrayList<ConsCell> parameters) {
		super(message, thrower);
		this.operation = operation;
		this.parameters = parameters;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public ArrayList<ConsCell> getParameters() {
		return parameters;
	}
	
}
