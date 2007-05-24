package ch.tbe.util;

import java.util.List;

import ch.tbe.Board;
import ch.tbe.Sport;
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
			public void loadTBESettings(){
				DefaultHandler handler = new SaxHandler();
				try{
					SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
					saxParser.parse( new File("../config/tbe.config"), handler );
				}catch( Throwable t ) {
					t.printStackTrace();
				}
			}
			
			public void startElement(String name, Attributes atts) throws SAXException {
				if (name.equals("trainer")){
					//tbe.setUser(atts.getValue("prename").toString(), atts.getValue("name").toString(), atts.getValue("name").toString());
					System.out.println(atts.getValue("prename").toString());
				}
				
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
	
	public Sport openSport(String path) {
		return null;
	}
	 
	public void saveSettings(String prename, String lastname, String email, String language) {
	}
	 
}
 
