package ch.tbe.util;

import java.util.List;

import ch.tbe.Board;
import ch.tbe.Sport;

public class XMLHandler {
	private static XMLHandler instance = null;
	 
	private void XMLHandler(){}
	
	public static XMLHandler getInstance() {
	      if(instance == null) {
	         instance = new XMLHandler();
	      }
	      return instance;
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
 
