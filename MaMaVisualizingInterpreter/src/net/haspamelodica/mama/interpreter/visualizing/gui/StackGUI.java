package net.haspamelodica.mama.interpreter.visualizing.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import net.haspamelodica.mama.interpreter.visualizing.stack.VisualizingStack;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.StackElement;

public class StackGUI extends Canvas
{
	public static final int	WIDTH			= 200;
	public static final int	ELEMENT_HEIGHT	= 50;

	private final VisualizingStack stack;

	public StackGUI(Composite parent, Composite commonParent, VisualizingStack stack)
	{
		super(parent, SWT.DOUBLE_BUFFERED);
		this.stack = stack;
		setSize(WIDTH, SWT.DEFAULT);
		addPaintListener(e -> draw(e.gc));
	}

	public int getHeight()
	{
		return stack.getElements().size() * ELEMENT_HEIGHT;
	}

	private void draw(GC gc)
	{
		int y = 0;
		for(StackElement e : stack.getElements())
		{
			e.draw(gc, 0, y, WIDTH, ELEMENT_HEIGHT);
			gc.drawLine(0, y, WIDTH, y);
			y += ELEMENT_HEIGHT;
		}
		gc.drawLine(0, y, WIDTH, y);
	}
}
