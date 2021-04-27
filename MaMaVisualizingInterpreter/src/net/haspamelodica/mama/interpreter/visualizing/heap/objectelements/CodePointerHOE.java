package net.haspamelodica.mama.interpreter.visualizing.heap.objectelements;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawTextCentered;

import net.haspamelodica.swt.helper.gcs.GeneralGC;

public class CodePointerHOE implements DrawableHeapObjectElement
{
	private final int pointer;

	public CodePointerHOE(int pointer)
	{
		this.pointer = pointer;
	}

	public int getPointer()
	{
		return pointer;
	}

	@Override
	public void draw(GeneralGC gc, double x, double y, double width, double height)
	{
		// TODO draw a line to the referenced code part
		drawTextCentered(gc, "code pointer " + pointer, x, y, width, height);
	}
}