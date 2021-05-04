package net.haspamelodica.mama.interpreter.heap;

import java.util.List;

public interface Heap
{
	public void clear();
	public BasicValue createBasicContent(int value);
	public Closure createClosureContent(int codePointer, HeapObject globalPointer);
	public Function createFunctionContent(int codePointer, HeapObject arguments, HeapObject globalPointer);
	public Vector createVectorContent(List<HeapObject> referencedObjects);
	public HeapObject createObject(HeapObjectContent content);
	public default HeapObject createBasic(int value)
	{
		return createObject(createBasicContent(value));
	}
	public default HeapObject createClosure(int codePointer, HeapObject globalPointer)
	{
		return createObject(createClosureContent(codePointer, globalPointer));
	}
	public default HeapObject createFunction(int codePointer, HeapObject arguments, HeapObject globalPointer)
	{
		return createObject(createFunctionContent(codePointer, arguments, globalPointer));
	}
	public default HeapObject createVector(List<HeapObject> referencedObjects)
	{
		return createObject(createVectorContent(referencedObjects));
	}
}
