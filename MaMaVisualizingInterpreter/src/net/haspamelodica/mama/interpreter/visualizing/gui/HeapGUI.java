package net.haspamelodica.mama.interpreter.visualizing.gui;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawHeapReferenceArrow;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeap;
import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeapObject;
import net.haspamelodica.mama.interpreter.visualizing.stack.VisualizingStack;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.HeapReferenceSE;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.StackElement;
import net.haspamelodica.mama.interpreter.visualizing.stack.elements.StackElement.Type;
import net.haspamelodica.swt.helper.gcs.GeneralGC;
import net.haspamelodica.swt.helper.gcs.SWTGC;
import net.haspamelodica.swt.helper.swtobjectwrappers.Point;
import net.haspamelodica.swt.helper.swtobjectwrappers.Rectangle;
import net.haspamelodica.swt.helper.zoomablecanvas.ZoomableCanvas;
import net.haspamelodica.swt.helper.zoomablecanvas.helper.ZoomableCanvasUserInput;

public class HeapGUI extends ZoomableCanvas
{
	private final VisualizingStack	stack;
	private final VisualizingHeap	heap;

	private final Supplier<org.eclipse.swt.graphics.Point>	getStackOffset;
	private final Supplier<org.eclipse.swt.graphics.Point>	getCodeOffset;

	private Point					lastMousePos;
	private VisualizingHeapObject	draggedObject;

	public HeapGUI(Composite parent, Supplier<org.eclipse.swt.graphics.Point> getStackOffset,
			Supplier<org.eclipse.swt.graphics.Point> getCodeOffset, VisualizingStack stack, VisualizingHeap heap)
	{
		super(parent, SWT.BORDER);
		this.stack = stack;
		this.heap = heap;
		this.getStackOffset = getStackOffset;
		this.getCodeOffset = getCodeOffset;
		addZoomedRenderer(this::draw);
		addPaintListener(e -> drawCrossrefs(e.gc));
		ZoomableCanvasUserInput userInput = new ZoomableCanvasUserInput(this);
		userInput.buttonDrag = 3;
		userInput.buttonZoom = 2;
		userInput.enableUserInput();
		addListener(SWT.MouseDown, this::mouseDown);
		addListener(SWT.MouseUp, this::mouseUp);
		addListener(SWT.MouseMove, this::mouseMoved);
	}
	private void draw(GeneralGC gc)
	{
		gc.setLineWidth(0.5);
		for(VisualizingHeapObject heapObject : heap.getHeapObjects())
			heapObject.draw(gc);
	}
	private void drawCrossrefs(GC swtgc)
	{
		GeneralGC gc = new SWTGC(swtgc);
		drawStackRefs(gc);
		drawCodeRefs(gc);
		gc.disposeThisLayer();
	}
	public void drawStackRefs(GeneralGC gc)
	{
		Point stackOffset = new Point(getStackOffset.get());
		List<StackElement> elements = stack.getElements();
		for(int i = 0; i < elements.size(); i ++)
		{
			StackElement e = elements.get(i);
			if(e.getType() == Type.HEAP_REFERENCE)
			{
				VisualizingHeapObject referencedObject = ((HeapReferenceSE) e).getReferencedObject();
				Rectangle referencedObjectBounds = worldToCanvasCoords(referencedObject.getBounds());
				drawHeapReferenceArrow(gc,
						new Rectangle(stackOffset.x, stackOffset.y + StackGUI.ELEMENT_HEIGHT * i, StackGUI.WIDTH, StackGUI.ELEMENT_HEIGHT),
						referencedObjectBounds);
			}
		}
	}
	public void drawCodeRefs(GeneralGC gc)
	{
		Point codeOffset = new Point(getCodeOffset.get());
		heap.getHeapObjects().forEach(o -> o.drawCodeRefs(gc, codeOffset, this::worldToCanvasCoords));
	}

	private void mouseDown(Event e)
	{
		if(e.button != 1)
			return;

		Point mousePos = canvasToWorldCoords(e.x, e.y);
		for(VisualizingHeapObject heapObject : heap.getHeapObjects())
			if(heapObject.getBounds().contains(mousePos))
				//don't break loop to pick the last / highest one
				draggedObject = heapObject;
		lastMousePos = mousePos;
	}
	private void mouseUp(Event e)
	{
		if(e.button != 1)
			return;

		draggedObject = null;
	}
	private void mouseMoved(Event e)
	{
		if(draggedObject == null)
			return;

		Point mousePos = canvasToWorldCoords(e.x, e.y);
		draggedObject.moveBy(mousePos.x - lastMousePos.x, mousePos.y - lastMousePos.y);
		lastMousePos = mousePos;
	}
}
