package net.haspamelodica.mama.interpreter.heap;

public interface Function extends HeapObjectContent
{
	public int getCodePointer();
	public HeapObjectRef getArguments();
	public HeapObjectRef getGp();

	@Override
	public default Tag getTag()
	{
		return Tag.F;
	}
}
