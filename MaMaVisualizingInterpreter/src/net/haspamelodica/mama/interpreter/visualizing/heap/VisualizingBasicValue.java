package net.haspamelodica.mama.interpreter.visualizing.heap;

import java.util.List;

import net.haspamelodica.mama.interpreter.heap.BasicValue;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.BasicValueHOE;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.DrawableHeapObjectElement;

public class VisualizingBasicValue extends VisualizingHeapObjectContent implements BasicValue
{
	private final int value;

	public VisualizingBasicValue(int value)
	{
		this.value = value;
	}

	@Override
	public int getValue()
	{
		return value;
	}

	@Override
	protected List<DrawableHeapObjectElement> getElements()
	{
		return List.of(new BasicValueHOE(getValue()));
	}
	@Override
	protected int getElementCount()
	{
		return 1;
	}
}
