package ch.tbe.util;

import java.io.* ;
import java.util.ArrayList;
import java.util.List;

import ch.tbe.gui.Menu;

public final class FileSystemHandler
{
	private FileSystemHandler(){}
	
	public static ArrayList<String> getInstalledSports(){
		ArrayList<String> sports = new ArrayList<String>();
		 
		// Create a file object for your root directory
		String folderPath = Menu.class.getResource("../config/sport").getPath();
		File f1 = new File(folderPath);
		
		// Get all the files and directory under your diretcory
		File[] strFilesDirs = f1.listFiles ( );
		
		for ( int i = 0 ; i < strFilesDirs.length ; i ++ ) {
			if ( strFilesDirs[i].isDirectory()){ 
				sports.add(strFilesDirs[i].getName());
			}
		}
		return sports;
	}
}