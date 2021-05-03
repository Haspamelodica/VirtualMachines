package net.haspamelodica.mama.interpreter.heap;

public interface Closure extends HeapObjectContent
{
	public int getCodePointer();
	public HeapObject getGlobalPointer();

	@Override
	public default Tag getTag()
	{
		return Tag.C;
	}
}
