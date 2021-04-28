package net.haspamelodica.mama.interpreter.visualizing.gui;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.getClientAreaOffset;

import java.util.function.IntSupplier;

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
import net.haspamelodica.mama.model.debugging.MaMaProgramForDebugging;

public class MaMaGUI
{
	public static final Color	HEAP_REF_BG	= new Color(255, 200, 200);
	public static final Color	HEAP_REF_FG	= new Color(255, 0, 0);
	public static final Color	CODE_REF_BG	= new Color(200, 200, 255);
	public static final Color	CODE_REF_FG	= new Color(0, 0, 255);

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

	public MaMaGUI(MaMaProgramForDebugging program, VisualizingStack stack, VisualizingHeap heap, IntSupplier getCurrentCodePointer,
			GUICallback callback)
	{
		this.callback = callback;
		this.display = Display.getCurrent() != null ? Display.getCurrent() : new Display();

		setupShell(program, stack, heap, getCurrentCodePointer);
	}

	public void run()
	{
		shell.open();
		while(!shell.isDisposed())
			if(!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}

	private void setupShell(MaMaProgramForDebugging program, VisualizingStack stack, VisualizingHeap heap, IntSupplier getCurrentCodePointer)
	{
		shell = new Shell();
		shell.setText("MaMa visualizer");
		shell.setLayout(new FormLayout());

		setupCodeScroller(shell, program, getCurrentCodePointer);
		setupHeapGUI(stack, heap);
		setupStackScroller(shell, stack);
		setupButtonStep(shell);

		codeScroller.setLayoutData(formData(fa(), fa(), null, fa(buttonStep, -MARGIN)));
		buttonStep.setLayoutData(formData(fa(0, MARGIN), null, fa(heapGUI, -MARGIN), fa(100, -MARGIN)));
		heapGUI.setLayoutData(formData(fa(codeScroller), fa(), fa(stackScroller), fa(100)));
		stackScroller.setLayoutData(formData(null, fa(), fa(100), fa(100)));
	}

	private void setupCodeScroller(Composite parent, MaMaProgramForDebugging program, IntSupplier getCurrentCodePointer)
	{
		codeScroller = new ScrolledComposite(parent, SWT.VERTICAL);
		codeScroller.setExpandVertical(true);
		codeScroller.setAlwaysShowScrollBars(true);

		codeGUI = new CodeGUI(codeScroller, () -> getClientAreaOffset(codeGUI, heapGUI, shell), gc -> heapGUI.drawCodeRefs(gc),
				program, getCurrentCodePointer);
		codeGUI.addListener(SWT.Move, e -> heapGUI.redraw());
		codeGUI.addListener(SWT.Move, e -> codeGUI.redraw());
		codeScroller.addListener(SWT.Move, e -> codeGUI.redraw());

		codeScroller.setContent(codeGUI);
		codeScroller.setMinHeight(codeGUI.getHeight());
	}
	public void codePointerChanged()
	{
		display.asyncExec(codeGUI::redraw);
	}
	private void setupHeapGUI(VisualizingStack stack, VisualizingHeap heap)
	{
		heapGUI = new HeapGUI(shell, () -> getClientAreaOffset(heapGUI, stackGUI, shell),
				() -> getClientAreaOffset(heapGUI, codeGUI, shell), stack, heap);
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
		stackGUI.addListener(SWT.Move, e -> stackGUI.redraw());
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
