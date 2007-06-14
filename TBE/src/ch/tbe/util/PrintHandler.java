package ch.tbe.util;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import ch.tbe.Board;
import ch.tbe.gui.LegendBar;
import ch.tbe.gui.PrintView;
import ch.tbe.jgraph.TBECellViewFactory;

import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PrintHandler implements Printable
{
	private Component componentToBePrinted;
	private static JFrame f;

	public static void printBoard(Board b)
	{
		new PrintHandler(PrintHandler.createLayout(b)).print();
	}

	public static void printComponent(Component c)
	{
		new PrintHandler(c).print();
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
			}
			catch (PrinterException pe)
			{
				System.out.println("Error printing: " + pe);
			}
			f.dispose();
	}

	public int print(Graphics g, PageFormat pf, int pageIndex)
	{
		int response = NO_SUCH_PAGE;
		Graphics2D g2 = (Graphics2D) g;

		disableDoubleBuffering(componentToBePrinted);
		Dimension d = componentToBePrinted.getSize();
		double panelWidth = d.width; // width in pixels
		double panelHeight = d.height; // height in pixels
		double pageHeight = pf.getImageableHeight(); // height of printer
		// page
		double pageWidth = pf.getImageableWidth(); // width of printer page
		double scale = pageWidth / panelWidth;
		int totalNumPages = (int) Math.ceil(scale * panelHeight / pageHeight); // make

		if (pageIndex >= totalNumPages)
		{
			response = NO_SUCH_PAGE;
		}
		else
		{
			g2.translate(pf.getImageableX(), pf.getImageableY());
			g2.translate(0f, -pageIndex * pageHeight);
			g2.scale(scale, scale);
			componentToBePrinted.paint(g2);
			enableDoubleBuffering(componentToBePrinted);
			response = Printable.PAGE_EXISTS;
		}
		return response;
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

	public static void export(Board b)
	{
		Component comp = createLayout(b);
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
		if (filename != null && !filename.getPath().toLowerCase().endsWith(".jpg"))
		{
			filename = new File(filename.getPath() + ".jpg");
		}
		Dimension size = comp.getSize();
		BufferedImage myImage = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = myImage.createGraphics();
		comp.paint(g2);
		try
		{
			OutputStream out = new FileOutputStream(filename);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(myImage);
			out.close();
		}
		catch (Exception e)
		{
			// TODO exeption handling
		}
		f.dispose();
	}

	private static Component createLayout(Board board)
	{
		f = new JFrame("Vorschau");// TODO language
		f.setBackground(Color.WHITE);
		f.add(new PrintView(board));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		return f;
	}

}