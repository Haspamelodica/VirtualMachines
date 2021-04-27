package net.haspamelodica.mama.interpreter.visualizing.heap.objectelements;

import org.eclipse.swt.graphics.Color;

import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeapObjectContent;
import net.haspamelodica.swt.helper.gcs.GeneralGC;

public class HeapReferenceHOE implements DrawableHeapObjectElement
{
	private static final int	RED			= 255;
	private static final int	GREEN		= 200;
	private static final int	BLUE		= 200;
	private static final Color	BG_COLOR	= new Color(RED, GREEN, BLUE);

	private final VisualizingHeapObjectContent referencedObject;

	public HeapReferenceHOE(VisualizingHeapObjectContent referencedObject)
	{
		this.referencedObject = referencedObject;
	}

	@Override
	public void draw(GeneralGC gc, double x, double y, double width, double height)
	{
		gc.setBackground(BG_COLOR);
		gc.fillRectangle(x, y, width, height);
		//TODO let this look more like an arrow
		gc.drawLine(x + width / 2, y + height / 2, referencedObject.getX(), referencedObject.getY() + height / 2);
	}

	public VisualizingHeapObjectContent getReferencedObject()
	{
		return referencedObject;
	}
}