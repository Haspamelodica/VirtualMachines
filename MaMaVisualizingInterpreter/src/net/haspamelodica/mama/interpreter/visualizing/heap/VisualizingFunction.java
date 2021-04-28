package net.haspamelodica.mama.interpreter.visualizing.heap;

import java.util.List;

import net.haspamelodica.mama.interpreter.heap.Function;
import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.CodePointerHOE;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.DrawableHeapObjectElement;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.HeapReferenceHOE;

public class VisualizingFunction extends VisualizingHeapObjectContent implements Function
{
	private final int					codePointer;
	private final VisualizingHeapObject	arguments;
	private final VisualizingHeapObject	gp;

	public VisualizingFunction(int codePointer, HeapObject arguments, HeapObject gp)
	{
		this.codePointer = codePointer;
		//TODO can we do this cleaner?
		this.arguments = (VisualizingHeapObject) arguments;
		this.gp = (VisualizingHeapObject) gp;
	}

	@Override
	public int getCodePointer()
	{
		return codePointer;
	}
	@Override
	public VisualizingHeapObject getArguments()
	{
		return arguments;
	}
	@Override
	public VisualizingHeapObject getGp()
	{
		return gp;
	}

	@Override
	protected List<DrawableHeapObjectElement> getElements()
	{
		return List.of(new CodePointerHOE(codePointer), new HeapReferenceHOE(arguments), new HeapReferenceHOE(gp));
	}
	@Override
	protected int getElementCount()
	{
		return 3;
	}
}
