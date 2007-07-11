package ch.tbe.util;

import java.util.ArrayList;
import java.util.List;
import java.net.URLDecoder;

import ch.tbe.*;
import ch.tbe.framework.*;
import ch.tbe.gui.TBE;
import ch.tbe.item.ShapeItem;
import ch.tbe.item.TextBoxItem;
import ch.tbe.jgraph.*;

import java.awt.Frame;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.*;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Tactic Board Editor
 * **********************
 * XMLHandler 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */
public final class XMLHandler {
	private static ArrayList<Sport> sports = new ArrayList<Sport>();
	private static Board board = null;

	/**
   * Von dieser Klasse darf keine Instanz erstellt werden.
   */
	private XMLHandler() {
	}

	/**
   * Reads the file tbe.config and configure the TBE
   */
	public static void loadTBESettings() {
		/**
     * @author Zumsr1@bfh.ch Der SaxHandler liest den Inhalt der Date tbe.config
     */
		class SaxHandler extends DefaultHandler {
			private TBE tbe = TBE.getInstance();
			private ArrayList<FTPServer> servers = new ArrayList<FTPServer>();
			private ArrayList<String> paths = new ArrayList<String>();

			/**
       * Starte das Abarbeiten von tbe.config
       */
			@SuppressWarnings("deprecation")
      public void loadTBESettings() {
				DefaultHandler handler = new SaxHandler();

				try {
					// Start reading File
					SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
					saxParser.parse(new File(URLDecoder.decode(ClassLoader.getSystemResource("").getPath())+"ch/tbe/config/tbe.config"), handler);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			/*
       * (non-Javadoc)
       * 
       * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
       *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
       */
			public void startElement(String name, String localName, String qName, Attributes atts) throws SAXException {
				// If element is "trainer"
				if (qName.equals("trainer")) {
					// set user-settings
					tbe.setUser(atts.getValue("prename").toString(), atts.getValue("name").toString(), atts.getValue("email").toString());
				}
				if (qName.equals("defaultLanguage")) {
					// set language
					tbe.setLang(atts.getValue("name").toString());
				}
				if (qName.equals("server")) {
					// Add this server to server list
					servers.add(new FTPServer(atts.getValue("name").toString(), atts.getValue("host").toString(), atts.getValue("username").toString(), atts.getValue("password").toString()));
				}

				if (qName.equals("recentlyOpened")) {
					// add this path to recently opened file list
					paths.add(atts.getValue("path").toString());
				}
			}

			/*
       * (non-Javadoc)
       * 
       * @see org.xml.sax.helpers.DefaultHandler#endDocument()
       */
			public void endDocument() throws SAXException {
				// reading of this document finished. Save now the server and
				// recently opened files lists.
				tbe.setFTPServers(servers);
				tbe.setPaths(paths);
			}
		}

		// loadTBESettings aufrufen
		SaxHandler xml = new SaxHandler();
		xml.loadTBESettings();
	}

	/**
	 * Open and return Board
   * @param path, Path to the xml-document
   * @return the opened board
   */
	public static Board openXML(String path) {

		class SaxHandler extends DefaultHandler {
			GraphModel model = new DefaultGraphModel();
			GraphLayoutCache view = new GraphLayoutCache(model, new TBECellViewFactory());
			String actArrowType;
			List<Point2D> actPoints = new ArrayList<Point2D>();
			Point2D actLabelPosition;
			String actText;

			@SuppressWarnings("deprecation")
      public void loadFile(String path) {
				DefaultHandler handler = new SaxHandler();

				try {
					File file = new File(URLDecoder.decode(path));
					if (file.exists()) {
						SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
						saxParser.parse(file, handler);
					} else {
						System.err.println("File not Found Exception");
						JOptionPane.showMessageDialog(TBE.getInstance().getView(), "File not Found! (" + file.getPath() + ")");
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			public void startElement(String name, String localName, String qName, Attributes atts) throws SAXException {
				try {
					if (qName.equals("sport")) {
						Sport sport = null;
						for (Sport s : TBE.getInstance().getSports()) {
							if (s.getName().equals(atts.getValue("name"))) {
								sport = s;
							}
						}

						// TODO Phase 2: check sport-version

						if (sport != null) {
							board = new Board(model, view, sport);
						}
					}
					if (board != null) {
						if (qName.equals("attribute")) {
							board.addAttribute(atts.getValue("text"), atts.getValue("title"));
						}

						if (qName.equals("description")) {
							board.getDescription().setDescription(atts.getValue("name"));
						}

						if (qName.equals("shape")) {
							Point2D point = new Point2D.Double(Double.valueOf(atts.getValue("xCoordinate")).doubleValue(), Double.valueOf(atts.getValue("yCoordinate")));
							ShapeItem item = ItemFactory.getShapeItem(board.getSport(), atts.getValue("type"), point);
							if (item != null) {
								item.setRotation(Integer.valueOf(atts.getValue("rotation")));
								TBEGraphConstants.setBounds(item.getAttributes(), new Rectangle2D.Double(point.getX(), point.getY(), Double.valueOf(atts.getValue("width")), Double.valueOf(atts.getValue("height"))));
								board.addItem(item);
							}
						}

						if (qName.equals("textbox")) {
							Point2D point = new Point2D.Double(Double.valueOf(atts.getValue("xCoordinate")).doubleValue(), Double.valueOf(atts.getValue("yCoordinate")));
							TextBoxItem item = new TextBoxItem(new Rectangle2D.Double(point.getX(), point.getY(), Double.valueOf(atts.getValue("width")).doubleValue(), Double.valueOf(atts.getValue("height")).doubleValue()));
							if (item != null) {
								item.setText(atts.getValue("text"));
								TBEGraphConstants.setBounds(item.getAttributes(), new Rectangle2D.Double(point.getX(), point.getY(), Double.valueOf(atts.getValue("width")), Double.valueOf(atts.getValue("height"))));
								board.addItem(item);
							}
						}

						if (qName.equals("arrow")) {
							if (actArrowType != null) {
								ArrowItem item = ItemFactory.getArrowItem(board.getSport(), actArrowType, actPoints);
								if (item != null) {
									item.setText(actText);
									board.addItem(item);
									TBEGraphConstants.setLabelPosition(item.getAttributes(), actLabelPosition);
								}
								actArrowType = null;
								actPoints.clear();

							}
							actArrowType = atts.getValue("type");
							actLabelPosition = new Point2D.Double(new Double(atts.getValue("xLabelPos")), new Double(Double.valueOf(atts.getValue("yLabelPos"))));
							actText = atts.getValue("text");
						}

						if (qName.equals("field")) {
							for (Field field : board.getSport().getFields()) {
								if (field.getName().equals(atts.getValue("name"))) {
									board.setField(field);
								}
							}
						}

						if (qName.equals("point")) {
							actPoints.add(new Point2D.Double(Double.valueOf(atts.getValue("xCoordinate")).doubleValue(), Double.valueOf(atts.getValue("yCoordinate"))));
						}
					}

				} catch (Exception ex) {
					System.err.println("Could not load Element");
				}
			}

			public void endDocument() throws SAXException {
				if (actArrowType != null) {
					ArrowItem item = ItemFactory.getArrowItem(board.getSport(), actArrowType, actPoints);
					if (item != null) {
						item.setText(actText);
						board.addItem(item);
						TBEGraphConstants.setLabelPosition(item.getAttributes(), actLabelPosition);
						board.addItem(item);
					}
					actArrowType = null;
					actPoints.clear();
				}
				setBoard(board);
			}
		}

		SaxHandler xml = new SaxHandler();
		xml.loadFile(path);

		Board actBoard = board;

		if (board != null) {
			actBoard.setPath(path);
			board = null;
		}

		return actBoard;
	}

	public static ArrayList<Sport> getSports() {
		ArrayList<String> installedSports = FileSystemHandler.getInstalledSports();

		sports.clear();
		for (int i = 0; i < installedSports.size(); i++) {
			openSport(installedSports.get(i));
		}

		return sports;
	}

	/**
	 * Open Sport
	 * @param sport
	 */
	private static void openSport(String sport) {
		class SaxHandler extends DefaultHandler {
			private ArrayList<ItemType> shapes = new ArrayList<ItemType>();
			private ArrayList<ItemType> arrows = new ArrayList<ItemType>();
			private ArrayList<Field> fields = new ArrayList<Field>();
			private Sport actSport;

			@SuppressWarnings("deprecation")
      public void loadSport(String sport) {
				DefaultHandler handler = new SaxHandler();

				try {
					SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
					String filePath = ClassLoader.getSystemResource("ch/tbe/config/sport/" + sport + "/sport.config").getPath();
					saxParser.parse(new File(URLDecoder.decode(filePath)), handler);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			@SuppressWarnings("deprecation")
      public void startElement(String name, String localName, String qName, Attributes atts) throws SAXException {
				if (qName.equals("sport")) {
					actSport = new Sport(atts.getValue("name"));
					actSport.setVersion(atts.getValue("version"));
					actSport.setLcVersion(atts.getValue("lastCompatibleVersion"));
					actSport.setIcon(new ImageIcon(URLDecoder.decode(ClassLoader.getSystemResource("").getPath())+"ch/tbe/config/sport/" + actSport.getName() + "/" + atts.getValue("picture")));
				}

				if (actSport != null) {
					if (qName.equals("shape")) {
						URL imgURL = ClassLoader.getSystemResource("ch/tbe/config/sport/" + actSport.getName() + "/" + atts.getValue("icon"));
						Icon actIcon = new ImageIcon(imgURL);
						imgURL = ClassLoader.getSystemResource("ch/tbe/config/sport/" + actSport.getName() + "/" + atts.getValue("picture"));
						Icon actPicture = new ImageIcon(imgURL);
						shapes.add(new ItemType(atts.getValue("name"), atts.getValue("description"), actIcon, actPicture, Integer.parseInt(atts.getValue("maxSideWidth"))));
					}

					if (qName.equals("arrow")) {
						URL imgURL = ClassLoader.getSystemResource("ch/tbe/config/sport/" + atts.getValue("picture"));
						if (imgURL != null) {
							Icon actIcon = new ImageIcon(imgURL);
							arrows.add(new ItemType(atts.getValue("name"), atts.getValue("description"), actIcon));
						}
					}

					if (qName.equals("field")) {
						URL imgURL = ClassLoader.getSystemResource("ch/tbe/config/sport/" + actSport.getName() + "/" + atts.getValue("picture"));
						Icon actIcon = null;
						try {
							actIcon = new ImageIcon(imgURL);
						} catch (Exception e) {

						}
						fields.add(new Field(atts.getValue("name"), atts.getValue("description"), actIcon));
					}
				}
			}

			public void endDocument() throws SAXException {
				actSport.setShapeTypes(shapes);
				actSport.setArrowTypes(arrows);
				actSport.setFields(fields);
				addSport(actSport);
			}
		}

		SaxHandler xml = new SaxHandler();
		xml.loadSport(sport);
	}

	private static void addSport(Sport sport) {
		sports.add(sport);
	}

	/**
   * These function is needed for the communication between the SaxHandler and the
   * function readXML.
   * 
   * @param myBoard
   */
	private static void setBoard(Board myBoard) {
		board = myBoard;
	}

	/**
	 * Saves the Board in an XML-File
	 * @param board, Board to save
	 * @return boolean, true if save is ok
	 */
	public static boolean saveBoard(Board board) {

		int answer = 0;
		if (board.getPath().equals("")) {
			JFileChooser chooser = new JFileChooser();

			chooser.setFileFilter(new FileFilter() {
				public boolean accept(File f) {
					return f.getName().toLowerCase().endsWith(".tbe") || f.isDirectory();
				}

				public String getDescription() {
					return "TBE (*.tbe)";
				}
			});
			answer = chooser.showSaveDialog(new Frame());
			if (answer == 0) {
				TBE.getInstance().setSaved(true);

				File filename = chooser.getSelectedFile();

				if (filename != null && !filename.getPath().toLowerCase().endsWith(".tbe")) {
					filename = new File(filename.getPath() + ".tbe");
				}

				board.setPath(filename.getPath());
				TBE.getInstance().addRecently(filename.getPath());
			}
		}

		if (!board.getPath().equals("")) {
			try {
				Element eTbe = new Element("TBE");
				Element eSport = new Element("sport");
				Element eCreator = new Element("creator");
				Element eHistory = new Element("history");
				Element eModifier = new Element("modifier");
				Element eField = new Element("field");
				Element eDescription = new Element("description");
				Element eItemComponents = new Element("itemComponents");

				eTbe.setAttribute("version", TBE.getInstance().getVersion());
				eTbe.addContent(eSport);
				eTbe.addContent(eCreator);
				eTbe.addContent(eModifier);
				eTbe.addContent(eHistory);
				eTbe.addContent(eField);
				eTbe.addContent(eDescription);
				eTbe.addContent(eItemComponents);

				eSport.setAttribute("name", board.getSport().getName());
				eSport.setAttribute("version", board.getSport().getVersion());

				eCreator.setAttribute("name", TBE.getInstance().getUserName());
				eCreator.setAttribute("prename", TBE.getInstance().getUserPrename());
				eCreator.setAttribute("email", TBE.getInstance().getUserEmail());

				eField.setAttribute("name", board.getField().getName());

				eDescription.setAttribute("name", board.getDescription().getDescription());

				for (Attribute attribute : board.getDescription().getAttributes()) {
					Element eAttribute = new Element("attribute");
					eAttribute.setAttribute("title", attribute.getTitle());
					eAttribute.setAttribute("text", attribute.getText());
					eDescription.addContent(eAttribute);
				}

				for (ItemComponent item : board.getItems()) {
					Element eItemComponent;

					if (item instanceof ArrowItem) {
						eItemComponent = new Element("arrow");
						eItemComponent.setAttribute("type", item.getType());

						if (((ArrowItem) item).getText() != null) {
							eItemComponent.setAttribute("text", ((ArrowItem) item).getText());
						} else {
							eItemComponent.setAttribute("text", "");
						}

						Point2D p = TBEGraphConstants.getLabelPosition(((ArrowItem) item).getAttributes());

						if (p != null) {
							eItemComponent.setAttribute("xLabelPos", p.getX() + "");
							eItemComponent.setAttribute("yLabelPos", p.getY() + "");
						} else {
							eItemComponent.setAttribute("xLabelPos", "0");
							eItemComponent.setAttribute("yLabelPos", "0");
						}

						for (Point2D point : ((ArrowItem) item).getPoints()) {
							Element ePoint = new Element("point");
							ePoint.setAttribute("xCoordinate", String.valueOf(point.getX()));
							ePoint.setAttribute("yCoordinate", String.valueOf(point.getY()));
							eItemComponent.addContent(ePoint);
						}
					} else if (item instanceof TextBoxItem) {
						eItemComponent = new Element("textbox");
						eItemComponent.setAttribute("text", ((TextBoxItem) item).getText());

						double widthDiff = TBEGraphConstants.getBounds(((TextBoxItem) item).getAttributes()).getWidth() / 2;
						double heightDiff = TBEGraphConstants.getBounds(((TextBoxItem) item).getAttributes()).getHeight() / 2;

						eItemComponent.setAttribute("xCoordinate", String.valueOf(TBEGraphConstants.getBounds(((TextBoxItem) item).getAttributes()).getCenterX() - widthDiff));
						eItemComponent.setAttribute("yCoordinate", String.valueOf(TBEGraphConstants.getBounds(((TextBoxItem) item).getAttributes()).getCenterY() - heightDiff));
						eItemComponent.setAttribute("height", String.valueOf(board.getCellBounds(item).getHeight()));
						eItemComponent.setAttribute("width", String.valueOf(board.getCellBounds(item).getWidth()));

					} else {
						eItemComponent = new Element("shape");
						eItemComponent.setAttribute("type", item.getType());

						double widthDiff = TBEGraphConstants.getBounds(((ShapeItem) item).getAttributes()).getWidth() / 2;
						double heightDiff = TBEGraphConstants.getBounds(((ShapeItem) item).getAttributes()).getHeight() / 2;

						eItemComponent.setAttribute("xCoordinate", String.valueOf(TBEGraphConstants.getBounds(((ShapeItem) item).getAttributes()).getCenterX() - widthDiff));
						eItemComponent.setAttribute("yCoordinate", String.valueOf(TBEGraphConstants.getBounds(((ShapeItem) item).getAttributes()).getCenterY() - heightDiff));
						eItemComponent.setAttribute("height", String.valueOf(board.getCellBounds(item).getHeight()));
						eItemComponent.setAttribute("width", String.valueOf(board.getCellBounds(item).getWidth()));
						eItemComponent.setAttribute("rotation", String.valueOf(((ShapeItem) item).getRotation()));
					}

					eItemComponents.addContent(eItemComponent);
				}

				Document document = new Document(eTbe);

				Format format = Format.getPrettyFormat();
				format.setEncoding("iso-8859-1");
				XMLOutputter out = new XMLOutputter(format);
				java.io.FileWriter writer = new java.io.FileWriter(board.getPath());
				out.output(document, writer);
				writer.flush();
				writer.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return answer == 0;
	}

	/**
	 * Saves the TBE-Settings in a XML-File
	 *
	 */
	@SuppressWarnings({ "deprecation" })
  public static void saveTBESettings() {
		TBE tbe = TBE.getInstance();
		try {
			Element eTbe = new Element("TBE");
			Element eTrainer = new Element("trainer");
			Element eDefaultLanguage = new Element("defaultLanguage");
			Element eServers = new Element("servers");
			Element eServer = new Element("server");
			Element eRecently = new Element("recently");
			Element eRecentlyOpened = new Element("recentlyOpened");

			eTbe.setAttribute("version", tbe.getVersion());
			eTbe.addContent(eTrainer);
			eTbe.addContent(eDefaultLanguage);
			eTbe.addContent(eServers);
			eTbe.addContent(eRecently);

			eTrainer.setAttribute("name", tbe.getUserName());
			eTrainer.setAttribute("prename", tbe.getUserPrename());
			eTrainer.setAttribute("email", tbe.getUserEmail());

			eDefaultLanguage.setAttribute("name", tbe.getLang());

			for (FTPServer server : tbe.getServers()) {
				eServer = new Element("server");
				eServer.setAttribute("name", server.getName());
				eServer.setAttribute("host", server.getHost());
				eServer.setAttribute("username", server.getUsername());
				eServer.setAttribute("password", server.getPassword());

				eServers.addContent(eServer);
			}

			for (String path : tbe.getRecently()) {
				eRecentlyOpened = new Element("recentlyOpened");
				eRecentlyOpened.setAttribute("path", path);
				eRecently.addContent(eRecentlyOpened);
			}

			Document document = new Document(eTbe);

			Format format = Format.getPrettyFormat();
			format.setEncoding("iso-8859-1");
			XMLOutputter out = new XMLOutputter(format);
			java.io.FileWriter writer = new java.io.FileWriter(URLDecoder.decode(ClassLoader.getSystemResource("").getPath())+"ch/tbe/config/tbe.config");
			out.output(document, writer);
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
