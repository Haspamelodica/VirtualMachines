package net.haspamelodica.mama.interpreter.heap;

import java.util.List;

public interface Heap
{
	public void clear();
	public BasicValue createBasicContent(int value);
	public Vector createVectorContent(List<HeapObjectRef> referencedObjects);
	public default HeapObjectRef createBasic(int value)
	{
		return new HeapObjectRef(createBasicContent(value));
	}
	public default HeapObjectRef createVector(List<HeapObjectRef> referencedObjects)
	{
		return new HeapObjectRef(createVectorContent(referencedObjects));
	}
}
