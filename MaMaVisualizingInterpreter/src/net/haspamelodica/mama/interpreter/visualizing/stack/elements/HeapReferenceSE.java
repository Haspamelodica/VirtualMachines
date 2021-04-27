package net.haspamelodica.mama.interpreter.visualizing.stack.elements;

import net.haspamelodica.mama.interpreter.heap.HeapObjectRef;

public class HeapReferenceSE implements StackElement
{
	private final HeapObjectRef referencedObject;

	public HeapReferenceSE(HeapObjectRef referencedObject)
	{
		this.referencedObject = referencedObject;
	}

	public HeapObjectRef getReferencedObject()
	{
		return referencedObject;
	}

	@Override
	public Type getType()
	{
		return Type.HEAP_REFERENCE;
	}
}