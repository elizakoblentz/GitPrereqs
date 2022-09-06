import java.util.*;
import java.io.*;

public class Index {
	HashMap<String, String> files;
	
	public Index () {
		files = new HashMap<String, String>();
		File theDir = new File(System.getProperty("user.dir") + "/objects");
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
	}
	
	public void init () throws Exception {
		FileOutputStream out = new FileOutputStream(System.getProperty("user.dir") + "/" + "index");
		
		out.close();
	}
	
	public void add (String fileName) throws Exception {
		//files.put(fileName, fileName)
		PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "/" + "index");
		out.close();
		
	}
	
	public static void main (String[]args) throws Exception {
		Index indy = new Index();
		indy.init();
	}
	
	
}
