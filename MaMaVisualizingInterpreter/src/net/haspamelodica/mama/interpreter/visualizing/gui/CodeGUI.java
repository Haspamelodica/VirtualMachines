package net.haspamelodica.mama.interpreter.visualizing.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import net.haspamelodica.mama.model.MaMaProgram;

public class CodeGUI extends Canvas
{
	private static final int WIDTH = 200;

	private final MaMaProgram program;

	public CodeGUI(Composite parent, Composite commonParent, MaMaProgram program)
	{
		super(parent, SWT.DOUBLE_BUFFERED);
		this.program = program;
		setSize(WIDTH, SWT.DEFAULT);
	}

	public int getHeight()
	{
		//TODO
		return 500;
	}
}
