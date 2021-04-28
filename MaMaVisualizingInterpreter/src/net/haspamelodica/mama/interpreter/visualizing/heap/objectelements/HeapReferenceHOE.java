package net.haspamelodica.mama.interpreter.visualizing.heap.objectelements;

import net.haspamelodica.mama.interpreter.visualizing.gui.MaMaGUI;
import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeapObjectContent;
import net.haspamelodica.swt.helper.gcs.GeneralGC;

public class HeapReferenceHOE implements DrawableHeapObjectElement
{
	private final VisualizingHeapObjectContent referencedObject;

	public HeapReferenceHOE(VisualizingHeapObjectContent referencedObject)
	{
		this.referencedObject = referencedObject;
	}

	@Override
	public void draw(GeneralGC gc, double x, double y, double width, double height)
	{
		gc.setBackground(MaMaGUI.HEAP_REF_BG);
		gc.fillRectangle(x, y, width, height);
		//TODO let this look more like an arrow
		gc.drawLine(x + width / 2, y + height / 2, referencedObject.getX(), referencedObject.getY() + height / 2);
	}

	public VisualizingHeapObjectContent getReferencedObject()
	{
		return referencedObject;
	}
}