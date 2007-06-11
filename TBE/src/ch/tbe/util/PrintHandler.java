package ch.tbe.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.RepaintManager;
import javax.swing.filechooser.FileFilter;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import ch.tbe.Board;

public final class PrintHandler implements Printable
{
	private Component componentToBePrinted;
	private static Board board;

	public static void printBoard(Board b)
	{
		new PrintHandler(PrintHandler.createLayout()).print();
	}

	public PrintHandler(Component componentToBePrinted)
	{
		this.componentToBePrinted = componentToBePrinted;
	}

	public void print()
	{
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);
		if (printJob.printDialog())
			try
			{
				printJob.print();
			} catch (PrinterException pe)
			{
				System.out.println("Error printing: " + pe);
			}
	}

	public int print(Graphics g, PageFormat pageFormat, int pageIndex)
	{
		if (pageIndex > 0)
		{
			return (NO_SUCH_PAGE);
		} else
		{
			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(pageFormat.getImageableX(), pageFormat
					.getImageableY());
			disableDoubleBuffering(componentToBePrinted);
			componentToBePrinted.paint(g2d);
			enableDoubleBuffering(componentToBePrinted);
			return (PAGE_EXISTS);
		}
	}

	public static void disableDoubleBuffering(Component c)
	{
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);
	}

	public static void enableDoubleBuffering(Component c)
	{
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);
	}

	public static void export(Component myComponent)
	{
		JFileChooser chooser = new JFileChooser();

		chooser.setFileFilter(new FileFilter()
		{
			public boolean accept(File f)
			{
				return f.getName().toLowerCase().endsWith(".jpg")
						|| f.isDirectory();
			}

			public String getDescription()
			{
				return "JPEG (*.jpg)";
			}
		});
		chooser.showSaveDialog(new Frame());

		File filename = chooser.getSelectedFile();
		Dimension size = myComponent.getSize();
		BufferedImage myImage = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = myImage.createGraphics();
		myComponent.paint(g2);
		try
		{
			OutputStream out = new FileOutputStream(filename);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(myImage);
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static Component createLayout(){
		JFrame f = new JFrame();
		f.setLayout(new BorderLayout());
		f.add(board, BorderLayout.CENTER);
		
		
		
		return f;
	}
}