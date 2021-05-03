package net.haspamelodica.mama.interpreter.heap;

public interface HeapObject
{
	public HeapObjectContent getContent();
	public void setContent(HeapObjectContent content);

	public default BasicValue checkBasicValue()
	{
		return getContent().checkBasicValue();
	}
	public default Closure checkClosure()
	{
		return getContent().checkClosure();
	}
	public default Function checkFunction()
	{
		return getContent().checkFunction();
	}
	public default Vector checkVector()
	{
		return getContent().checkVector();
	}
}
