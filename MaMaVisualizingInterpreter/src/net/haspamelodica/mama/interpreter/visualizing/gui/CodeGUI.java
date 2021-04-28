package net.haspamelodica.mama.interpreter.visualizing.gui;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawTextCentered;

import java.util.List;
import java.util.function.IntSupplier;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import net.haspamelodica.mama.model.Instruction;
import net.haspamelodica.mama.model.MaMaProgram;

public class CodeGUI extends Canvas
{
	public static final int		LEFT_MARGIN			= 10;
	public static final int		WIDTH				= 200;
	public static final int		ELEMENT_HEIGHT		= 50;
	private static final Color	CURRENT_INSTR_BG	= new Color(200, 255, 200);

	private final MaMaProgram	program;
	private final IntSupplier	getCurrentCodePointer;

	public CodeGUI(Composite parent, Composite commonParent, MaMaProgram program, IntSupplier getCurrentCodePointer)
	{
		super(parent, SWT.DOUBLE_BUFFERED);
		this.program = program;
		this.getCurrentCodePointer = getCurrentCodePointer;
		setSize(WIDTH, SWT.DEFAULT);
		addPaintListener(e -> draw(e.gc));
	}

	private void draw(GC swtgc)
	{
		int currentCodePointer = getCurrentCodePointer.getAsInt();
		int y = 0;
		List<Instruction> instructions = program.getInstructions();
		for(int i = 0; i < instructions.size(); i ++)
		{
			if(i == currentCodePointer)
			{
				swtgc.setBackground(CURRENT_INSTR_BG);
				swtgc.fillRectangle(0, y, WIDTH, ELEMENT_HEIGHT);
			}
			Instruction instruction = instructions.get(i);
			drawTextCentered(swtgc, instruction.toString(), LEFT_MARGIN, y, -1, ELEMENT_HEIGHT);
			swtgc.drawLine(0, y, WIDTH, y);
			y += ELEMENT_HEIGHT;
		}
		swtgc.drawLine(0, y, WIDTH, y);

		//TODO draw code references
	}

	public int getHeight()
	{
		return program.getLength() * ELEMENT_HEIGHT;
	}
}
