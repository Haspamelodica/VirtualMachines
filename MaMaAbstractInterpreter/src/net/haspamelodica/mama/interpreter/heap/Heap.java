package net.haspamelodica.mama.interpreter.heap;

import java.util.List;

public interface Heap
{
	public void clear();
	public BasicValue createBasicContent(int value);
	public Vector createVectorContent(List<HeapObject> referencedObjects);
	public HeapObject createObject(HeapObjectContent content);
	public default HeapObject createBasic(int value)
	{
		return createObject(createBasicContent(value));
	}
	public default HeapObject createVector(List<HeapObject> referencedObjects)
	{
		return createObject(createVectorContent(referencedObjects));
	}
}
