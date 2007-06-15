package ch.tbe.util;

import java.awt.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import ch.tbe.Board;
import ch.tbe.gui.PrintView;
import ch.tbe.gui.TBE;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private static JFrame f;

    private static JPanel p;

    private static Board b;

    public static void printBoard(Board b) {
	PrintHandler.b = b;

	new PrintHandler(PrintHandler.createLayout(false)).print();

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

	Component comp = createLayout(false);
	export(comp);

    }

    private static void export(Component comp) {
	JFileChooser chooser = new JFileChooser();

	chooser.setFileFilter(new FileFilter() {
	    public boolean accept(File f) {
		return f.getName().toLowerCase().endsWith(".jpg")
			|| f.isDirectory();
	    }

	    public String getDescription() {
		return "JPEG (*.jpg)";
	    }
	});

	chooser.showSaveDialog(new Frame());

	File filename = chooser.getSelectedFile();
	if (filename != null
		&& !filename.getPath().toLowerCase().endsWith(".jpg")) {
	    filename = new File(filename.getPath() + ".jpg");
	}
	Dimension size = comp.getSize();
	BufferedImage myImage = new BufferedImage(size.width, size.height,
		BufferedImage.TYPE_INT_RGB);
	Graphics2D g2 = myImage.createGraphics();
	comp.paint(g2);
	try {
	    OutputStream out = new FileOutputStream(filename);
	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	    encoder.encode(myImage);
	    out.close();
	} catch (Exception e) {
	    // TODO exeption handling
	}
    }

    private static Component createLayout(boolean visible) {

	InputStream printHandlerStream;
	ResourceBundle rb = null;
	try {
	    printHandlerStream = PrintHandler.class
		    .getResourceAsStream("../config/lang/"
			    + TBE.getInstance().getLang() + "/printHandler.txt");
	    rb = new PropertyResourceBundle(printHandlerStream);
	} catch (FileNotFoundException fnne) {
	    System.out.println("LanguageFile for PrintHandler not found !");
	} catch (IOException ioe) {
	    System.out.println("Error with ResourceBundle PrintHandler!");
	}

	f = new JFrame(rb.getString("title"));
	f.setLayout(new BorderLayout());
	f.setBackground(Color.WHITE);
	p = new PrintView(b);
	f.add(new JScrollPane(p), BorderLayout.CENTER);
	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	f.setResizable(false);
	if (visible) {

	    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    class printListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		    new PrintHandler(p).print();
		    f.dispose();
		}
	    }
	    JButton print = new JButton(rb.getString("print"));

	    print.addActionListener(new printListener());

	    class exportListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		    export(p);
		    f.dispose();
		}
	    }
	    JButton export = new JButton(rb.getString("export"));
	    export.addActionListener(new exportListener());

	    class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		    f.dispose();
		}
	    }
	    JButton cancel = new JButton(rb.getString("cancel"));
	    cancel.addActionListener(new cancelListener());
	    buttons.add(print);
	    buttons.add(export);
	    buttons.add(cancel);
	    f.add(buttons, BorderLayout.NORTH);

	    Toolkit toolkit = Toolkit.getDefaultToolkit();
	    f.setSize(new Dimension((int) f.getPreferredSize().getWidth(),
		    (int) toolkit.getScreenSize().getHeight() - 50));

	    f.setVisible(visible);

	}
	return p;
    }

}