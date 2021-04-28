package net.haspamelodica.mama.interpreter.visualizing.stack.elements;

import org.eclipse.swt.graphics.GC;

import net.haspamelodica.mama.interpreter.heap.HeapObjectRef;
import net.haspamelodica.mama.interpreter.visualizing.gui.MaMaGUI;

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
	public void draw(GC gc, int x, int y, int width, int height)
	{
		gc.setBackground(MaMaGUI.HEAP_REF_BG);
		gc.fillRectangle(x, y, width, height);
	}

	@Override
	public Type getType()
	{
		return Type.HEAP_REFERENCE;
	}
}