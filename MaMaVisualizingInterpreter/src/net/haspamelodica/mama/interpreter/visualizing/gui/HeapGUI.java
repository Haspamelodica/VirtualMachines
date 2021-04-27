package net.haspamelodica.mama.interpreter.visualizing.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeap;
import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeapObjectContent;
import net.haspamelodica.swt.helper.gcs.GeneralGC;
import net.haspamelodica.swt.helper.swtobjectwrappers.Point;
import net.haspamelodica.swt.helper.zoomablecanvas.ZoomableCanvas;
import net.haspamelodica.swt.helper.zoomablecanvas.helper.ZoomableCanvasUserInput;

public class HeapGUI extends ZoomableCanvas
{
	private final VisualizingHeap heap;

	private Point							lastMousePos;
	private VisualizingHeapObjectContent	draggedObject;

	public HeapGUI(Composite parent, VisualizingHeap heap)
	{
		super(parent, SWT.BORDER);
		this.heap = heap;

		addZoomedRenderer(this::draw);
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
		for(VisualizingHeapObjectContent heapObject : heap.getHeapObjects())
			heapObject.draw(gc);
	}

	private void mouseDown(Event e)
	{
		if(e.button != 1)
			return;

		Point mousePos = canvasToWorldCoords(e.x, e.y);
		for(VisualizingHeapObjectContent heapObject : heap.getHeapObjects())
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
		redraw();
	}
}
