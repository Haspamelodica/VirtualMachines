package net.haspamelodica.mama.interpreter.visualizing.heap.objectelements;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawTextCentered;

import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeapObject;
import net.haspamelodica.swt.helper.gcs.GeneralGC;

public class BasicValueHOE implements DrawableHeapObjectElement
{
	private final int value;

	public BasicValueHOE(int value)
	{
		this.value = value;
	}

	@Override
	public void draw(GeneralGC gc, double x, double y, double width, double height)
	{
		gc.setBackground(VisualizingHeapObject.DEFAULT_BG);
		gc.fillRectangle(x, y, width, height);
		drawTextCentered(gc, Integer.toString(value), x, y, width, height);
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public Type getType()
	{
		return Type.BASIC_VALUE;
	}
}