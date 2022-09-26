import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
public class Tree {
	ArrayList<String> indeces=new ArrayList<String>();
	String fileData="";
	public Tree(ArrayList<String> input) {
		indeces=input;
		for(int i=0;i<indeces.size();i++) {
			fileData+=indeces.get(i);
			if(i+1<indeces.size()) {
				fileData+="\n";
			}
		}
		//System.out.println(fileData);
		}
	public void saveInfo() throws IOException {
		File tree=new File("./objects/"+encryptThisString(fileData));
		tree.createNewFile();
		PrintWriter writer=new PrintWriter(tree);
		writer.print(fileData);
		writer.close();
		
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
	
	public static void main (String [] args)
	{
		
	}
	
}
