package ch.tbe.util;

import java.io.* ;
import java.util.ArrayList;
import java.util.List;

public class FileSystemHandler
{
	private static FileSystemHandler instance = null;
	 
	private FileSystemHandler(){}
	
	public static FileSystemHandler getInstance() {
		if(instance == null) {
			instance = new FileSystemHandler();
		}
		return instance;
	}
	public List<String> getInstalledSports(String strSportDirectory){
		List<String> sports = new ArrayList<String>();
		 
		// Create a file object for your root directory
		File f1 = new File ( strSportDirectory ) ;
		
		// Get all the files and directory under your diretcory
		File[] strFilesDirs = f1.listFiles ( );
		 
		for ( int i = 0 ; i < strFilesDirs.length ; i ++ ) {
			if ( strFilesDirs[i].isDirectory()){ 
				sports.add(strFilesDirs[i].toString());
			}
		}
		return sports;
	}
}