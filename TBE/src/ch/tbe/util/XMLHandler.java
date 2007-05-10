package ch.tbe.util;

import ch.tbe.Board;

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
	 
	public void saveUser(String prename, String lastname, String email, String language) {
	}
	 
}
 
