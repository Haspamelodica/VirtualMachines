package net.haspamelodica.puf.compiler.exception;

public class CompilerException extends RuntimeException
{
	public CompilerException()
	{}
	public CompilerException(String message)
	{
		super(message);
	}
	public CompilerException(String message, Throwable cause)
	{
		super(message, cause);
	}
	public CompilerException(Throwable cause)
	{
		super(cause);
	}
	protected CompilerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
