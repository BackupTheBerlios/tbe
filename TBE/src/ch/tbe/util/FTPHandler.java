package ch.tbe.util;

import java.io.IOException;
import java.util.ArrayList;

import ch.tbe.FTPServer;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;

public final class FTPHandler 
{
	private FTPHandler()
	{
		
	}

	public static ArrayList<String> getAllSports()
	{
		return null;
	}

	public static void installSport(ArrayList<String> sports)
	{
		
	}
	
	public static void disconnect(FTPClient client)
	{
		try
		{
			System.out.println("quit");
			client.quit();
		}
		catch (IOException e)
		{
			System.out.println("quit IO");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			System.out.println("quit FTPEx");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static FTPClient connect(FTPServer server)
	{
		FTPClient client = new FTPClient();
		try
		{
			client.setRemoteHost(server.getHost());
			client.connect();
			System.out.println("login");
			client.login(server.getUsername(), server.getPassword());
		}
		catch (IOException e)
		{
			System.out.println("connect IO");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			System.out.println("client is already connected to the server");
			e.printStackTrace();
		}
		return client;
	}
	
	public static String[] getDir(FTPServer server, String dir)
	{
		FTPClient client = connect(server);
		
		String[] s = new String[]{};
		
		try
		{
			s = client.dir(dir);
		}
		catch (IOException e)
		{
			System.out.println("IO getDir");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			System.out.println("FTPex getDir");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			disconnect(client);
		}
		return s;
	}
	
	public static void upload(FTPServer server, String localPath, String remotePath)
	{
		FTPClient client = connect(server);
		
		try
		{
			client.put(localPath, remotePath);
		}
		catch (IOException e)
		{
			System.out.println("IO upload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			System.out.println("FTPex download");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			disconnect(client);
		}
	}

	public static void download(String path)
	{
		
	}
}
