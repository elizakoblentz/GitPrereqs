import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		
		Path p = Paths.get("HEAD");
        try {
            Files.writeString(p, "", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public void add (String fileName) throws Exception {
		Blob added = new Blob (fileName);
		files.put(fileName, added.sha1Code(fileName));
		update();
		
	}
	
	private void update () throws Exception 
	{
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
		files.put("*deleted*", shaCode);
		File removed = new File("objects/" + shaCode);
		removed.delete();
		update();
	}
	
	
	
	public static void main (String[]args) throws Exception {
		
	}
	
	
}
