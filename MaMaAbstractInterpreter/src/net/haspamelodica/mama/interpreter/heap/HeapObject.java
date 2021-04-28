package net.haspamelodica.mama.interpreter.heap;

public interface HeapObject
{
	public HeapObjectContent getContent();
	public void setContent(HeapObjectContent content);
}
