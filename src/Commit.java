
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
	private ArrayList<String> blobsNotDeleted;
	
	public Commit (String summary, String author) throws Exception {
		File theDir = new File("/objects");
		if (!theDir.exists()){
		    theDir.mkdirs();
		}

		this.summary = summary;
		this.author = author;
		blobsNotDeleted = new ArrayList<String>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
		Date date = new Date();
		this.date = formatter.format(date);
		
		fileName = createFileName();
		File file = new File(fileName);
		
		if (getHead() == null)
		{
			System.out.println ("Head in construtor: " + getHead());
			parent = null;
		}
		else
		{
			System.out.println (getHead());
			parent = getHead();
		}
		
		changeHead();
		
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
		System.out.println (fileName);
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
	
	
	public void checkTreeForFileAndDelete(String tree, String fileName) throws IOException
	{
		if (parent != null)
		{
			int c = 0;
			BufferedReader br = new BufferedReader(new FileReader(parent));
			String fullLine = br.readLine();
			String parTreeSha = fullLine.substring(fullLine.indexOf("/")+1);
			
			BufferedReader buf = new BufferedReader (new FileReader("objects/" + parTreeSha));
			String parTree = buf.readLine();
			
			if (parTree.indexOf("tree") == -1 && parTree.indexOf(fileName) == -1)
			{
				blobsNotDeleted.add(parTree);
			}
			else if (parTree.indexOf("tree") == -1 && parTree.indexOf(fileName) != -1)
			{
				c++;
			}
			
			while (buf.ready())
			{
				String temp = buf.readLine();
				if (temp.indexOf(fileName) == -1)
				{
					blobsNotDeleted.add(temp);
				}
				if (temp.indexOf(fileName) != -1)
				{
					c++;
				}
			}
			if (c == 1)
			{
				String content = "";
				
				for (int index = 0; index < blobsNotDeleted.size(); index++)
				{
					content +=  blobsNotDeleted.get(index) + "\n"; 
				}
				/*
				Path p = Paths.get("objects/" + treeSha);
		        try {
		            Files.writeString(p, content, StandardCharsets.ISO_8859_1);
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		        */
				FileWriter writer = new FileWriter(new File("objects/" + treeSha), true );
				
				for (int index = 0; index < blobsNotDeleted.size(); index++)
				{
					writer.append(blobsNotDeleted.get(index) + "\n"); 
				}
				writer.close();
			}
			else
			{
				checkTreeForFileAndDelete(parTree, fileName);
			}
		}
	}
	
	
	public void changeHead() throws IOException
	{
		
		PrintWriter writer = new PrintWriter("HEAD");
		writer.flush();
		System.out.println ("First line:" + fileName);
		writer.println(fileName.substring(fileName.indexOf("/")+1));
		System.out.println ("file name: " + fileName.substring(fileName.indexOf("/")+1));
		BufferedReader br = new BufferedReader(new FileReader("HEAD"));
		String temp = br.readLine();
		System.out.println ("line of head:" + temp);
		writer.close();
		
	}
	
	public String getHead() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("HEAD"));
		String temp = br.readLine();
		br.close();
		
		return temp;
	}
	
	public static void main (String[]args) throws Exception
	{	
		Index index1 = new Index();
		
		index1.init();
		
		index1.add("ElizaTesterBlob1.txt");
		index1.add("ElizaTesterBlob2.txt");
		index1.add("ElizaTesterBlob3.txt");
		Commit commit1 = new Commit ("this is my summary!", "Eliza Koblentz");
		
		
		
		Index index2 = new Index();
		index2.init(); 
		index2.add("ElizaTesterBlob4.txt");
		index2.add("ElizaTesterBlob5.txt");
		index2.add("ElizaTesterBlob6.txt");
		
		Commit commit2 = new Commit ("this is my second summary!", "Eliza Koblentz");
		
		//commit2.checkTreeForFileAndDelete("b9783d1a7510f1b98e3592c23ba91675db9837e0", "ElizaTesterBlob1.txt");
		
	}
	
}
