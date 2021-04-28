package net.haspamelodica.mama.interpreter.visualizing.stack.elements;

import org.eclipse.swt.graphics.GC;

import net.haspamelodica.mama.interpreter.exceptions.StackException;

public interface StackElement
{
	public void draw(GC gc, int x, int y, int width, int height);
	public StackElement.Type getType();

	public static enum Type
	{
		BASIC_VALUE,
		HEAP_REFERENCE;
	}

	public default BasicValueSE checkBasicValue()
	{
		if(getType() != Type.BASIC_VALUE)
			throw new StackException("Expected basic value, but was " + getType());
		return (BasicValueSE) this;
	}
	public default HeapReferenceSE checkHeapReference()
	{
		if(getType() != Type.HEAP_REFERENCE)
			throw new StackException("Expected heap reference, but was " + getType());
		return (HeapReferenceSE) this;
	}
}