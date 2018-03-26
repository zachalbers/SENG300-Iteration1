package testsTypeFinder;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import mainFiles.TypeFinder;

public class UnitTestsAllJavaTypes {
	// BASEDIR should be the directory to the 'SENG300-Iteration1' folder
	private static String BASEDIR = System.getProperty("user.dir");
	/**
	 * Tests for the correct output when given files with comments containing declarations and references
	 * for full java-type names.
	 */
	@Test
	public void testCommentsWithFullName() {
	  String[] args = {BASEDIR + "" + File.separator + "TestFiles" + File.separator + "testAllJavaTypes"};
	  
	  List<String> correctOutput = new ArrayList<String>();
	  correctOutput.add("anotherPack.AnotherClass. Declarations found: 1; references found: 0.");
	  correctOutput.add("int. Declarations found: 0; references found: 1.");
	  correctOutput.add("java.lang.Integer. Declarations found: 0; references found: 1.");
	  correctOutput.add("java.lang.String. Declarations found: 0; references found: 1.");
	  correctOutput.add("somePack.ArrayList. Declarations found: 0; references found: 2.");
	  correctOutput.add("somePack.SomeClass. Declarations found: 1; references found: 1.");
	  
	  TypeFinder finder = new TypeFinder();
	  finder.run(args);
	  assertEquals(correctOutput,  finder.allOutputStrings);
	  
	}

		/**
	 * Tests for the correct output when given files with comments containing declarations and references and given only the directory
	 */
	@Test
	public void testCommentsAll() {
	  String[] args = {BASEDIR + "" + File.separator + "TestFiles" + File.separator + "testDir2"};
	  
	  List<String> correctOutput = new ArrayList<String>();
	  correctOutput.add("java.lang.String. Declarations found: 0; references found: 1.");
	  correctOutput.add("package1.Vehicle. Declarations found: 1; references found: 0.");
	  
	  
	  TypeFinder finder = new TypeFinder();
	  finder.run(args);
	  assertEquals(correctOutput,  finder.allOutputStrings);
	}
	
	/**
	 * Tests for the correct output giving a directory containing a jarFile that contains a jarfile + directories when only given a directory
	 */
	@Test
	public void testRecusiveJarAll() {
	  String[] args = {BASEDIR + "" + File.separator + "TestFiles" + File.separator + "testDir11"};
	  
	  List<String> correctOutput = new ArrayList<String>();
	  correctOutput.add("int. Declarations found: 0; references found: 2.");
	  correctOutput.add("java.lang.String. Declarations found: 0; references found: 4.");
	  correctOutput.add("java.lang.String[]. Declarations found: 0; references found: 2.");
	  correctOutput.add("package1.someFolder.TestClass2. Declarations found: 1; references found: 2.");
	  correctOutput.add("package1.someFolder.TestClass3. Declarations found: 1; references found: 2.");
	  correctOutput.add("package1.TestClass1. Declarations found: 2; references found: 0.");
	  
	  TypeFinder finder = new TypeFinder();
	  finder.run(args);
	  assertEquals(correctOutput,  finder.allOutputStrings);
	}
	
	@Test
	public void testParameterizedType() {
		String[] args = {BASEDIR + "" + File.separator + "TestFiles" + File.separator + "testAllJavaTypes"};
		
	}
}
