package net.haspamelodica.mama.interpreter.heap;

public interface Function extends HeapObjectContent
{
	public int getCodePointer();
	public HeapObject getArgumentPointer();
	public HeapObject getGlobalPointer();

	@Override
	public default Tag getTag()
	{
		return Tag.F;
	}
}
