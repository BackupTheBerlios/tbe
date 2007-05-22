package ch.tbe.util;

import ch.tbe.Board;

public class PrintHandler {
	private static PrintHandler instance = null;
	 
	private PrintHandler(){}
	
	public static PrintHandler getInstance() {
	      if(instance == null) {
	         instance = new PrintHandler();
	      }
	      return instance;
	   }
 
	public void print(Board board) {
	}
	 
	public void export(Board board) {
	}
	 
}
 
