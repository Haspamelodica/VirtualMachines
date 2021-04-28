package net.haspamelodica.mama.interpreter.visualizing.heap;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawCodeReferenceArrow;
import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawTextCentered;

import java.util.List;
import java.util.function.Function;

import org.eclipse.swt.graphics.Color;

import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.heap.HeapObjectContent;
import net.haspamelodica.mama.interpreter.visualizing.gui.CodeGUI;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.CodePointerHOE;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.DrawableHeapObjectElement;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.DrawableHeapObjectElement.Type;
import net.haspamelodica.swt.helper.gcs.GeneralGC;
import net.haspamelodica.swt.helper.swtobjectwrappers.Point;
import net.haspamelodica.swt.helper.swtobjectwrappers.Rectangle;

public class VisualizingHeapObject implements HeapObject
{
	private static final double	TAG_WIDTH		= 20;
	private static final double	ELEMENT_WIDTH	= 100;
	private static final double	HEIGHT			= 25;
	public static final Color	DEFAULT_BG		= new Color(255, 255, 255);

	private double	x;
	private double	y;

	private VisualizingHeapObjectContent content;

	private final Runnable positionObserver;

	public VisualizingHeapObject(HeapObjectContent content, double x, double y, Runnable positionObserver)
	{
		setContent(content);
		this.x = x;
		this.y = y;
		this.positionObserver = positionObserver;
	}

	public void draw(GeneralGC gc)
	{
		double xWithOff = x;
		gc.setBackground(DEFAULT_BG);
		gc.fillRectangle(xWithOff, y, TAG_WIDTH, getHeight());
		drawTextCentered(gc, content.getTag().name(), xWithOff, y, TAG_WIDTH, getHeight());
		xWithOff += TAG_WIDTH;

		for(DrawableHeapObjectElement e : content.getElements())
		{
			e.draw(gc, xWithOff, y, ELEMENT_WIDTH, getHeight());
			gc.drawLine(xWithOff, y, xWithOff, y + getHeight());
			xWithOff += ELEMENT_WIDTH;
		}

		gc.setLineWidth(1);
		gc.drawRectangle(x, y, xWithOff - x, getHeight());
		gc.setLineWidth(0.5);
	}
	public void drawCodeRefs(GeneralGC gc, Point codeOffset, Function<Rectangle, Rectangle> worldToCanvasCoords)
	{
		double xWithOff = x + TAG_WIDTH;

		List<DrawableHeapObjectElement> elements = content.getElements();
		for(int i = 0; i < elements.size(); i ++)
		{
			DrawableHeapObjectElement e = elements.get(i);
			if(e.getType() == Type.CODE_POINTER)
			{
				int codePointer = ((CodePointerHOE) e).getPointer();
				Rectangle referencingHOEBounds = worldToCanvasCoords.apply(new Rectangle(xWithOff, y, ELEMENT_WIDTH, getHeight()));
				drawCodeReferenceArrow(gc,
						referencingHOEBounds,
						new Rectangle(codeOffset.x, codeOffset.y + CodeGUI.ELEMENT_HEIGHT * codePointer, CodeGUI.WIDTH, CodeGUI.ELEMENT_HEIGHT));
				xWithOff += ELEMENT_WIDTH;
			}
		}
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, getWidth(), getHeight());
	}
	public double getWidth()
	{
		return TAG_WIDTH + ELEMENT_WIDTH * content.getElementCount();
	}
	public double getHeight()
	{
		return HEIGHT;
	}

	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public void moveBy(double x, double y)
	{
		this.x += x;
		this.y += y;
		positionObserver.run();
	}

	@Override
	public VisualizingHeapObjectContent getContent()
	{
		return content;
	}
	@Override
	public void setContent(HeapObjectContent content)
	{
		//TODO can we do this cleaner?
		this.content = (VisualizingHeapObjectContent) content;
	}
}
