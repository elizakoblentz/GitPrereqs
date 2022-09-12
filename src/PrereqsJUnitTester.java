import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PrereqsJUnitTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		File testText=new File("myTesterText.txt");
		testText.createNewFile();
		PrintWriter writer=new PrintWriter(testText);
		writer.print("watch infinity train");
		writer.close();
		//assertTrue(testText.exists());
	}
	//Charlie-s-GitPrereqs/objects

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//File testText=new File("myTesterText.txt");
		//testText.delete();
		//kill blobs in objects, also index
	}
	
	@Test
	void testBlob() throws Exception {
		Blob blobby=new Blob("myTesterText.txt");
		File blobedText=new File("./objects/")
	}

}
