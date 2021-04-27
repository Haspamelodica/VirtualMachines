package net.haspamelodica.mama.interpreter.heap;

import net.haspamelodica.mama.interpreter.exceptions.HeapException;

public interface HeapObjectContent
{
	public Tag getTag();

	public default BasicValue checkBasicValue()
	{
		if(getTag() != Tag.B)
			throw new HeapException("Expected basic value (B), was " + getTag());
		return (BasicValue) this;
	}
	public default Closure checkClosure()
	{
		if(getTag() != Tag.C)
			throw new HeapException("Expected closure (C), was " + getTag());
		return (Closure) this;
	}
	public default Function checkFunction()
	{
		if(getTag() != Tag.F)
			throw new HeapException("Expected function (F), was " + getTag());
		return (Function) this;
	}
	public default Vector checkVector()
	{
		if(getTag() != Tag.V)
			throw new HeapException("Expected vector (V), was " + getTag());
		return (Vector) this;
	}
}
