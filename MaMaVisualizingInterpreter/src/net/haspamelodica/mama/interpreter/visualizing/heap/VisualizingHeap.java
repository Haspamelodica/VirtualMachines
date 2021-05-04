package net.haspamelodica.mama.interpreter.visualizing.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.haspamelodica.mama.interpreter.heap.BasicValue;
import net.haspamelodica.mama.interpreter.heap.Closure;
import net.haspamelodica.mama.interpreter.heap.Function;
import net.haspamelodica.mama.interpreter.heap.Heap;
import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.heap.HeapObjectContent;
import net.haspamelodica.mama.interpreter.heap.HeapReachabilityProvider;
import net.haspamelodica.mama.interpreter.heap.Vector;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.DrawableHeapObjectElement.Type;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.HeapReferenceHOE;

public class VisualizingHeap implements Heap
{
	private static final int HEAP_OBJECT_DISTANCE = 50;

	private final List<VisualizingHeapObject>	heapObjectsM;
	private final List<VisualizingHeapObject>	heapObjectsU;

	private final Set<HeapReachabilityProvider> externalReachabilityProviders;

	private final Runnable observer;

	private int nextY;

	public VisualizingHeap(Runnable observer)
	{
		this.heapObjectsM = new ArrayList<>();
		this.heapObjectsU = Collections.unmodifiableList(heapObjectsM);
		this.externalReachabilityProviders = new HashSet<>();
		this.observer = observer;
	}

	@Override
	public void clear()
	{
		heapObjectsM.clear();
		observer.run();
	}

	@Override
	public BasicValue createBasicContent(int value)
	{
		return new VisualizingBasicValue(value);
	}
	@Override
	public Closure createClosureContent(int codePointer, HeapObject globalPointer)
	{
		return new VisualizingClosure(codePointer, globalPointer);
	}
	@Override
	public Function createFunctionContent(int codePointer, HeapObject arguments, HeapObject globalPointer)
	{
		return new VisualizingFunction(codePointer, arguments, globalPointer);
	}
	@Override
	public Vector createVectorContent(List<HeapObject> referencedObjects)
	{
		return new VisualizingVector(referencedObjects);
	}

	@Override
	public HeapObject createObject(HeapObjectContent content)
	{
		VisualizingHeapObject newObject = new VisualizingHeapObject(content, 0, nextY += HEAP_OBJECT_DISTANCE, observer);
		heapObjectsM.add(newObject);
		observer.run();
		return newObject;
	}

	@Override
	public void addExternalReachabilityProvider(HeapReachabilityProvider reachabilityProvider)
	{
		externalReachabilityProviders.add(reachabilityProvider);
	}
	@Override
	public void gc()
	{
		Set<VisualizingHeapObject> externallyReachableObjects = externalReachabilityProviders.stream()
				.flatMap(HeapReachabilityProvider::getReachableObjects)
				.filter(Objects::nonNull)
				.map(o -> (VisualizingHeapObject) o)
				.collect(Collectors.toUnmodifiableSet());

		Set<VisualizingHeapObject> lastReachableObjects = externallyReachableObjects;
		for(;;)
		{
			Set<VisualizingHeapObject> newReachableObjects = Stream.concat(
					externallyReachableObjects.stream(),
					lastReachableObjects.stream()
							.map(VisualizingHeapObject::getContent)
							.map(VisualizingHeapObjectContent::getElements)
							.flatMap(List::stream)
							.filter(e -> e.getType() == Type.HEAP_REFERENCE)
							.map(e -> (HeapReferenceHOE) e)
							.map(HeapReferenceHOE::getReferencedObject)
							.filter(Objects::nonNull))
					.collect(Collectors.toUnmodifiableSet());

			if(newReachableObjects.equals(lastReachableObjects))
				break;
			lastReachableObjects = newReachableObjects;
		}

		heapObjectsM.retainAll(lastReachableObjects);
		observer.run();
	}

	public List<VisualizingHeapObject> getHeapObjects()
	{
		return heapObjectsU;
	}
}
