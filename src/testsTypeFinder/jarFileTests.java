package testsTypeFinder;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import mainFiles.TypeFinder;

public class jarFileTests {
	
	// BASEDIR should be the directory to the 'SENG300-Iteration1' folder
	private static String BASEDIR = System.getProperty("user.dir");

	/**
	 * The following tests are used to test jar files
	 */
	
	/**
	 * Test for correct declaration and reference count given a Jar file for int 
	 */
	@Test
	public void testInt() {
	  String[] args = {BASEDIR + "" + File.separator + "TestFiles" + File.separator + "testDir9", "int"};

	  TypeFinder finder = new TypeFinder();
	  finder.run(args);
	  assertEquals("int. Declarations found: 0; references found: 1.", finder.outputString);
	}
	
	
	/**
	 * Test for correct declaration and reference count given a Jar file for String
	 */
	@Test
	public void testString() {
	  String[] args = {BASEDIR + "" + File.separator + "TestFiles" + File.separator + "testDir9", "String"};

	  TypeFinder finder = new TypeFinder();
	  finder.run(args);
	  assertEquals("String. Declarations found: 0; references found: 2.", finder.outputString);
	}
	
	
	/**
	 * Test for correct declaration and reference count given a Jar file for fully qualified name for String
	 */
	@Test
	public void testStringFullName() {
	  String[] args = {BASEDIR + "" + File.separator + "TestFiles" + File.separator + "testDir9", "java.lang.String"};

	  TypeFinder finder = new TypeFinder();
	  finder.run(args);
	  assertEquals("java.lang.String. Declarations found: 0; references found: 2.",  finder.outputString);
	  
	}
	
	/**
	 * Tests for the correct output when given an enum with declarations and references, for a given jar file
	 */
	@Test
	public void testEnum() {
	  String[] args = {BASEDIR + "" + File.separator + "TestFiles" + File.separator + "testDir10", "Day"};

	  TypeFinder finder = new TypeFinder();
	  finder.run(args);
	  assertEquals("Day. Declarations found: 1; references found: 2.",  finder.outputString);	  
	}
	
	/**
	 * Tests for the correct output when given an enum with a full java-type name with declarations and references.
	 */
	@Test
	public void testEnumFullName() {
	  String[] args = {BASEDIR + "" + File.separator + "TestFiles" + File.separator + "testDir10", "package3.Vehicle.Day"};

	  TypeFinder finder = new TypeFinder();
	  finder.run(args);
	  
	  assertEquals("package3.Vehicle.Day. Declarations found: 1; references found: 2.", finder.outputString);
	  
	}	
}
