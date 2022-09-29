import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.*;

public class Blob {
	String fileName;
	public Blob (String fileName) throws Exception {
		this.fileName = fileName;
		//zipFile();
		writeFile();
	}
	
	public String sha1Code(String filePath) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
        byte[] bytes = new byte[1024];
        // read all file content
        while (digestInputStream.read(bytes) > 0);

//        digest = digestInputStream.getMessageDigest();
        byte[] resultByteArry = digest.digest();
        return bytesToHexString(resultByteArry);
    }

    /**
     * Convert a array of byte to hex String. <br/>
     * Each byte is covert a two character of hex String. That is <br/>
     * if byte of int is less than 16, then the hex String will append <br/>
     * a character of '0'.
     *
     * @param bytes array of byte
     * @return hex String represent the array of byte
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int value = b & 0xFF;
            if (value < 16) {
                // if value less than 16, then it's hex String will be only
                // one character, so we need to append a character of '0'
                sb.append("0");
            }
            sb.append(Integer.toHexString(value));
        }
        return sb.toString();
    }
    
    public void writeFile() throws NoSuchAlgorithmException, IOException {
    	//reading and printing the file to new file:
    	//String zipFileName = this.fileName;
    	String code = sha1Code(fileName);
    	FileInputStream in = new FileInputStream(fileName);
    	FileOutputStream out = new FileOutputStream("objects/" + code);
    	try {
    		  
            int n;
  
            // read() function to read the
            // byte of data
            while ((n = in.read()) != -1) {
                // write() function to write
                // the byte of data
                out.write(n);
            }
        }
        finally {
            if (in != null) {
  
                // close() function to close the
                // stream
                in.close();
            }
            // close() function to close
            // the stream
            if (out != null) {
                out.close();
            }
        }
    	
    }
   
    /*
    public void zipFile() throws Exception {
    	FileOutputStream output = new FileOutputStream(fileName + ".zip");
    	ZipOutputStream zipOut = new ZipOutputStream(output);
    	ZipEntry ze = new ZipEntry(this.fileName);
        zipOut.putNextEntry(ze);
        
        zipOut.closeEntry();
        zipOut.close();	
    }
    */
    public static void main (String[]args) throws Exception 
    {
    	Blob b1 = new Blob ("ElizaTesterBlob1.txt");
    	Blob b2 = new Blob ("ElizaTesterBlob2.txt");
    	
    }
}
