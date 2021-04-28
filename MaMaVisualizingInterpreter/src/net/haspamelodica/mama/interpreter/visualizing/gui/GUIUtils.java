package net.haspamelodica.mama.interpreter.visualizing.gui;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import net.haspamelodica.swt.helper.gcs.GeneralGC;

public class GUIUtils
{
	public static void drawReferenceArrow(GeneralGC gc, double xFrom, double yFrom, double xTo, double yTo)
	{
		//TODO
		gc.setLineWidth(0);
		gc.drawLine(xFrom, yFrom, xTo, yTo);
		gc.setLineWidth(1);
	}

	public static void drawTextCentered(GeneralGC gc, String text, double x, double y, double width, double height)
	{
		net.haspamelodica.swt.helper.swtobjectwrappers.Point textExtent = gc.textExtent(text);
		gc.drawText(text, x + (width - textExtent.x) / 2, y + (height - textExtent.y) / 2, true);
	}
	public static void drawTextCentered(GC gc, String text, int x, int y, int width, int height)
	{
		org.eclipse.swt.graphics.Point textExtent = gc.textExtent(text);
		gc.drawText(text, x + (width - textExtent.x) / 2, y + (height - textExtent.y) / 2, true);
	}

	public static Point getOffset(Control from, Control to, Composite commonParent)
	{
		Point fromLoc = getPositionInParent(from, commonParent);
		Point toLoc = getPositionInParent(to, commonParent);
		toLoc.x -= fromLoc.x;
		toLoc.y -= fromLoc.y;
		return toLoc;
	}
	public static Point getPositionInParent(Control control, Composite parent)
	{
		Point result = new Point(0, 0);
		for(Control c = control; c != parent; c = c.getParent())
		{
			Point location = c.getLocation();
			result.x += location.x;
			result.y += location.y;
		}
		return result;
	}

	private GUIUtils()
	{}
}
