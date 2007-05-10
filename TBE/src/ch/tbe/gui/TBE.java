package ch.tbe.gui;

import java.util.ArrayList;
import ch.tbe.Sport;
import ch.tbe.Field;
import java.util.List;

import javax.swing.JFrame;



public class TBE
{

	private ArrayList sports;

	private String UserName;

	private String UserPrename;

	private String UserEmail;
	private final int HEIGHT = 300;
	private final int WIDTH = 600;

	public TBE()
	{
		this.init();
	}
	private void init(){
		JFrame f = new JFrame("TBE");
		f.setSize(this.WIDTH, this.HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}

	public ArrayList getSports()
	{
		return null;
	}

	public void createBoard(Sport sport)
	{
	}

	public void saveAs()
	{
	}

	public void save()
	{
	}

	public void load(String path)
	{
	}

	public void changeField(Field field)
	{
	}

	public void clear()
	{
	}

	public void load()
	{
	}

	public void share()
	{
	}

	public void undo()
	{
	}

	public void redo()
	{
	}

	public void openSettings()
	{
	}

	public void addFTPServer(String name, String host, int port,
			String username, String password)
	{
	}

	public void editFTPServer(String name, String host, int port,
			String username, String password)
	{
	}

	public void removeFTPServer(String name)
	{
	}

	public void setUser(String prename, String lastname, String email,
			String language)
	{
	}
	public static void main(String[] args) {
		new TBE();
	}

}
