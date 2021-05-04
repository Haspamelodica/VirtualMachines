package net.haspamelodica.mama.interpreter.visualizing.stack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import net.haspamelodica.mama.interpreter.exceptions.StackException;
import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.stack.Stack;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.BasicValueSE;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.HeapReferenceSE;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.StackElement;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.StackElement.Type;

public class VisualizingStack implements Stack
{
	private final LinkedList<StackElement>	elementsM;
	private final List<StackElement>		elementsU;

	private final Runnable observer;

	public VisualizingStack(Runnable observer)
	{
		LinkedList<StackElement> elementsMLocal = new LinkedList<>();
		this.elementsM = elementsMLocal;
		this.elementsU = Collections.unmodifiableList(elementsMLocal);
		this.observer = observer;
	}

	@Override
	public void clear()
	{
		elementsM.clear();
		observer.run();
	}
	@Override
	public void pushBasic(int value)
	{
		push(new BasicValueSE(value));
	}
	@Override
	public void pushHeapReference(HeapObject referencedObject)
	{
		push(new HeapReferenceSE(referencedObject));
	}
	@Override
	public int popBasic()
	{
		return pop().checkBasicValue().getValue();
	}
	@Override
	public HeapObject popHeapReference()
	{
		return pop().checkHeapReference().getReferencedObject();
	}
	@Override
	public HeapObject getHeapReferenceRelative(int offsetFromStackPointer)
	{
		return getRelative(offsetFromStackPointer).checkHeapReference().getReferencedObject();
	}


	public List<StackElement> getElements()
	{
		return elementsU;
	}

	private void push(StackElement value)
	{
		elementsM.push(value);
		observer.run();
	}
	private StackElement pop()
	{
		if(elementsM.isEmpty())
			throw new StackException("Popping from empty stack");
		StackElement popped = elementsM.pop();
		observer.run();
		return popped;
	}
	@Override
	public int getStackPointer()
	{
		return elementsM.size() - 1;
	}
	private StackElement getRelative(int offsetFromStackPointer)
	{
		if(offsetFromStackPointer < 0 || offsetFromStackPointer >= elementsM.size())
			throw new StackException("Offset out of bounds: " + offsetFromStackPointer);
		//pop and push operate on the first element
		return elementsM.get(offsetFromStackPointer);
	}
	@Override
	public void popMultiple(int numberOfValuesToPop)
	{
		//pop and push operate on the first element
		if(numberOfValuesToPop < 0)
			throw new StackException("Popping a negative amount of values: " + numberOfValuesToPop);
		else if(numberOfValuesToPop > elementsM.size())
			throw new StackException("Popping more values than are on the stack: " + numberOfValuesToPop);
		elementsM.subList(0, numberOfValuesToPop).clear();
		observer.run();
	}

	@Override
	public Stream<HeapObject> getReachableObjects()
	{
		return getElements().stream().filter(e -> e.getType() == Type.HEAP_REFERENCE)
				.map(e -> (HeapReferenceSE) e).map(HeapReferenceSE::getReferencedObject);
	}
}
