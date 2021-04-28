package net.haspamelodica.mama.interpreter.visualizing.gui;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import net.haspamelodica.mama.interpreter.visualizing.stack.VisualizingStack;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.StackElement;
import net.haspamelodica.swt.helper.gcs.GeneralGC;
import net.haspamelodica.swt.helper.gcs.SWTGC;
import net.haspamelodica.swt.helper.gcs.TranslatedGC;

public class StackGUI extends Canvas
{
	public static final int	WIDTH			= 200;
	public static final int	ELEMENT_HEIGHT	= 50;

	private final VisualizingStack stack;

	private final Supplier<org.eclipse.swt.graphics.Point>	getHeapOffset;
	private final Consumer<GeneralGC>						drawHeapCrossrefs;

	public StackGUI(Composite parent, Supplier<org.eclipse.swt.graphics.Point> getHeapOffset, Consumer<GeneralGC> drawHeapCrossrefs,
			VisualizingStack stack)
	{
		super(parent, SWT.DOUBLE_BUFFERED);
		this.stack = stack;
		this.getHeapOffset = getHeapOffset;
		this.drawHeapCrossrefs = drawHeapCrossrefs;
		setSize(WIDTH, SWT.DEFAULT);
		addPaintListener(e -> draw(e.gc));
	}

	public int getHeight()
	{
		return stack.getElements().size() * ELEMENT_HEIGHT;
	}

	private void draw(GC swtgc)
	{
		int y = 0;
		for(StackElement e : stack.getElements())
		{
			e.draw(swtgc, 0, y, WIDTH, ELEMENT_HEIGHT);
			swtgc.drawLine(0, y, WIDTH, y);
			y += ELEMENT_HEIGHT;
		}
		swtgc.drawLine(0, y, WIDTH, y);

		GeneralGC gc = new SWTGC(swtgc);
		org.eclipse.swt.graphics.Point heapOffset = getHeapOffset.get();
		TranslatedGC tgc = new TranslatedGC(gc, heapOffset.x, heapOffset.y);
		drawHeapCrossrefs.accept(tgc);
		tgc.disposeThisLayer();
		gc.disposeThisLayer();
	}
}
