package ch.tbe.util;

import java.awt.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import ch.tbe.Board;
import ch.tbe.gui.PrintView;
import ch.tbe.gui.TBE;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PrintHandler implements Printable {
	private Component componentToBePrinted;

	private static JPanel p;

	private static Board b;

	public static void printBoard(Board b) {
		PrintHandler.b = b;
		PrintHandler.createLayout(false);
		new PrintHandler(p).print();

	}

	public static void printComponent(Component c) {
		new PrintHandler(c).print();
	}

	public PrintHandler(Component componentToBePrinted) {
		this.componentToBePrinted = componentToBePrinted;
	}

	public static void showPreview(Board b) {
		PrintHandler.b = b;
		PrintHandler.createLayout(true);

	}

	public void print() {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);
		if (printJob.printDialog())
			try {
				printJob.print();
			} catch (PrinterException pe) {
				System.out.println("Error printing: " + pe);
			}

	}

	public int print(Graphics g, PageFormat pf, int pageIndex) {
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

		if (pageIndex >= totalNumPages) {
			response = NO_SUCH_PAGE;
		} else {
			g2.translate(pf.getImageableX(), pf.getImageableY());
			g2.translate(0f, -pageIndex * pageHeight);
			g2.scale(scale, scale);
			componentToBePrinted.paint(g2);
			enableDoubleBuffering(componentToBePrinted);
			response = Printable.PAGE_EXISTS;
		}
		return response;
	}

	public static void disableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);
	}

	public static void enableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);
	}

	public static void exportBoard(Board b) {
		PrintHandler.b = b;

		createLayout(false);
		export(p);

	}

	private static void export(Component comp) {
		JFileChooser chooser = new JFileChooser();

		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".jpg") || f.isDirectory();
			}

			public String getDescription() {
				return "JPEG (*.jpg)";
			}
		});

		int answer = chooser.showSaveDialog(new Frame());
		if (answer == 0) {
			File filename = chooser.getSelectedFile();
			if (filename != null && !filename.getPath().toLowerCase().endsWith(".jpg")) {
				filename = new File(filename.getPath() + ".jpg");
			}
			Dimension size = comp.getSize();
			BufferedImage myImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = myImage.createGraphics();
			comp.paint(g2);
			try {
				OutputStream out = new FileOutputStream(filename);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(myImage);
				out.close();
			} catch (Exception e) {
				// TODO exeption handling
				System.err.println("Export-Error");
			}
		}
	}

	private static void createLayout(boolean visible) {

		InputStream printHandlerStream;
		ResourceBundle rb = null;
		try {
			printHandlerStream = PrintHandler.class.getResourceAsStream("../config/lang/" + TBE.getInstance().getLang() + "/printHandler.txt");
			rb = new PropertyResourceBundle(printHandlerStream);
		} catch (FileNotFoundException fnne) {
			System.out.println("LanguageFile for PrintHandler not found !");
		} catch (IOException ioe) {
			System.out.println("Error with ResourceBundle PrintHandler!");
		}

		p = new PrintView(b);

		JScrollPane pane = new JScrollPane(p);
		if (visible) {
			Object[] options = { rb.getString("print"), rb.getString("export"), rb.getString("cancel") };
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			if (pane.getPreferredSize().height >= toolkit.getScreenSize().getHeight()) {
				pane.setPreferredSize(new Dimension((int) pane.getPreferredSize().getWidth() + 20, (int) toolkit.getScreenSize().getHeight() - 200));
			}

			switch (JOptionPane.showOptionDialog(null, pane, rb.getString("title"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1])) {
			case 0:
				new PrintHandler(p).print();
				break;
			case 1:
				export(p);
			}
		} else {
			JFrame f = new JFrame();
			f.add(pane);
			f.pack();

		}

	}

}