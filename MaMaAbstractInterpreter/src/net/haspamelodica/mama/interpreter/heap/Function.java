package net.haspamelodica.mama.interpreter.heap;

public interface Function extends HeapObjectContent
{
	public int getCodePointer();
	public HeapObject getArguments();
	public HeapObject getGp();

	@Override
	public default Tag getTag()
	{
		return Tag.F;
	}
}
