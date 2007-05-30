package ch.tbe.schrott;

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
		FTPHandler.upload(server, "D:/webkey.txt", "test/tbe/test.txt");
		
		
	}
}
