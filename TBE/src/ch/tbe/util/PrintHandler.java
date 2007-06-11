package ch.tbe.util;

import java.awt.*;
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

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import ch.tbe.Board;
import ch.tbe.jgraph.TBECellViewFactory;

public final class PrintHandler implements Printable
{
	private Component componentToBePrinted;
	private static JFrame f;

	public static void printBoard(Board b)
	{
		new PrintHandler(PrintHandler.createLayout(b)).print();
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
			//f.dispose();
	}

	public int print(Graphics g, PageFormat pageFormat, int pageIndex)
	{
		if (pageIndex > 0)
		{
			return (NO_SUCH_PAGE);
		}
		else
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
		if (filename != null && !filename.getPath().toLowerCase().endsWith(".jpg")){
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
		f.setLayout(new BorderLayout());
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,
				new TBECellViewFactory());
		Board temp = new Board(model, view, board.getSport());
		temp.getGraphLayoutCache().insert(
				board.cloneItems(board.getGraphLayoutCache().getCells(true,
						true, true, true)));
		temp.setBackgroundImage(board.getBackgroundImage());
		temp.clearSelection();
		f.add(temp);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		return f;
	}
}