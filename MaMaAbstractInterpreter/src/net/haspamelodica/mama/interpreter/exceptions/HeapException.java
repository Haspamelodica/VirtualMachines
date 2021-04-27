package net.haspamelodica.mama.interpreter.exceptions;

public class HeapException extends InterpreterException
{
	public HeapException()
	{}
	public HeapException(String message)
	{
		super(message);
	}
	public HeapException(String message, Throwable cause)
	{
		super(message, cause);
	}
	public HeapException(Throwable cause)
	{
		super(cause);
	}
	protected HeapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
