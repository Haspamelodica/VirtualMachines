package net.haspamelodica.mama.interpreter.visualizing.stack.elements;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawTextCentered;

import org.eclipse.swt.graphics.GC;

public class BasicValueSE implements StackElement
{
	private final int value;

	public BasicValueSE(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public void draw(GC gc, int x, int y, int width, int height)
	{
		drawTextCentered(gc, Integer.toString(value), x, y, width, height);
	}

	@Override
	public Type getType()
	{
		return Type.BASIC_VALUE;
	}
}