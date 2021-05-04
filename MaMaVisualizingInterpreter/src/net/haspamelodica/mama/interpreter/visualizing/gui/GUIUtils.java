package net.haspamelodica.mama.interpreter.visualizing.gui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import net.haspamelodica.swt.helper.gcs.GeneralGC;
import net.haspamelodica.swt.helper.swtobjectwrappers.Rectangle;

public class GUIUtils
{
	public static void drawHeapReferenceArrow(GeneralGC gc, Rectangle from, Rectangle to)
	{
		drawReferenceArrow(gc, from, to, MaMaGUI.HEAP_REF_FG);
	}
	public static void drawCodeReferenceArrow(GeneralGC gc, Rectangle from, Rectangle to)
	{
		drawReferenceArrow(gc, from, to, MaMaGUI.CODE_REF_FG);
	}
	private static void drawReferenceArrow(GeneralGC gc, Rectangle from, Rectangle to, Color color)
	{
		Color oldForeground = gc.getForeground();
		double oldLineWidth = gc.getLineWidth();

		gc.setForeground(color);
		gc.setLineWidth(0);

		gc.drawLine(from.x + from.width / 2, from.y + from.height / 2, to.x, to.y + to.height / 2);
		//TODO draw a dot and an arrow head

		gc.setForeground(oldForeground);
		gc.setLineWidth(oldLineWidth);
	}

	public static void drawTextCentered(GeneralGC gc, String text, Rectangle rect)
	{
		drawTextCentered(gc, text, rect.x, rect.y, rect.width, rect.height);
	}
	public static void drawTextCentered(GeneralGC gc, String text, double x, double y, double width, double height)
	{
		net.haspamelodica.swt.helper.swtobjectwrappers.Point textExtent = gc.textExtent(text);
		double xOff = width < 0 ? 0 : (width - textExtent.x) / 2;
		double yOff = height < 0 ? 0 : (height - textExtent.y) / 2;
		gc.drawText(text, x + xOff, y + yOff, true);
	}
	public static void drawTextCentered(GC gc, String text, org.eclipse.swt.graphics.Rectangle rect)
	{
		drawTextCentered(gc, text, rect.x, rect.y, rect.width, rect.height);
	}
	public static void drawTextCentered(GC gc, String text, int x, int y, int width, int height)
	{
		org.eclipse.swt.graphics.Point textExtent = gc.textExtent(text);
		int xOff = width < 0 ? 0 : (width - textExtent.x) / 2;
		int yOff = height < 0 ? 0 : (height - textExtent.y) / 2;
		gc.drawText(text, x + xOff, y + yOff, true);
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
