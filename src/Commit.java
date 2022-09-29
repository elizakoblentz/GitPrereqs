
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
	private String pTree;
	private String summary;
	private String author;
	private String date;
	private String treeSha;
	private String fileName;
	
	public Commit (String summary, String author, String parent) throws Exception {
		File theDir = new File("/objects");
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		
		this.parent = parent;
		this.next = null;
		this.summary = summary;
		this.author = author;
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
		Date date = new Date();
		this.date = formatter.format(date);
		
		fileName = createFileName();
		File file = new File(fileName);
		
		
		generateTree();
		writeFile(file);
		
		if (parent != null)
		{
			changeParentChildToMe(parent);
		}
		clearIndex();
	}
	
	public String getDate() {
		return this.date;
	}
	
	public void generateTree() throws IOException
	{
		ArrayList<String> arr = makeIndexArrayList();
		if (parent != null)
		{
			BufferedReader br = new BufferedReader(new FileReader(parent));
			String fullLine = br.readLine();
			String parTreeSha = fullLine.substring(fullLine.indexOf("/")+1);
			Tree tree = new Tree (arr, parTreeSha);
			treeSha = tree.getTreeSha();
		}
		else
		{
			Tree tree = new Tree (arr, null);
			treeSha = tree.getTreeSha();
		}
	}
	
	public ArrayList<String> makeIndexArrayList() throws IOException
	{
		ArrayList<String> c =new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("index"));
		while (br.ready())
		{
			c.add(br.readLine());
		}
		return c;
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
		content += fileName + "\n";
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
		out.println("objects/"+treeSha);
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
	public void clearIndex() throws IOException
	{
		FileWriter fwOb = new FileWriter("index", false); 
		PrintWriter pwOb = new PrintWriter(fwOb, false);
		pwOb.flush();
		pwOb.close();
		fwOb.close();
	}
	
	
	public static void main (String[]args) throws Exception{
		/*
		Blob blob1 = new Blob ("ElizaTesterBlob1.txt");
		Blob blob2 = new Blob ("ElizaTesterBlob2.txt");
		Blob blob3 = new Blob ("ElizaTesterBlob3.txt");
		*/
		
		Index index1 = new Index();
		index1.init();
		index1.add("ElizaTesterBlob1.txt");
		index1.add("ElizaTesterBlob2.txt");
		index1.add("ElizaTesterBlob3.txt");
		
		Commit commit1 = new Commit ("this is my summary!", "Eliza Koblentz", null);

		Index index2 = new Index();
		index2.init();
		index2.add("ElizaTesterBlob1.txt");
		index2.add("ElizaTesterBlob2.txt");
		index2.add("ElizaTesterBlob3.txt");
		
		Commit commit2 = new Commit ("this is my second summary!", "Eliza Koblentz", commit1.createFileName());
		
		/*
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
		 */
	}
	
}
