package net.haspamelodica.mama.interpreter.exceptions;

public class InterpreterException extends RuntimeException
{
	public InterpreterException()
	{}
	public InterpreterException(String message)
	{
		super(message);
	}
	public InterpreterException(String message, Throwable cause)
	{
		super(message, cause);
	}
	public InterpreterException(Throwable cause)
	{
		super(cause);
	}
	protected InterpreterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
