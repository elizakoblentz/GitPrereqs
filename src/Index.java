import java.util.*;
import java.io.*;

public class Index {
	HashMap<String, String> files;
	
	public Index () {
		files = new HashMap<String, String>();
	}
	
	public void init () throws Exception {
		FileOutputStream out = new FileOutputStream(System.getProperty("user.dir") + "/" + "index");
		
		out.close();
	}
	
	public static void main (String[]args) throws Exception {
		Index indy = new Index();
		indy.init();
	}
	
	
}
