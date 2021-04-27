package net.haspamelodica.mama.interpreter.heap;

public class HeapObjectRef
{
	private HeapObjectContent content;

	public HeapObjectRef(HeapObjectContent content)
	{
		setContent(content);
	}

	public HeapObjectContent getContent()
	{
		return content;
	}

	public void setContent(HeapObjectContent content)
	{
		this.content = content;
	}
}
