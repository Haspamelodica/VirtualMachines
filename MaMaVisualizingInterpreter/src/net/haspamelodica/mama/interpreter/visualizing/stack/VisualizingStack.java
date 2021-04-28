package net.haspamelodica.mama.interpreter.visualizing.stack;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import net.haspamelodica.mama.interpreter.exceptions.StackException;
import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.stack.Stack;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.BasicValueSE;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.HeapReferenceSE;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.StackElement;

public class VisualizingStack implements Stack
{
	private final Deque<StackElement>	elementsM;
	private final List<StackElement>	elementsU;

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
}
