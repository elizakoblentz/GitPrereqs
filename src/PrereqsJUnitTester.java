import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrereqsJUnitTester {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		File testText=new File("myTesterText.txt");
		testText.createNewFile();
		
		File testText2=new File("myTesterText2.txt");
		testText.createNewFile();
		
		File testText3=new File("myTesterText3.txt");
		testText.createNewFile();
		
		PrintWriter writer2=new PrintWriter(testText2);
		writer2.print("watch infinity train");
		writer2.close();
		
		PrintWriter writer3=new PrintWriter(testText3);
		writer3.print("watch mob psycho");
		writer3.close();
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
		File objectsFolder=new File("./objects");
		objectsFolder.mkdir();
		
		Blob blobby=new Blob("myTesterText.txt");
		File blobbedText=new File("./objects/da39a3ee5e6b4b0d3255bfef95601890afd80709");
		assertTrue(blobbedText.exists());
		objectsFolder.delete();
		
	}
	
	
	@Test //write one big test index tester ig
	void testInit() throws Exception {
		Index dexy=new Index();
		dexy.init();
		
		File objectsFolder=new File("./objects");
		assertTrue(objectsFolder.exists());
		File index=new File("index");
		assertTrue(index.exists());
		
	}
	//make interface to his code
	
	void testAdd() throws Exception {
		Index dexy=new Index();
		dexy.init();
		
		dexy.add("myTesterText.txt");
		dexy.add("myTesterText2.txt");
	}
	

}
