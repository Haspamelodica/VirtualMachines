package net.haspamelodica.mama.interpreter.visualizing.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.haspamelodica.mama.interpreter.heap.BasicValue;
import net.haspamelodica.mama.interpreter.heap.Heap;
import net.haspamelodica.mama.interpreter.heap.HeapObjectRef;
import net.haspamelodica.mama.interpreter.heap.Vector;

public class VisualizingHeap implements Heap
{
	private static final int HEAP_OBJECT_DISTANCE = 50;

	private final List<VisualizingHeapObjectContent>	heapObjectsM;
	private final List<VisualizingHeapObjectContent>	heapObjectsU;

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
		VisualizingBasicValue newObject = new VisualizingBasicValue(value, 0, nextY += HEAP_OBJECT_DISTANCE);
		heapObjectsM.add(newObject);
		observer.run();
		return newObject;
	}
	@Override
	public Vector createVectorContent(List<HeapObjectRef> referencedObjects)
	{
		VisualizingVector newObject = new VisualizingVector(referencedObjects, 0, nextY += HEAP_OBJECT_DISTANCE);
		heapObjectsM.add(newObject);
		observer.run();
		return newObject;
	}

	public List<VisualizingHeapObjectContent> getHeapObjects()
	{
		return heapObjectsU;
	}
}
