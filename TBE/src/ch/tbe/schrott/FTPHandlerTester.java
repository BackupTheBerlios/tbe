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

		FTPHandler.getDir(server, "include");

	}
}
