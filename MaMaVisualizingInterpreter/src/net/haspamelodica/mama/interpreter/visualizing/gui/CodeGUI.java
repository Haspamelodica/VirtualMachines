package net.haspamelodica.mama.interpreter.visualizing.gui;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawTextCentered;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import net.haspamelodica.mama.model.debugging.InstructionForDebugging;
import net.haspamelodica.mama.model.debugging.MaMaProgramForDebugging;
import net.haspamelodica.swt.helper.gcs.GeneralGC;
import net.haspamelodica.swt.helper.gcs.SWTGC;
import net.haspamelodica.swt.helper.gcs.TranslatedGC;

public class CodeGUI extends Canvas
{
	public static final int		LEFT_MARGIN			= 10;
	public static final int		WIDTH				= 200;
	public static final int		ELEMENT_HEIGHT		= 50;
	private static final Color	CURRENT_INSTR_BG	= new Color(200, 255, 200);

	private final MaMaProgramForDebugging	program;
	private final IntSupplier				getCurrentCodePointer;

	private final Supplier<org.eclipse.swt.graphics.Point>	getHeapOffset;
	private final Consumer<GeneralGC>						drawHeapCrossrefs;

	public CodeGUI(Composite parent, Supplier<org.eclipse.swt.graphics.Point> getHeapOffset, Consumer<GeneralGC> drawHeapCrossrefs,
			MaMaProgramForDebugging program, IntSupplier getCurrentCodePointer)
	{
		super(parent, SWT.DOUBLE_BUFFERED);
		this.program = program;
		this.getCurrentCodePointer = getCurrentCodePointer;
		this.getHeapOffset = getHeapOffset;
		this.drawHeapCrossrefs = drawHeapCrossrefs;
		setSize(WIDTH, SWT.DEFAULT);
		addPaintListener(e -> draw(e.gc));
	}

	private void draw(GC swtgc)
	{
		int currentCodePointer = getCurrentCodePointer.getAsInt();
		int y = 0;
		List<InstructionForDebugging> instructions = program.getInstructionsForDebugging();
		for(int i = 0; i < instructions.size(); i ++)
		{
			if(i == currentCodePointer)
			{
				swtgc.setBackground(CURRENT_INSTR_BG);
				swtgc.fillRectangle(0, y, WIDTH, ELEMENT_HEIGHT);
			}
			InstructionForDebugging instruction = instructions.get(i);
			int iFinal = i;
			String labelsString = program
					.getLabels()
					.stream()
					.filter(l -> l.getTarget() == iFinal)
					.map(l -> l.getName() + ":")
					.collect(Collectors.joining(": "));
			drawTextCentered(swtgc, i + ": " + labelsString + "\r" + instruction, LEFT_MARGIN, y, -1, ELEMENT_HEIGHT);
			//If the immediate has a label argument, we could, but probably don't want to draw a line to the referenced code line
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

	public int getHeight()
	{
		return program.getLength() * ELEMENT_HEIGHT;
	}
}
