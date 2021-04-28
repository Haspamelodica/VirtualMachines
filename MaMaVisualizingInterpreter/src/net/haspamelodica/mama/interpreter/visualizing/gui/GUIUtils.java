package net.haspamelodica.mama.interpreter.visualizing.gui;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import net.haspamelodica.swt.helper.gcs.GeneralGC;
import net.haspamelodica.swt.helper.swtobjectwrappers.Rectangle;

public class GUIUtils
{
	public static void drawReferenceArrow(GeneralGC gc, Rectangle from, Rectangle to)
	{
		//TODO
		gc.setLineWidth(0);
		gc.drawLine(from.x + from.width / 2, from.y + from.height / 2, to.x, to.y + to.height / 2);
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

	public static Point getClientAreaOffset(Composite from, Composite to, Composite commonParent)
	{
		Point fromLoc = getPositionInParentIncludingClientArea(from, commonParent);
		Point toLoc = getPositionInParentIncludingClientArea(to, commonParent);
		toLoc.x -= fromLoc.x;
		toLoc.y -= fromLoc.y;
		return toLoc;
	}
	public static Point getPositionInParentIncludingClientArea(Composite control, Composite parent)
	{
		org.eclipse.swt.graphics.Rectangle clientArea = control.getClientArea();
		int borderWidth = control.getBorderWidth();
		Point result = new Point(clientArea.x + borderWidth, clientArea.y + borderWidth);
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
