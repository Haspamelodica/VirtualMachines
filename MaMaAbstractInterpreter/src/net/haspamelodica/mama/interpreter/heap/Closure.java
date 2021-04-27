package net.haspamelodica.mama.interpreter.heap;

public interface Closure extends HeapObjectContent
{
	public int getCodePointer();
	public HeapObjectRef getGp();

	@Override
	public default Tag getTag()
	{
		return Tag.C;
	}
}
