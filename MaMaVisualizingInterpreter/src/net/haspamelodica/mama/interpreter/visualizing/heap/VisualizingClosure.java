package net.haspamelodica.mama.interpreter.visualizing.heap;

import java.util.List;

import net.haspamelodica.mama.interpreter.heap.Closure;
import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.CodePointerHOE;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.DrawableHeapObjectElement;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.HeapReferenceHOE;

public class VisualizingClosure extends VisualizingHeapObjectContent implements Closure
{
	private final int					codePointer;
	private final VisualizingHeapObject	globalPointer;

	public VisualizingClosure(int codePointer, HeapObject globalPointer)
	{
		this.codePointer = codePointer;
		//TODO can we do this cleaner?
		this.globalPointer = (VisualizingHeapObject) globalPointer;
	}

	@Override
	public int getCodePointer()
	{
		return codePointer;
	}
	@Override
	public VisualizingHeapObject getGlobalPointer()
	{
		return globalPointer;
	}

	@Override
	protected List<DrawableHeapObjectElement> getElements()
	{
		return List.of(new CodePointerHOE(codePointer), new HeapReferenceHOE(globalPointer));
	}
	@Override
	protected int getElementCount()
	{
		return 3;
	}
}
