package net.haspamelodica.mama.interpreter.heap;

public interface BasicValue extends HeapObjectContent
{
	public int getValue();
	
	@Override
	public default Tag getTag()
	{
		return Tag.B;
	}
}
