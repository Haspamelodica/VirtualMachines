package net.haspamelodica.mama.interpreter.visualizing.heap;

import static net.haspamelodica.mama.interpreter.visualizing.gui.GUIUtils.drawTextCentered;

import java.util.List;

import net.haspamelodica.mama.interpreter.heap.HeapObjectContent;
import net.haspamelodica.mama.interpreter.visualizing.heap.objectelements.DrawableHeapObjectElement;
import net.haspamelodica.swt.helper.gcs.GeneralGC;
import net.haspamelodica.swt.helper.swtobjectwrappers.Rectangle;

public abstract class VisualizingHeapObjectContent implements HeapObjectContent
{
	private static final double	TAG_WIDTH		= 20;
	private static final double	ELEMENT_WIDTH	= 100;
	private static final double	HEIGHT			= 25;

	private double	x;
	private double	y;

	public VisualizingHeapObjectContent(double x, double y)
	{
		this.x = x;
		this.y = y;
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
	}

	public void draw(GeneralGC gc)
	{
		double x = this.x;
		drawTextCentered(gc, getTag().name(), x, y, TAG_WIDTH, HEIGHT);
		x += TAG_WIDTH;

		for(DrawableHeapObjectElement e : getElements())
		{
			e.draw(gc, x, y, ELEMENT_WIDTH, HEIGHT);
			gc.drawLine(x, y, x, y + HEIGHT);
			x += ELEMENT_WIDTH;
		}

		gc.setLineWidth(1);
		gc.drawRectangle(this.x, y, x - this.x, HEIGHT);
		gc.setLineWidth(0.5);
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, TAG_WIDTH + ELEMENT_WIDTH * getElementCount(), HEIGHT);
	}

	protected abstract List<DrawableHeapObjectElement> getElements();
	protected abstract int getElementCount();
}
