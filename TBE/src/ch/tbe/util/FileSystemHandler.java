package ch.tbe.util;

import java.io.* ;
import java.util.ArrayList;
import java.util.List;

public final class FileSystemHandler
{
	private FileSystemHandler(){}
	
	public static ArrayList<String> getInstalledSports(){
		ArrayList<String> sports = new ArrayList<String>();
		 
		// Create a file object for your root directory
		File f1 = new File ("src/tbe/config/sport");
		
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