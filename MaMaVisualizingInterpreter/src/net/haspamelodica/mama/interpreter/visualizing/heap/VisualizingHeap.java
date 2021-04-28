package net.haspamelodica.mama.interpreter.visualizing.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.haspamelodica.mama.interpreter.heap.BasicValue;
import net.haspamelodica.mama.interpreter.heap.Heap;
import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.heap.HeapObjectContent;
import net.haspamelodica.mama.interpreter.heap.Vector;

public class VisualizingHeap implements Heap
{
	private static final int HEAP_OBJECT_DISTANCE = 50;

	private final List<VisualizingHeapObject>	heapObjectsM;
	private final List<VisualizingHeapObject>	heapObjectsU;

	private final Runnable observer;

	private int nextY;

	public VisualizingHeap(Runnable observer)
	{
		this.heapObjectsM = new ArrayList<>();
		this.heapObjectsU = Collections.unmodifiableList(heapObjectsM);
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
	public Vector createVectorContent(List<HeapObject> referencedObjects)
	{
		return new VisualizingVector(referencedObjects);
	}
	@Override
	public HeapObject createObject(HeapObjectContent content)
	{
		VisualizingHeapObject newObject = new VisualizingHeapObject(content, 0, nextY += HEAP_OBJECT_DISTANCE);
		heapObjectsM.add(newObject);
		observer.run();
		return newObject;
	}

	public List<VisualizingHeapObject> getHeapObjects()
	{
		return heapObjectsU;
	}
}
