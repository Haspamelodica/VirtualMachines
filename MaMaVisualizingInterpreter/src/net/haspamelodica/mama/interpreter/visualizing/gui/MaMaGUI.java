package net.haspamelodica.mama.interpreter.visualizing.gui;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.getClientAreaOffset;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeap;
import net.haspamelodica.mama.interpreter.visualizing.stack.VisualizingStack;
import net.haspamelodica.mama.model.MaMaProgram;

public class MaMaGUI
{
	public static final Color HEAP_REF_BG = new Color(255, 200, 200);

	private static final int MARGIN = 5;

	private final GUICallback callback;

	private final Display		display;
	private Shell				shell;
	private ScrolledComposite	codeScroller;
	private CodeGUI				codeGUI;
	private Button				buttonStep;
	private HeapGUI				heapGUI;
	private ScrolledComposite	stackScroller;
	private StackGUI			stackGUI;

	public MaMaGUI(MaMaProgram program, VisualizingStack stack, VisualizingHeap heap, GUICallback callback)
	{
		this.callback = callback;
		this.display = new Display();

		setupShell(program, stack, heap);
	}

	public void run()
	{
		shell.open();
		while(!shell.isDisposed())
			if(!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}

	private void setupShell(MaMaProgram program, VisualizingStack stack, VisualizingHeap heap)
	{
		shell = new Shell();
		shell.setText("MaMa visualizer");
		shell.setLayout(new FormLayout());

		setupCodeScroller(shell, program);
		setupHeapGUI(stack, heap);
		setupStackScroller(shell, stack);
		setupButtonStep(shell);

		codeScroller.setLayoutData(formData(fa(), fa(), null, fa(buttonStep, -MARGIN)));
		buttonStep.setLayoutData(formData(fa(0, MARGIN), null, fa(heapGUI, -MARGIN), fa(100, -MARGIN)));
		heapGUI.setLayoutData(formData(fa(codeScroller), fa(), fa(stackScroller), fa(100)));
		stackScroller.setLayoutData(formData(null, fa(), fa(100), fa(100)));
	}

	private void setupCodeScroller(Composite parent, MaMaProgram program)
	{
		codeScroller = new ScrolledComposite(parent, SWT.VERTICAL);
		codeScroller.setExpandVertical(true);
		codeScroller.setAlwaysShowScrollBars(true);

		codeGUI = new CodeGUI(codeScroller, shell, program);

		codeScroller.setContent(codeGUI);
		codeScroller.setMinHeight(codeGUI.getHeight());
	}
	private void setupHeapGUI(VisualizingStack stack, VisualizingHeap heap)
	{
		heapGUI = new HeapGUI(shell, () -> getClientAreaOffset(heapGUI, stackGUI, shell), stack, heap);
		heapGUI.addTransformListener((x, y, z) -> display.asyncExec(stackGUI::redraw));
		heapGUI.addTransformListener((x, y, z) -> display.asyncExec(codeGUI::redraw));
	}

	public void heapChanged()
	{
		display.asyncExec(heapGUI::redraw);
		display.asyncExec(stackGUI::redraw);
		display.asyncExec(codeGUI::redraw);
	}

	private void setupStackScroller(Composite parent, VisualizingStack stack)
	{
		stackScroller = new ScrolledComposite(parent, SWT.VERTICAL);
		stackScroller.setExpandVertical(true);
		stackScroller.setAlwaysShowScrollBars(true);

		stackGUI = new StackGUI(stackScroller, () -> getClientAreaOffset(stackGUI, heapGUI, shell), heapGUI::drawStackRefs, stack);
		stackGUI.addListener(SWT.Move, e -> heapGUI.redraw());
		stackScroller.addListener(SWT.Move, e -> stackGUI.redraw());

		stackScroller.setContent(stackGUI);
	}
	public void stackChanged()
	{
		display.asyncExec(() -> stackScroller.setMinHeight(stackGUI.getHeight()));
		display.asyncExec(stackGUI::redraw);
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
