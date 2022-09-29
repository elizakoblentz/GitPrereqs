import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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
		File testText=new File("myTesterText.txt");
		testText.delete();
		File testText2=new File("myTesterText2.txt");
		testText2.delete();
		File testText3=new File("myTesterText3.txt");
		testText3.delete();
		
		File blob1=new File("./objects/da39a3ee5e6b4b0d3255bfef95601890afd80709");
		File blob2=new File("./objects/92d96b52b7f26ae0561c2be7466196ef848ac975");
		File blob3=new File("./objects/58d42438552af3d1978a8631a29823f0fd56e9ca");
		blob1.delete();
		blob2.delete();
		blob3.delete();
		File objectsFolder=new File("./objects");
		objectsFolder.delete();
		
		
		//kill blobs in objects, also index
		
	}
	
	@Test
	void testBlob() throws Exception {
		File objectsFolder=new File("./objects");
		objectsFolder.mkdir();
		
		Blob blobby=new Blob("myTesterText.txt");
		File blobbedText=new File("./objects/da39a3ee5e6b4b0d3255bfef95601890afd80709");
		assertTrue(blobbedText.exists());
		blobbedText.delete();
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
	@Test
	void testAdd() throws Exception {
		Index dexy=new Index();
		dexy.init();
		
		
		dexy.add("myTesterText.txt");
		File tester1=new File("./objects/da39a3ee5e6b4b0d3255bfef95601890afd80709");
		assertTrue(tester1.exists());
		
		Path indexFile=Paths.get("index");
		String indexContent=Files.readString(indexFile);
		
		assertTrue(indexContent.contains("myTesterText.txt"+" : "+"da39a3ee5e6b4b0d3255bfef95601890afd80709"));
		
		
		dexy.add("myTesterText2.txt");
		File tester2=new File("./objects/92d96b52b7f26ae0561c2be7466196ef848ac975");
		assertTrue(tester2.exists());
		indexContent=Files.readString(indexFile);
		assertTrue(indexContent.contains("myTesterText2.txt"+" : "+"92d96b52b7f26ae0561c2be7466196ef848ac975"));
		
		
		dexy.add("myTesterText3.txt");
		File tester3=new File("./objects/58d42438552af3d1978a8631a29823f0fd56e9ca");
		assertTrue(tester3.exists());
		indexContent=Files.readString(indexFile);
		assertTrue(indexContent.contains("myTesterText3.txt"+" : "+"58d42438552af3d1978a8631a29823f0fd56e9ca"));
		
		
		tester1.delete();
		tester2.delete();
		tester3.delete();
		
		File objectsFolder=new File("./objects");
		objectsFolder.delete();
		
	}
	@Test
	void remove() throws Exception {
		Index dexy=new Index();
		dexy.init();
		
		Path indexFile=Paths.get("index");
		String indexContent=Files.readString(indexFile);
		
	
		dexy.add("myTesterText.txt");
		dexy.add("myTesterText2.txt");
		dexy.add("myTesterText3.txt");
		
		dexy.remove("myTesterText.txt");
		indexContent=Files.readString(indexFile);
		assertTrue(!indexContent.contains("myTesterText.txt"+" : "+"da39a3ee5e6b4b0d3255bfef95601890afd80709"));
		File tester1=new File("./objects/da39a3ee5e6b4b0d3255bfef95601890afd80709");
		assertTrue(!tester1.exists());
		
		dexy.remove("MyTesterText2.txt");
		indexContent=Files.readString(indexFile);
		assertTrue(!indexContent.contains("myTesterText2.txt"+" : "+"da39a3ee5e6b4b0d3255bfef95601890afd80709"));
		File tester2=new File("./objects/92d96b52b7f26ae0561c2be7466196ef848ac975");
		assertTrue(!tester2.exists());
		
		dexy.remove("MyTesterText3.txt");
		indexContent=Files.readString(indexFile);
		assertTrue(!indexContent.contains("myTesterText3.txt"+" : "+"da39a3ee5e6b4b0d3255bfef95601890afd80709"));
		File tester3=new File("./objects/58d42438552af3d1978a8631a29823f0fd56e9ca");
		assertTrue(!tester3.exists());
	}
	
	@Test
	void tree() throws Exception {
		ArrayList<String> test = new ArrayList<String>();
		
		test.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		test.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		test.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		test.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
		test.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
		//Tree treeTest = new Tree(test);
		
		File tester=new File(".objects/dd4840f48a74c1f97437b515101c66834b59b1be");
		assertTrue(!tester.exists());
	}
	
	

}
