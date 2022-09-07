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
	
	public void add (String filePath) throws Exception {
		Blob added = new Blob (filePath);
		files.put(filePath, added.sha1Code(filePath));
		update();
		
	}
	
	private void update () throws Exception {
		FileOutputStream clearer = new FileOutputStream(System.getProperty("user.dir") + "/" + "index");
		clearer.close();
		PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "/" + "index");
		
		for (String key : files.keySet()) {
			out.println(key + " : " + files.get(key));
			//System.out.println(key + " : " + files.get(key));
		}
		out.close();
	}
	
	public void remove (String filePath) throws Exception {
		String shaCode = files.get(filePath);
		files.remove(filePath);
		File removed = new File(System.getProperty("user.dir") + "/objects/" + shaCode);
		removed.delete();
		update();
	}
	
	public static void main (String[]args) throws Exception {
		Index indy = new Index();
		indy.init();
		indy.add("/Users/charlesseymour/eclipse-workspace/GitPrereqs/test.txt");
		indy.add("/Users/charlesseymour/eclipse-workspace/GitPrereqs/test2.txt");
		//indy.remove("/Users/charlesseymour/eclipse-workspace/GitPrereqs/test.txt");
	}
	
	
}
