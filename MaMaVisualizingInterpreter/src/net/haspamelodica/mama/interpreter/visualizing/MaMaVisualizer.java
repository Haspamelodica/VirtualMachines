package net.haspamelodica.mama.interpreter.visualizing;

import net.haspamelodica.mama.interpreter.AbstractMaMaInterpreter;
import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.visualizing.gui.GUICallback;
import net.haspamelodica.mama.interpreter.visualizing.gui.MaMaGUI;
import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeap;
import net.haspamelodica.mama.interpreter.visualizing.stack.VisualizingStack;
import net.haspamelodica.mama.model.MaMaProgram;
import net.haspamelodica.mama.model.debugging.MaMaProgramForDebugging;

public class MaMaVisualizer
{
	private final VisualizingStack			stack;
	private final VisualizingHeap			heap;
	private final AbstractMaMaInterpreter	interpreter;

	private final MaMaGUI gui;

	public MaMaVisualizer(MaMaProgram program)
	{
		this(MaMaProgramForDebugging.fromMaMaProgram(program));
	}
	public MaMaVisualizer(MaMaProgramForDebugging program)
	{
		this.stack = new VisualizingStack(this::stackChanged);
		this.heap = new VisualizingHeap(this::heapChanged);
		this.interpreter = new AbstractMaMaInterpreter(program, stack, heap)
		{
			@Override
			protected void codePointerHook(int newCodePointer)
			{
				gui.codePointerChanged();
			}
			@Override
			protected void globalPointerHook(HeapObject newGlobalPointer)
			{
				//TODO visualize global pointer
			}
			@Override
			protected void framePointerHook(int newFramePointer)
			{
				//TODO visualize frame pointer
			}
		};
		this.gui = new MaMaGUI(program, stack, heap, interpreter::getCodePointer, new GUICallback()
		{
			@Override
			public boolean step()
			{
				return interpreter.step();
			}
		});
	}

	public void run()
	{
		gui.run();
	}

	public void shutdownGUI()
	{
		gui.shutdown();
	}

	//TODO add different listeners for different events to highlight what just happened
	private void stackChanged()
	{
		gui.stackChanged();
	}
	private void heapChanged()
	{
		gui.heapChanged();
	}
}
