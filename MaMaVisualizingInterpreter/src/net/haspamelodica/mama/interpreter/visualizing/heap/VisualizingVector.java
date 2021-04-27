package net.haspamelodica.mama.interpreter.visualizing.heap;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.haspamelodica.mama.interpreter.heap.HeapObjectRef;
import net.haspamelodica.mama.interpreter.heap.Vector;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.BasicValueHOE;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.DrawableHeapObjectElement;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.HeapReferenceHOE;

public class VisualizingVector extends VisualizingHeapObjectContent implements Vector
{
	private final List<HeapObjectRef> referencedObjects;

	public VisualizingVector(List<HeapObjectRef> referencedObjects, double x, double y)
	{
		super(x, y);
		this.referencedObjects = List.copyOf(referencedObjects);
	}

	@Override
	public int getLength()
	{
		return referencedObjects.size();
	}
	@Override
	public HeapObjectRef get(int i)
	{
		return referencedObjects.get(i);
	}

	@Override
	protected List<DrawableHeapObjectElement> getElements()
	{
		return Stream.concat(Stream.of(new BasicValueHOE(referencedObjects.size())), referencedObjects
				.stream()
				.map(HeapObjectRef::getContent)
				//TODO can we do this cleaner?
				.map(c -> (VisualizingHeapObjectContent) c)
				.map(HeapReferenceHOE::new))
				.collect(Collectors.toList());
	}
	@Override
	protected int getElementCount()
	{
		return 1 + referencedObjects.size();
	}
}
