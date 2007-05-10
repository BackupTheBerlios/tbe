package ch.tbe.util;

import java.util.List;

public class FTPHandler {
	private static FTPHandler instance = null;
 
	private void FTPHandler(){}
	
	public static FTPHandler getInstance() {
	      if(instance == null) {
	         instance = new FTPHandler();
	      }
	      return instance;
	   }

	
	public List getAllSports() {
		return null;
	}
	 
	public void installSport(List sports) {
	}
	 
	public void connect() {
	}
	 
	public void upload(String path) {
	}
	 
	public void download(String path) {
	}
	 
}
 
