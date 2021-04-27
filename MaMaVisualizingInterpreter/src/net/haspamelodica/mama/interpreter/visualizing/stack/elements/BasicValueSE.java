package net.haspamelodica.mama.interpreter.visualizing.stack.elements;

public class BasicValueSE implements StackElement
{
	private final int value;

	public BasicValueSE(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public Type getType()
	{
		return Type.BASIC_VALUE;
	}
}