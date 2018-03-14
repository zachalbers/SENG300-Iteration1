package typeFinderTests;

import mainFiles.*;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
		assertEquals(finder.outputString, "java.lang.String. Declarations found: 0; references found: 1.");
		
	}
	
	
	/**
	 * Tests for the correct output when given an enum with a full java-type name with declarations and references.
	 */
	@Test
	void testEnum() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "package3.Vehicle.Day"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals(finder.outputString, "package3.Vehicle.Day. Declarations found: 1; references found: 2.");
		
	}	
	
	/**
	 * Tests for the correct output when given a specific class with declarations and references.
	 */
	@Test
	void testClasses() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "package3.Vehicle"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals(finder.outputString, "package3.Vehicle. Declarations found: 1; references found: 3.");
		
	}

	
	
	/**
	 * Tests that the correct output is given when the type is in multiple files, but packages are different.
	 */
	@Test
	void testMultipleFiles() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "package3.Person"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals(finder.outputString, "package3.Person. Declarations found: 1; references found: 0.");
	}
	
	

	
	
	
	
}
