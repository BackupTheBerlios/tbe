package ch.tbe.schrott;

import java.util.ArrayList;

import ch.tbe.FTPServer;
import ch.tbe.util.FTPHandler;

public class FTPHandlerTester
{
	public static void main(String[] args)
	{
		FTPServer server = new FTPServer("capsTest", "ftp.berncapitals.ch",
				"testberncapitals", "hovcapyod9");
		
		// FTPHandler.connect(server);
		
		/*
		String[] s = FTPHandler.getDir(server, "test");
		for(int i = 0; i < s.length; i++)
		{
			System.out.println(s[i]);
		}
		*/
		
		// FTPHandler.upload(server, "D:/webkey.txt", "test/tbe/test.txt");
		
		// FTPHandler.download(server, "test/tbe/test.txt", "D:/webkey.txt");
		
		ArrayList<String> localPaths = new ArrayList<String>();
		localPaths.add("D:/webkey.txt");
		localPaths.add("D:/BFH-Zugriffsdaten.pdf");
		
		ArrayList<String> remotePaths = new ArrayList<String>();
		remotePaths.add("test/tbe/webkey.txt");
		remotePaths.add("test/tbe/BFH-Zugriffsdaten.pdf");
		
		// FTPHandler.upload(server, localPaths, remotePaths);
		// FTPHandler.upload(server, remotePaths, localPaths);
		
		ArrayList<String> sports = FTPHandler.getAllSports();
		for(String s : sports)
		{
			System.out.println(s);
		}
		
		
	}
}
