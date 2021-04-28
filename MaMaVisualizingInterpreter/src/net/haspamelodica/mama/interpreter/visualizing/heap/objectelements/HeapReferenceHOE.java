package net.haspamelodica.mama.interpreter.visualizing.heap.objectelements;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawHeapReferenceArrow;

import net.haspamelodica.mama.interpreter.visualizing.gui.MaMaGUI;
import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeapObject;
import net.haspamelodica.swt.helper.gcs.GeneralGC;
import net.haspamelodica.swt.helper.swtobjectwrappers.Rectangle;

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
		drawHeapReferenceArrow(gc, new Rectangle(x, y, width, height), referencedObject.getBounds());
	}

	public VisualizingHeapObject getReferencedObject()
	{
		return referencedObject;
	}
}