package net.haspamelodica.mama.interpreter.visualizing.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeap;

public class MaMaGUI
{
	private static final int MARGIN = 5;

	private final GUICallback callback;

	private final Display		display;
	private Shell				shell;
	private HeapGUI				heapGUI;
	private ScrolledComposite	stackScroller;
	private Canvas				stackCanvas;
	private Button				buttonStep;

	public MaMaGUI(VisualizingHeap heap, GUICallback callback)
	{
		this.callback = callback;
		this.display = new Display();

		setupShell(heap);
	}

	public void run()
	{
		shell.open();
		while(!shell.isDisposed())
			if(!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}

	private void setupShell(VisualizingHeap heap)
	{
		shell = new Shell();
		shell.setText("MaMa visualizer");
		shell.setLayout(new FormLayout());

		setupHeapCanvas(shell, heap);
		setupStackScroller(shell);
		setupButtonStep(shell);

		heapGUI.setLayoutData(formData(fa(), fa(), fa(stackScroller), fa(100)));
		stackScroller.setLayoutData(formData(null, fa(0), fa(100), fa(buttonStep, -MARGIN)));
		buttonStep.setLayoutData(formData(fa(heapGUI, MARGIN), null, fa(100, -MARGIN), fa(100, -MARGIN)));
	}

	private void setupStackScroller(Composite parent)
	{
		stackScroller = new ScrolledComposite(parent, SWT.VERTICAL | SWT.BORDER);

		stackCanvas = new Canvas(stackScroller, SWT.DOUBLE_BUFFERED);
		stackCanvas.setSize(200, SWT.DEFAULT);

		stackScroller.setContent(stackCanvas);
		stackScroller.setExpandVertical(true);
		stackScroller.setAlwaysShowScrollBars(true);
		stackScroller.setMinHeight(500);
	}
	private void setupHeapCanvas(Composite parent, VisualizingHeap heap)
	{
		heapGUI = new HeapGUI(parent, heap);
	}
	public void redrawHeap()
	{
		display.asyncExec(heapGUI::redraw);
	}

	private void setupButtonStep(Composite parent)
	{
		buttonStep = new Button(parent, SWT.PUSH);
		buttonStep.setText("Step");
		buttonStep.addListener(SWT.Selection, e -> callback.step());
	}

	private FormData formData(FormAttachment left, FormAttachment top, FormAttachment right, FormAttachment bottom)
	{
		FormData fd = new FormData();
		fd.left = left;
		fd.top = top;
		fd.right = right;
		fd.bottom = bottom;
		return fd;
	}
	private FormAttachment fa()
	{
		return new FormAttachment();
	}
	private FormAttachment fa(int percent)
	{
		return new FormAttachment(percent);
	}
	private FormAttachment fa(int percent, int offset)
	{
		return new FormAttachment(percent, offset);
	}
	private FormAttachment fa(Control control)
	{
		return new FormAttachment(control);
	}
	private FormAttachment fa(Control control, int offset)
	{
		return new FormAttachment(control, offset);
	}

	public void shutdown()
	{
		display.syncExec(() -> shell.dispose());
	}
}
