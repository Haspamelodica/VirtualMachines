package net.haspamelodica.mama.interpreter.visualizing.heap;

import java.util.List;

import net.haspamelodica.mama.interpreter.heap.HeapObjectContent;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.DrawableHeapObjectElement;

public abstract class VisualizingHeapObjectContent implements HeapObjectContent
{
	protected abstract List<DrawableHeapObjectElement> getElements();
	protected abstract int getElementCount();
}
