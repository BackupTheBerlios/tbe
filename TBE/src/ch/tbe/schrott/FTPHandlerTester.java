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
		 *  	tbe_admin     pass: 4quabwej
		 *	tbe_public      pass: carmad4
		 */
	    
	    	// TODO: TEST CAPS-SERVER ! (Custom!)
	    	
	    	FTPServer server = new FTPServer("Berncapitals", "ftp.berncapitals.ch", "berncapitals", "caps2002");
	    	FTPHandler.connect(server);
	    	
	    	ArrayList<String> capsDir = FTPHandler.getDir("include");
	    	for(String s : capsDir)
	    	{
	    	    System.out.println(s);
	    	}
		
		// FTPServer server = new FTPServer("Public", "tbe.netstyle.ch", "tbe_admin", "4quabwej");
		//FTPHandler.connect(server);
	    	/*
		String[] test = new File("src/ch/tbe/config/sport").list();
		System.out.println(test.length);
		for(int i = 0; i < test.length; i++)
		{
			System.out.println(test[i]);
		}
		
		boolean bool = new File("src/ch/tbe/config/sport/double.gif").delete();
		System.out.println(bool);
		
		bool = new File("src/ch/tbe/config/sport/soccer").delete();
		System.out.println(bool);
		 */
	    	
		/*
		ArrayList<String> pathesL = new ArrayList<String>();
		ArrayList<String> pathesR = new ArrayList<String>();
		
		pathesL.add("D:/webkey.txt");
		pathesR.add("boards/webkey.txt");
		
		FTPHandler.upload(server, pathesL, pathesR);
		*/
		
		/*
		ArrayList<String> dir_fs = FTPHandler.getDir("boards\floorball");
		for(String s : dir_fs)
		{
			System.out.println(s);
		}
		
		ArrayList<String> dir_bs = FTPHandler.getDir("boards/floorball");
		for(String s : dir_bs)
		{
			System.out.println(s);
		}
		*/
		//FTPServer server = new FTPServer("Public", "tbe.netstyle.ch", "tbe_public", "carmad4");
		
		// FTPHandler.connect(server);
		
		/*
		String[] s = FTPHandler.getDir(server, "test");
		for(int i = 0; i < s.length; i++)
		{
			System.out.println(s[i]);
		}
		
		// FTPHandler.upload(server, "src/ch/tbe/config/sport/double.png", "sport/double.png");
		*/
		
		/*
		FTPHandler.download(server, "C:/romy/index.html", "boards/index.html");
		// inkl. Ordner erstellen!
		FTPHandler.download(server, "C:/romy/test/index.html", "boards/index.html");
		FTPHandler.download(server, "C:/romy/test/test/index.html", "boards/index.html");
		
		ArrayList<String> localPaths = new ArrayList<String>();
		localPaths.add("D:/webkey.txt");
		localPaths.add("D:/BFH-Zugriffsdaten.pdf");
		
		ArrayList<String> remotePaths = new ArrayList<String>();
		remotePaths.add("test/tbe/webkey.txt");
		remotePaths.add("test/tbe/BFH-Zugriffsdaten.pdf");
		
		 */
		// FTPHandler.upload(server, localPaths, remotePaths);
		// FTPHandler.upload(server, remotePaths, localPaths);
		/*
		ArrayList<String> sports = FTPHandler.getAllSports();
		for(String s : sports)
		{
			System.out.println(s);
		}
		*/
		
		/*
		ArrayList<String> NEWsports = new ArrayList<String>();
		sports.add("football");
		sports.add("floorball");
		
		FTPHandler.installSport(NEWsports);
		*/
	}
}
