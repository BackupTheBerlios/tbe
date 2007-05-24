package ch.tbe.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.tbe.Board;
import ch.tbe.FTPServer;
import ch.tbe.Sport;
import ch.tbe.gui.Menu;
import ch.tbe.gui.TBE;

import java.io.*;
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
		List<Sport> sports = new ArrayList<Sport>();
		
		// TO DO:
		// sportarten auslesen und für jede sportart openSport ausführen
		sports.add(openSport("path"));
		
		return sports;
	}
	
	private Sport openSport(String path) {
		class SaxHandler extends DefaultHandler{
			private TBE tbe = TBE.getInstance();
			private List<FTPServer> servers = new ArrayList<FTPServer>();
			private Sport actSport;
			
			public Sport loadSport(String filename){
				DefaultHandler handler = new SaxHandler();
				
				try{
					SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
					saxParser.parse( new File(filename), handler );
				}catch( Throwable t ) {
					t.printStackTrace();
				}
				return actSport;
			}
			
			public void startElement(String name, String localName, String qName, Attributes atts) throws SAXException {
				if (qName.equals("sport")){
					atts.getValue("prename").toString();
				}	
			}
		}
		
		SaxHandler xml = new SaxHandler();
		return xml.loadSport("");
	}
	 
	public void saveSettings(String prename, String lastname, String email, String language) {
	}
	 
}
 
