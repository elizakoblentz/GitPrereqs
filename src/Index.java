import java.util.*;
import java.io.*;

public class Index {
	HashMap<String, String> files;
	
	public Index () {
		files = new HashMap<String, String>();
		File theDir = new File("/objects");
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
	}
	
	public void init () throws Exception {
		FileOutputStream out = new FileOutputStream("index");
		
		out.close();
	}
	
	public void add (String fileName) throws Exception {
		Blob added = new Blob (fileName);
		files.put(fileName, added.sha1Code(fileName));
		update();
		
	}
	
	private void update () throws Exception {
		FileOutputStream clearer = new FileOutputStream("index");
		clearer.close();
		PrintWriter out = new PrintWriter("index");
		
		for (String key : files.keySet()) {
			out.println(key + " : " + files.get(key));
			//System.out.println(key + " : " + files.get(key));
		}
		out.close();
	}
	
	public void remove (String fileName) throws Exception {
		String shaCode = files.get(fileName);
		files.remove(fileName);
		File removed = new File("objects/" + shaCode);
		removed.delete();
		update();
	}
	
	public static void main (String[]args) throws Exception {
		Index indy = new Index();
		indy.init();
		indy.add("test.txt");
		indy.add("test2.txt");
		indy.remove("test.txt");
	}
	
	
}
