package net.haspamelodica.mama.interpreter.exceptions;

public class StackException extends InterpreterException
{
	public StackException()
	{}
	public StackException(String message)
	{
		super(message);
	}
	public StackException(String message, Throwable cause)
	{
		super(message, cause);
	}
	public StackException(Throwable cause)
	{
		super(cause);
	}
	protected StackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
