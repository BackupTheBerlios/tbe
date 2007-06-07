package ch.tbe.util;

import java.util.ArrayList;
import java.util.List;

import ch.tbe.*;
import ch.tbe.framework.*;
import ch.tbe.gui.TBE;
import ch.tbe.jgraph.*;

import java.awt.Frame;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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
 * @author Meied4@bfh.ch, Zumsr1@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch
 * 
 *
 */
public final class XMLHandler
{
	private static ArrayList<Sport> sports = new ArrayList<Sport>();
	private static Board board = null;

	/**
	 * Von dieser Klasse darf keine Instanz erstellt werden.
	 */
	private XMLHandler(){}
	
	/**
	 * Liest die Datei tbe.config und konfiguriert den TBE
	 */
	public static void loadTBESettings()
	{
		/**
		 * @author Zumsr1@bfh.ch
		 * Der SaxHandler liest den Inhalt der Date tbe.config
		 */
		class SaxHandler extends DefaultHandler
		{
			private TBE tbe = TBE.getInstance();
			private ArrayList<FTPServer> servers = new ArrayList<FTPServer>();
			private ArrayList<String> paths = new ArrayList<String>();

			
			/**
			 * Starte das Abarbeiten von tbe.config
			 */
			public void loadTBESettings()
			{
				DefaultHandler handler = new SaxHandler();

				try
				{
					//Start reading File
					SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
					saxParser.parse(new File("src/ch/tbe/config/tbe.config"),handler); // FIXME: Pfad muss anders definiert werden.
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}

			/* (non-Javadoc)
			 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
			 */
			public void startElement(String name, String localName, String qName, Attributes atts) throws SAXException
			{
				// If element is "trainer"
				if (qName.equals("trainer"))
				{
					// set user-settings
					tbe.setUser(atts.getValue("prename").toString(), atts.getValue("name").toString(), atts.getValue("name").toString());
				}
				if (qName.equals("defaultLanguage"))
				{
					// set language
					tbe.setLang(atts.getValue("name").toString());
				}
				if (qName.equals("server"))
				{
					// Add this server to server list
					servers.add(new FTPServer(atts.getValue("name").toString(), atts.getValue("host").toString(), atts.getValue("username").toString(), atts.getValue("password").toString()));
				}

				if (qName.equals("recentlyOpened"))
				{
					// add this path to recently opened file list
					paths.add(atts.getValue("path").toString());
				}
			}

			/* (non-Javadoc)
			 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
			 */
			public void endDocument() throws SAXException
			{
				// reading of this document finished. Save now the server and recently opened files lists.
				tbe.setFTPServers(servers);
				tbe.setPaths(paths);
			}
		}
		
		// loadTBESettings aufrufen
		SaxHandler xml = new SaxHandler();
		xml.loadTBESettings();
	}

	
	/**
	 * @param path: Path to the xml-document
	 * @return the opened board
	 */
	public static Board openXML(String path)
	{
		class SaxHandler extends DefaultHandler
		{
			GraphModel model = new DefaultGraphModel();
			GraphLayoutCache view = new GraphLayoutCache(model,
					new TBECellViewFactory());
			String actArrowType;
			List<Point2D> actPoints = new ArrayList<Point2D>();

			public void loadFile(String path)
			{
				DefaultHandler handler = new SaxHandler();

				try
				{
					// TODO: Check if file exists
					SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
					saxParser.parse(new File(path), handler);
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}

			public void startElement(String name, String localName,
					String qName, Attributes atts) throws SAXException
			{
				if (qName.equals("sport"))
				{
					Sport sport = null;
					for (Sport s : TBE.getInstance().getSports())
					{
						if (s.getName().equals(atts.getValue("name")))
						{
							sport = s;
						}
					}

					// TODO: check sport-version

					if (sport != null)
					{
						board = new Board(model, view, sport);
					}
				}
				if (board != null)
				{
					if (qName.equals("attribute"))
					{
						board.addAttribute(atts.getValue("title"), atts.getValue("text"));
					}

					if (qName.equals("shape"))
					{
						Point2D point = new Point2D.Double(Double.valueOf(atts.getValue("xCoordinate")).doubleValue(),Double.valueOf(atts.getValue("yCoordinate")));
						ShapeItem item = ItemFactory.getShapeItem(board.getSport(), atts.getValue("type"), point);
						if (item != null)
						{
							board.addItem(item);
						}
					}

					if (qName.equals("arrow"))
					{
						if (actArrowType != null)
						{
							ArrowItem item = ItemFactory.getArrowItem(board.getSport(), actArrowType, actPoints);
							if (item != null)
							{
								board.addItem(item);
							}
							actArrowType = null;
							actPoints.clear();
						}
						actArrowType = atts.getValue("type");
					}
					
					if (qName.equals("field"))
					{
						for (Field field: board.getSport().getFields()){
							if (field.getName().equals(atts.getValue("name"))){
								board.setField(field);
							}
						}
					}

					if (qName.equals("point"))
					{
						actPoints.add(new Point2D.Double(Double.valueOf(atts.getValue("xCoordinate")).doubleValue(),Double.valueOf(atts.getValue("yCoordinate"))));
					}
				}
			}

			public void endDocument() throws SAXException
			{
				if (actArrowType != null)
				{
					ArrowItem item = ItemFactory.getArrowItem(board.getSport(),
							actArrowType, actPoints);
					if (item != null)
					{
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
		System.out.println(path);
		actBoard.setPath(path);
		board = null;

		return actBoard;

	}

	public static ArrayList<Sport> getSports()
	{
		ArrayList<String> installedSports = FileSystemHandler
				.getInstalledSports();

		sports.clear();
		for (int i = 0; i < installedSports.size(); i++)
		{
			openSport(installedSports.get(i));
		}

		return sports;
	}

	private static void openSport(String sport)
	{
		class SaxHandler extends DefaultHandler
		{
			private ArrayList<ItemType> shapes = new ArrayList<ItemType>();
			private ArrayList<ItemType> arrows = new ArrayList<ItemType>();
			private ArrayList<Field> fields = new ArrayList<Field>();
			private Sport actSport;

			public void loadSport(String sport)
			{
				DefaultHandler handler = new SaxHandler();

				try
				{
					SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
					String filePath = "src/ch/tbe/config/sport/" + sport+ "/sport.config";
					saxParser.parse(new File(filePath), handler);
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}

			public void startElement(String name, String localName,
					String qName, Attributes atts) throws SAXException
			{
				if (qName.equals("sport"))
				{
					actSport = new Sport(atts.getValue("name"));
					actSport.setVersion(atts.getValue("version"));
					actSport.setLcVersion(atts.getValue("lastCompatibleVersion"));
					actSport.setIcon(new ImageIcon("src/ch/tbe/config/sport/"+ actSport.getName() + "/"	+ atts.getValue("picture")));
				}

				if (actSport != null)
				{
					if (qName.equals("shape"))
					{
						URL imgURL = TBE.class.getResource("../config/sport/"+ actSport.getName() + "/"+ atts.getValue("icon"));
						Icon actIcon = new ImageIcon(imgURL);
						imgURL = TBE.class.getResource("../config/sport/"+ actSport.getName() + "/" + atts.getValue("picture"));
						Icon actPicture = new ImageIcon(imgURL);
						shapes.add(new ItemType(atts.getValue("name"), atts.getValue("description"), actIcon, actPicture));
					}

					if (qName.equals("arrow"))
					{
						URL imgURL = TBE.class.getResource("../config/sport/"+ atts.getValue("picture"));
						if (imgURL != null)
						{
							Icon actIcon = new ImageIcon(imgURL);
							arrows.add(new ItemType(atts.getValue("name"),atts.getValue("description"), actIcon));
						}
					}

					if (qName.equals("field"))
					{
						URL imgURL = TBE.class.getResource("../config/sport/"+ actSport.getName() + "/"	+ atts.getValue("picture"));
						Icon actIcon = null;
						try{
						actIcon = new ImageIcon(imgURL);}
						catch(Exception e){
							
						}
						fields.add(new Field(atts.getValue("name"), atts.getValue("description"), actIcon));
					}
				}
			}

			public void endDocument() throws SAXException
			{
				actSport.setShapeTypes(shapes);
				actSport.setArrowTypes(arrows);
				actSport.setFields(fields);
				addSport(actSport);
			}
		}

		SaxHandler xml = new SaxHandler();
		xml.loadSport(sport);
	}

	private static void addSport(Sport sport)
	{
		sports.add(sport);
	}

	/**
	 * Diese Funktion wird zur Kommunikation zwischen dem SaxHandler und der Funktion readXML gebraucht.
	 * @param myBoard
	 */
	private static void setBoard(Board myBoard)
	{
		board = myBoard;
	}

	public static void saveSettings(String prename, String lastname, String email, String language)
	{
		// TODO: saveSettings
	}

	public void saveFTPServers(ArrayList<FTPServer> servers)
	{
		// TODO: save Servers
	}

	public static void saveBoard(Board board)
	{
		if (board.getPath().equals(""))
		{
			JFileChooser chooser = new JFileChooser();

			chooser.setFileFilter(new FileFilter()
			{
				public boolean accept(File f)
				{
					return f.getName().toLowerCase().endsWith(".tbe") || f.isDirectory();
				}

				public String getDescription()
				{
					return "TBE (*.tbe)";
				}
			});
			chooser.showSaveDialog(new Frame());

			File filename = chooser.getSelectedFile();
			board.setPath(filename.getPath());

			// TODO: Dateiendung immer .TBE
		}

		if (!board.getPath().equals(""))
		{
			try
			{
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
				
				for (Attribute attribute : board.getDescription()
						.getAttributes())
				{
					Element eAttribute = new Element("attribute");
					eAttribute.setAttribute("title", attribute.getTitle());
					eAttribute.setAttribute("text", attribute.getText());
					eDescription.addContent(eAttribute);
				}

				for (ItemComponent item : board.getItems())
				{
					Element eItemComponent;

					if (item instanceof ArrowItem)
					{
						eItemComponent = new Element("arrow");
						eItemComponent.setAttribute("type", item.getType());
						for (Point2D point : ((ArrowItem) item).getPoints())
						{
							Element ePoint = new Element("point");
							ePoint.setAttribute("xCoordinate", String.valueOf(point.getX()));
							ePoint.setAttribute("yCoordinate", String.valueOf(point.getY()));
							eItemComponent.addContent(ePoint);
						}
					}
					else
					{
						eItemComponent = new Element("shape");
						eItemComponent.setAttribute("type", item.getType());

						double widthDiff = TBEGraphConstants.getBounds(((ShapeItem) item).getAttributes()).getWidth() / 2;
						double heightDiff = TBEGraphConstants.getBounds(((ShapeItem) item).getAttributes()).getHeight() / 2;
						
						// TODO: Speichern der Gr�sse, und des Drehwinkels
						
						eItemComponent.setAttribute("xCoordinate", String.valueOf(TBEGraphConstants.getBounds(((ShapeItem) item).getAttributes()).getCenterX() - widthDiff));
						eItemComponent.setAttribute("yCoordinate", String.valueOf(TBEGraphConstants.getBounds(((ShapeItem) item).getAttributes()).getCenterY() - heightDiff));
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

			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
