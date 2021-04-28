package net.haspamelodica.mama.interpreter.visualizing;

import net.haspamelodica.mama.interpreter.AbstractMaMaInterpreter;
import net.haspamelodica.mama.interpreter.visualizing.gui.GUICallback;
import net.haspamelodica.mama.interpreter.visualizing.gui.MaMaGUI;
import net.haspamelodica.mama.interpreter.visualizing.heap.VisualizingHeap;
import net.haspamelodica.mama.interpreter.visualizing.stack.VisualizingStack;
import net.haspamelodica.mama.model.MaMaProgram;

public class MaMaVisualizer
{
	private final VisualizingStack			stack;
	private final VisualizingHeap			heap;
	private final AbstractMaMaInterpreter	interpreter;

	private final MaMaGUI gui;

	public MaMaVisualizer(MaMaProgram program)
	{
		this.stack = new VisualizingStack(this::stackChanged);
		this.heap = new VisualizingHeap(this::heapChanged);
		this.interpreter = new AbstractMaMaInterpreter(program, stack, heap)
		{};
		this.gui = new MaMaGUI(program, stack, heap, new GUICallback()
		{
			@Override
			public void step()
			{
				interpreter.step();
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
