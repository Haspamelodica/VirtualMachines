package net.haspamelodica.mama.interpreter.visualizing.heap;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.heap.Vector;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.BasicValueHOE;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.DrawableHeapObjectElement;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.HeapReferenceHOE;

public class VisualizingVector extends VisualizingHeapObjectContent implements Vector
{
	private final List<HeapObject> referencedObjects;

	public VisualizingVector(List<HeapObject> referencedObjects)
	{
		this.referencedObjects = List.copyOf(referencedObjects);
	}

	@Override
	public int getLength()
	{
		return referencedObjects.size();
	}
	@Override
	public HeapObject get(int i)
	{
		return referencedObjects.get(i);
	}

	@Override
	protected List<DrawableHeapObjectElement> getElements()
	{
		return Stream.concat(Stream.of(new BasicValueHOE(referencedObjects.size())), referencedObjects
				.stream()
				//TODO can we do this cleaner?
				.map(c -> (VisualizingHeapObject) c)
				.map(HeapReferenceHOE::new))
				.collect(Collectors.toList());
	}
	@Override
	protected int getElementCount()
	{
		return 1 + referencedObjects.size();
	}
}
