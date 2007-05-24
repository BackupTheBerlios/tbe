package ch.tbe.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.tbe.ArrowType;
import ch.tbe.Board;
import ch.tbe.FTPServer;
import ch.tbe.Field;
import ch.tbe.ShapeType;
import ch.tbe.Sport;
import ch.tbe.gui.Menu;
import ch.tbe.gui.TBE;

import java.io.*;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;


public class XMLHandler{
	private static XMLHandler instance = null;
	private XMLHandler(){}
	
	public static XMLHandler getInstance() {
	      if(instance == null) {
	         instance = new XMLHandler();
	      }
	      return instance;
	   }
 
	public void loadTBESettings(){
		
		class SaxHandler extends DefaultHandler{
			private TBE tbe = TBE.getInstance();
			private List<FTPServer> servers = new ArrayList<FTPServer>();
			
			public void loadTBESettings(){
				DefaultHandler handler = new SaxHandler();
				
				try{
					SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
					saxParser.parse( new File("src/ch/tbe/config/tbe.config"), handler );
				}catch( Throwable t ) {
					t.printStackTrace();
				}
			}
			
			public void startElement(String name, String localName, String qName, Attributes atts) throws SAXException {
				if (qName.equals("trainer")){
					tbe.setUser(atts.getValue("prename").toString(), atts.getValue("name").toString(), atts.getValue("name").toString());
				}
				if (qName.equals("defaultLanguage")){
					tbe.setLang(atts.getValue("name").toString());
				}
				if (qName.equals("server")){
					servers.add(new FTPServer(atts.getValue("name").toString(), atts.getValue("host").toString(), Integer.valueOf(atts.getValue("port")).intValue(), atts.getValue("username").toString(), atts.getValue("password").toString()));
				}	
			}
			
			public void endDocument() throws SAXException {
				tbe.setFTPServers(servers);
			}
		}
		
		SaxHandler xml = new SaxHandler();
		xml.loadTBESettings();
	}

	
	
	
	
	public void createXML(Board board) {
	}
	
	public Board openXML(String path) {
		return null;
	}
	
	public List<Sport> getSports(){
		ArrayList<Sport> sports = new ArrayList<Sport>(); 
		ArrayList<String> installedSports = FileSystemHandler.getInstalledSports();
		
		for (int i=0; i<= installedSports.size() -1; i++){
			sports.add(openSport(installedSports.get(i)));
		}
		
		return sports;
	}
	
	private Sport openSport(String sport) {
		class SaxHandler extends DefaultHandler{
			private TBE tbe = TBE.getInstance();
			private ArrayList<ShapeType> shapes = new ArrayList<ShapeType>();
			private ArrayList<ArrowType> arrows = new ArrayList<ArrowType>();
			private ArrayList<Field> fields = new ArrayList<Field>();
			private Sport actSport;
			
			public Sport loadSport(String sport){
				DefaultHandler handler = new SaxHandler();
				
				try{
					SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
					saxParser.parse( new File("src/ch/tbe/config/sport/"+sport+"/sport.config"), handler );
				}catch( Throwable t ) {
					t.printStackTrace();
				}
				return actSport;
			}
			
			public void startElement(String name, String localName, String qName, Attributes atts) throws SAXException {
				if (qName.equals("sport")){
					actSport = new Sport(atts.getValue("name"));
					actSport.setVersion(atts.getValue("version"));
					actSport.setLcVersion(atts.getValue("lastCompatibleVersion"));
				}

				if (actSport != null){
					if (qName.equals("shape")){
						URL imgURL = TBE.class.getResource("../config/sport/"+actSport.getName()+"/"+ atts.getValue("picture"));
						Icon actIcon = new ImageIcon(imgURL);
						shapes.add(new ShapeType(atts.getValue("name"), actIcon, atts.getValue("description")));
					}
					
					if (qName.equals("arrow")){
						arrows.add(new ArrowType(atts.getValue("type"), atts.getValue("description")));
					}
					
					if (qName.equals("field")){
						fields.add(new Field(atts.getValue("name"), atts.getValue("picture")));
					}
				}
			}
			
			public void endDocument() throws SAXException {
				actSport.setShapeTypes(shapes);
				actSport.setArrowTypes(arrows);
				actSport.setFields(fields);
			}
		}
		
		SaxHandler xml = new SaxHandler();
		return xml.loadSport("");
	}
	 
	public void saveSettings(String prename, String lastname, String email, String language) {
	}
	 
}
 
