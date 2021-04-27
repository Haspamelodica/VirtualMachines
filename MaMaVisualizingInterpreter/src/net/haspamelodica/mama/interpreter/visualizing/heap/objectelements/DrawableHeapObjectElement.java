package net.haspamelodica.mama.interpreter.visualizing.heap.objectelements;

import net.haspamelodica.swt.helper.gcs.GeneralGC;

public interface DrawableHeapObjectElement
{
	public void draw(GeneralGC gc, double x, double y, double width, double height);
}