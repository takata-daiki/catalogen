package net.minecraft.src.modbridge._native;

public class NotImplementedException extends RuntimeException
{
	public NotImplementedException()
	{
		super("The specified method is not implemented.");
	}
	
	public NotImplementedException(String msg)
	{
		super(msg);
	}
	
	public NotImplementedException(Throwable throwable)
	{
		super("The specified method is not implemented.", throwable);
	}
	
	public NotImplementedException(String msg, Throwable throwable)
	{
		super(msg, throwable);
	}
}
