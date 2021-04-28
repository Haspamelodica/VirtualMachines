package net.haspamelodica.mama.interpreter.visualizing.stack.elements;

import org.eclipse.swt.graphics.GC;

import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.visualizing.gui.MaMaGUI;
import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeapObject;

public class HeapReferenceSE implements StackElement
{
	private final VisualizingHeapObject referencedObject;

	public HeapReferenceSE(HeapObject referencedObject)
	{
		//TODO can we do this cleaner?
		this.referencedObject = (VisualizingHeapObject) referencedObject;
	}

	public VisualizingHeapObject getReferencedObject()
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