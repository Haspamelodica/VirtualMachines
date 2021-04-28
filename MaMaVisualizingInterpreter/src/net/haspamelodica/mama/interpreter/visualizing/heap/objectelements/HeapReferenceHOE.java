package net.haspamelodica.mama.interpreter.visualizing.heap.objectelements;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawReferenceArrow;

import net.haspamelodica.mama.interpreter.visualizing.gui.MaMaGUI;
import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeapObject;
import net.haspamelodica.swt.helper.gcs.GeneralGC;

public class HeapReferenceHOE implements DrawableHeapObjectElement
{
	private final VisualizingHeapObject referencedObject;

	public HeapReferenceHOE(VisualizingHeapObject referencedObject)
	{
		this.referencedObject = referencedObject;
	}

	@Override
	public void draw(GeneralGC gc, double x, double y, double width, double height)
	{
		gc.setBackground(MaMaGUI.HEAP_REF_BG);
		gc.fillRectangle(x, y, width, height);
		drawReferenceArrow(gc, x + width / 2, y + height / 2, referencedObject.getX(), referencedObject.getY() + height / 2);
	}

	public VisualizingHeapObject getReferencedObject()
	{
		return referencedObject;
	}
}