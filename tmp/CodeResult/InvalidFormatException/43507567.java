package com.bp.pensionline.exception;

public class InvalidFormatException extends Exception
{
	private String message = "";
	
	
	public InvalidFormatException(String message)
	{
		this.message = message;
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage()
	{
		// TODO Auto-generated method stub
		return message;
	}
	
}
