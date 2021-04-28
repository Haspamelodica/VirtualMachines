package net.haspamelodica.mama.interpreter.heap;

public interface Vector extends HeapObjectContent
{
	public int getLength();
	public HeapObject get(int i);

	@Override
	public default Tag getTag()
	{
		return Tag.V;
	}
}
