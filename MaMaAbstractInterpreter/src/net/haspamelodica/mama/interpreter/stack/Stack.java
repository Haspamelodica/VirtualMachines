package net.haspamelodica.mama.interpreter.stack;

import net.haspamelodica.mama.interpreter.heap.HeapObject;

public interface Stack
{
	public void clear();

	public void pushBasic(int value);
	public void pushHeapReference(HeapObject referencedObject);
	public int popBasic();
	public HeapObject popHeapReference();

	public int getStackPointer();
	public void popMultiple(int numberOfValuesToPop);
	public HeapObject getHeapReferenceRelative(int offsetFromStackPointer);
}
