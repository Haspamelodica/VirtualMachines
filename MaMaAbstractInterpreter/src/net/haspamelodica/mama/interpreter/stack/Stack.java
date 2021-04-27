package net.haspamelodica.mama.interpreter.stack;

import net.haspamelodica.mama.interpreter.heap.HeapObjectRef;

public interface Stack
{
	public void clear();
	public void pushBasic(int value);
	public void pushHeapReference(HeapObjectRef referencedObject);
	public int popBasic();
	public HeapObjectRef popHeapReference();
}
