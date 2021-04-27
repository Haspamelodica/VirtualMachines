package net.haspamelodica.mama.interpreter.visualizing.gui;

import net.haspamelodica.swt.helper.gcs.GeneralGC;
import net.haspamelodica.swt.helper.swtobjectwrappers.Point;

public class GUIUtils
{
	public static void drawTextCentered(GeneralGC gc, String text, double x, double y, double width, double height)
	{
		Point textExtent = gc.textExtent(text);
		gc.drawText(text, x + (width - textExtent.x) / 2, y + (height - textExtent.y) / 2, true);
	}

	private GUIUtils()
	{}
}
