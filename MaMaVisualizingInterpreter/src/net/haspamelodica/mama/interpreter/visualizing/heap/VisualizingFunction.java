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
	private final VisualizingHeapObject	argumentPointer;
	private final VisualizingHeapObject	globalPointer;

	public VisualizingFunction(int codePointer, HeapObject argumentPointer, HeapObject globalPointer)
	{
		this.codePointer = codePointer;
		//TODO can we do this cleaner?
		this.argumentPointer = (VisualizingHeapObject) argumentPointer;
		this.globalPointer = (VisualizingHeapObject) globalPointer;
	}

	@Override
	public int getCodePointer()
	{
		return codePointer;
	}
	@Override
	public VisualizingHeapObject getArgumentPointer()
	{
		return argumentPointer;
	}
	@Override
	public VisualizingHeapObject getGlobalPointer()
	{
		return globalPointer;
	}

	@Override
	protected List<DrawableHeapObjectElement> getElements()
	{
		return List.of(new CodePointerHOE(codePointer), new HeapReferenceHOE(argumentPointer), new HeapReferenceHOE(globalPointer));
	}
	@Override
	protected int getElementCount()
	{
		return 3;
	}
}
