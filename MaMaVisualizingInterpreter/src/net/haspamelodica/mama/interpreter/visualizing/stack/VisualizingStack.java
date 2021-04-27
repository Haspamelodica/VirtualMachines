package net.haspamelodica.mama.interpreter.visualizing.stack;

import java.util.Deque;
import java.util.LinkedList;

import net.haspamelodica.mama.interpreter.exceptions.StackException;
import net.haspamelodica.mama.interpreter.heap.HeapObjectRef;
import net.haspamelodica.mama.interpreter.stack.Stack;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.BasicValueSE;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.HeapReferenceSE;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.StackElement;

public class VisualizingStack implements Stack
{
	private final Deque<StackElement> elements;

	private final Runnable observer;

	public VisualizingStack(Runnable observer)
	{
		this.elements = new LinkedList<>();
		this.observer = observer;
	}

	@Override
	public void clear()
	{
		elements.clear();
		observer.run();
	}
	@Override
	public void pushBasic(int value)
	{
		push(new BasicValueSE(value));
	}
	@Override
	public void pushHeapReference(HeapObjectRef referencedObject)
	{
		push(new HeapReferenceSE(referencedObject));
	}
	@Override
	public int popBasic()
	{
		return pop().checkBasicValue().getValue();
	}
	@Override
	public HeapObjectRef popHeapReference()
	{
		return pop().checkHeapReference().getReferencedObject();
	}

	private void push(StackElement value)
	{
		elements.push(value);
		observer.run();
	}
	private StackElement pop()
	{
		if(elements.isEmpty())
			throw new StackException("Popping from empty stack");
		StackElement popped = elements.pop();
		observer.run();
		return popped;
	}
}
