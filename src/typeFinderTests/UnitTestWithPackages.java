package typeFinderTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import mainFiles.*;



class UnitTestWithPackages {

	// BASEDIR should be the directory to the 'SENG300-Iteration1' folder
	//private static String BASEDIR = "/home/andrew/Projects/SENG300-Iteration1"; 
	private static String BASEDIR = "/Users/zachalbers/eclipse-workspace/SENG300-Iteration1"; 



	
	/**
	 * Tests for the correct output when given files with comments containing declarations and references
	 * for full java-type names.
	 */
	@Test
	void testComments() {
		String[] args = {BASEDIR + "/TestFiles/testDir2", "java.lang.String"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("java.lang.String. Declarations found: 0; references found: 1.",  finder.outputString);
		
	}
	
	
	/**
	 * Tests for the correct output when given an enum with a full java-type name with declarations and references.
	 */
	@Test
	void testEnum() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "package3.Vehicle.Day"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("package3.Vehicle.Day. Declarations found: 1; references found: 2.", finder.outputString);
		
	}	
	
	/**
	 * Tests for the correct output when given a specific class with declarations and references.
	 */
	@Test
	void testClasses() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "package3.Vehicle"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("package3.Vehicle. Declarations found: 1; references found: 3.", finder.outputString);
		
	}

	
	
	/**
	 * Tests that the correct output is given when the type is in multiple files, but packages are different.
	 */
	@Test
	void testMultipleFiles() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "package3.Person"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("package3.Person. Declarations found: 1; references found: 0.", finder.outputString);
	}
	
	
	/**
	 * Tests that the correct output is given when the type is in an import statement.
	 */
	@Test
	void testImport() {
		String[] args = {BASEDIR + "/TestFiles/testDir5", "java.io.BufferedReader"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("java.io.BufferedReader. Declarations found: 0; references found: 3.", finder.outputString);
	}


	
	
	
}
