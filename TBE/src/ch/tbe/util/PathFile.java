package ch.tbe.util;

import java.io.File;

public class PathFile extends File
{
    public PathFile(File arg0, String arg1) {
	super(arg0, arg1);
    }
    
    public PathFile(String arg0) {
	super(arg0);
    }
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public String toString() 
    {
	String path = super.toString();
	path = path.replaceAll("\\\\", "/");
	return path.substring(path.lastIndexOf("/")+1, path.length());
    }
}
