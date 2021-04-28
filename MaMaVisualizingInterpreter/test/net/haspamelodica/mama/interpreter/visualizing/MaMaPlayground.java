package net.haspamelodica.mama.interpreter.visualizing;

import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import net.haspamelodica.mama.MaMaParser;
import net.haspamelodica.mama.model.debugging.MaMaProgramForDebugging;

public class MaMaPlayground
{
	public static void main(String[] args) throws IOException
	{
		Display display = new Display();
		Shell dummy = new Shell(display);
		FileDialog fileDialog = new FileDialog(dummy, SWT.OPEN);
		fileDialog.setText("Select MaMa program to run");
		Path programPath = Path.of(fileDialog.open());
		MaMaProgramForDebugging program = MaMaParser.parse(programPath);
		MaMaVisualizer visualizer = new MaMaVisualizer(program);
		visualizer.run();
	}
}
