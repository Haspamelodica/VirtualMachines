package net.haspamelodica.mama.interpreter.visualizing.heap.objectelements;

import net.haspamelodica.mama.interpreter.visualizing.gui.MaMaGUI;
import net.haspamelodica.swt.helper.gcs.GeneralGC;

public class CodePointerHOE implements DrawableHeapObjectElement
{
	private final int pointer;

	public CodePointerHOE(int pointer)
	{
		this.pointer = pointer;
	}

	@Override
	public void draw(GeneralGC gc, double x, double y, double width, double height)
	{
		gc.setBackground(MaMaGUI.CODE_REF_BG);
		gc.fillRectangle(x, y, width, height);
	}

	public int getPointer()
	{
		return pointer;
	}

	@Override
	public Type getType()
	{
		return Type.CODE_POINTER;
	}
}