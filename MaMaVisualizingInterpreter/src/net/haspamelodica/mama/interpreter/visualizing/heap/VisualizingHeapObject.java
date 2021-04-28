package net.haspamelodica.mama.interpreter.visualizing.heap;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawTextCentered;

import org.eclipse.swt.graphics.Color;

import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.heap.HeapObjectContent;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.DrawableHeapObjectElement;
import net.haspamelodica.swt.helper.gcs.GeneralGC;
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
