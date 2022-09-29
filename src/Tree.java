import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class Tree {
	private String content;
	private Tree parent;
	public ArrayList<String> indexSha;
	public ArrayList<String> indexFileName;
	
	ArrayList<String> indeces=new ArrayList<String>();
	
	String fileData="";
	public Tree(ArrayList<String> input, Tree parent) {
		indeces=input;
		indexSha = new ArrayList<String>();
		indexFileName = new ArrayList<String>();
		
		this.parent = parent;
		for(int i=0;i<indeces.size();i++) {
			fileData+=indeces.get(i);
			if(i+1<indeces.size()) {
				fileData+="\n";
			}
		}
		content = encryptThisString(fileData);
		System.out.println(fileData);
		
	}
	public void saveInfo() throws IOException {
		
		File tree=new File("objects/"+ content);
		tree.createNewFile();
		PrintWriter writer=new PrintWriter(tree);
		writer.print(fileData);
		writer.close();
		
	}
	
	public String getTreeSha()
	{
		return content;
	}
	
	public void getIndexContents () throws IOException
	{
		
		BufferedReader br = new BufferedReader(new FileReader("index"));
		String firstLine = br.readLine();
		System.out.println (firstLine);
		int indexOfColon = firstLine.indexOf(":");
		indexSha.add(firstLine.substring(0, indexOfColon-1));
		indexFileName.add(firstLine.substring(indexOfColon+2));
		
		while (br.ready())
		{
			String temp = br.readLine();
			indexSha.add(temp.substring(0, indexOfColon-1));
			indexFileName.add(temp.substring(indexOfColon+2));
		}
		
		for (int index = 0; index < indexSha.size(); index++)
		{
			System.out.println (indexSha.get(index));
		}
		
		for (int index = 0; index < indexFileName.size(); index++)
		{
			System.out.println (indexFileName.get(index));
		}
		System.out.println (indexSha.size());
		System.out.println (indexFileName.size());
	}
	
	
	public String getLinkToParentTree()
	{
		return "hi";
	}
	
	public void createTreeFile()
	{
		
		Path p = Paths.get(content);
        try {
            Files.writeString(p, treeContents(), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public String treeContents()
	{
		String treeContent = "";
		System.out.println (indexSha.size());
		System.out.println (indexFileName.size());
		for (int index = 0; index < indexSha.size(); index++)
		{
			System.out.println ("makes into forLoop");
			treeContent += "blob : " + indexSha.get(index) + " " + indexFileName.get(index) + "\n";
		}
		
		if (parent != null)
		{
			treeContent += "tree : " + parent.getTreeSha() + "\n";
		}
		treeContent += "tree : " + content;
		System.out.println (treeContent);
		return treeContent;
	}
	
	
	private static String encryptThisString(String input)// from geeksforgeeks
	{
		try {
			// getInstance() method is called with algorithm SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA-1");

			// digest() method is called
			// to calculate message digest of the input string
			// returned as array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);

			// Add preceding 0s to make it 32 bit
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			// return the HashText
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main (String [] args) throws IOException
	{
		ArrayList<String> arr1 = new ArrayList<String>();
		arr1.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		arr1.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		arr1.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		Tree tree1 = new Tree (arr1, null);
		//System.out.println (tree1.getTreeSha());
		//tree1.getIndexContents();
		tree1.treeContents();
		
	}
	
}
