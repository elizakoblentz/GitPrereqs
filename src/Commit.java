
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Commit {
	private String parent;
	private String next;
	private Tree tree;
	private String pTree;
	private String summary;
	private String author;
	private String date;
	private String fileName;
	
	public Commit (Tree tree, String summary, String author, String parent) throws Exception {
		File theDir = new File("/objects");
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		
		this.parent = parent;
		this.next = null;
		this.tree = tree;
		pTree = tree.getTreeSha();
		this.summary = summary;
		this.author = author;
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
		Date date = new Date();
		this.date = formatter.format(date);
		
		fileName = createFileName();
		File file = new File(fileName);
		writeFile(file);
	
		if (parent != null)
		{
			changeParentChildToMe(parent);
		}
		
		
	}
	
	public String getDate() {
		return this.date;
	}
	
	public void setNext(String next) throws Exception{
		this.next = next;
		File file = new File(createFileName());
		writeFile(file);
	}
	
	public void changeParentChildToMe(String parent) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(parent));
		String content = "";
		for (int index = 0; index < 2; index++)
		{
			content += br.readLine()+"\n";
		}
		content += "objects/" + fileName + "\n";
		br.readLine();
		for (int index = 0; index < 2; index++)
		{
			content += br.readLine() + "\n";
		}
		content += br.readLine();
		
		File f = new File (parent);
		f.delete();
		
		Path p = Paths.get(parent);
        try {
            Files.writeString(p, content, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	//gets sha1Code of parameter String
	public String sha1Code (String value) {
		String sha1 = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(value.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}
		return sha1;
	}
	
	public String createFileName() {
		String input = summary + getDate() + author + parent;
		return "objects/" + sha1Code(input);
	}
	
	public void writeFile(File file) throws Exception {
	PrintWriter out = new PrintWriter(file);
	out.println(pTree);
	if (parent != null)
	{
		out.println(parent);
	}
	else
	{
		out.println("");
	}
	if (next != null)
	{
		out.println(next);
	}
	else
	{
		out.println ("");
	}
	out.println(author);
	out.println(getDate());
	out.println(summary);
	out.close();
	}
	
	
	public static void main (String[]args) throws Exception{
		ArrayList<String> test = new ArrayList<String>();
		
		test.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		test.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		test.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		test.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
		test.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
		//Tree treeTest = new Tree(test);
		
		//Commit commit1 = new Commit("objects/dd4840f48a74c1f97437b515101c66834b59b1be", "this is cool", "charlie seymour", null);
		//Commit commit2 = new Commit("objects/dd4840f48a74c1f97437b515101c66834b59b1be", "wow, really cool", "charlie seymour", commit1.createFileName());
		//commit1.setNext(commit2.createFileName());
		//Commit commit3 = new Commit ("objects/dd4840f48a74c1f97437b515101c66834b59b1be", "cool! this is", "charlie seymour", commit2.createFileName());
		//Commit commit4 = new Commit ("objects/dd4840f48a74c1f97437b515101c66834b59b1be", "is this cool?", "charlie seymour", commit3.createFileName());
		//commit2.setNext(commit3.createFileName());
	}
	
}
