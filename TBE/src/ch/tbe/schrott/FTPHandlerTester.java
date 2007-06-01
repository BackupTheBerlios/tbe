package ch.tbe.schrott;

import java.util.ArrayList;

import ch.tbe.FTPServer;
import ch.tbe.util.FTPHandler;

public class FTPHandlerTester
{
	public static void main(String[] args)
	{
		/*
		 * PUBLIC - Server
		 * 	domain: tbe.netstyle.ch
		 *	users: 
		 *  tbe_admin     pass: 4quabwej
		 *	tbe_public      pass: carmad4
		 */
		
		FTPServer server = new FTPServer("Public", "tbe.netstyle.ch", "tbe_admin", "4quabwej");
		//FTPServer server = new FTPServer("Public", "tbe.netstyle.ch", "tbe_public", "carmad4");
		
		// FTPHandler.connect(server);
		
		/*
		String[] s = FTPHandler.getDir(server, "test");
		for(int i = 0; i < s.length; i++)
		{
			System.out.println(s[i]);
		}
		/*
		
		// FTPHandler.upload(server, "src/ch/tbe/config/sport/double.png", "sport/double.png");
		
		// FTPHandler.download(server, "src/ch/tbe/config/sport/test.txt", "sport/test.txt");
		// inkl. Ordner erstellen!
		// FTPHandler.download(server, "src/ch/tbe/config/sport/test/test.txt", "sport/test.txt");
		// FTPHandler.download(server, "src/ch/tbe/config/sport/test/test.txt", "sport/test.txt");
		
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
		/*
		ArrayList<String> NEWsports = new ArrayList<String>();
		sports.add("football");
		sports.add("floorball");
		
		FTPHandler.installSport(NEWsports);
		*/
	}
}
