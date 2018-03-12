package typeFinderTests;

import mainFiles.*;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;



import java.io.IOException;



class UnitTests {
	
	// BASEDIR should be the directory to the 'SENG300-Iteration1' folder
	//private static String BASEDIR = "/home/andrew/Projects/SENG300-Iteration1"; 
	private static String BASEDIR = "/Users/zachalbers/eclipse-workspace/SENG300-Iteration1"; 

	
	/**
	 * Tests for the correct output when given an empty file.
	 */
	@Test
	void testEmptyFile() {
		String[] args = {BASEDIR + "/TestFiles/testDir1", "String"};



		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals(finder.outputString, "String. Declarations found: 0; references found: 0.");
		
	}

	
	/**
	 * Tests for the correct output when given files with comments containing declarations and references.
	 */
	@Test
	void testComments() {
		String[] args = {BASEDIR + "/TestFiles/testDir2", "String"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals(finder.outputString, "String. Declarations found: 0; references found: 1.");
		
	}
	
	
	/**
	 * Tests for the correct output when given an enum with declarations and references.
	 */
	@Test
	void testEnum() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "Day"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals(finder.outputString, "Day. Declarations found: 1; references found: 2.");
		
	}	
	
	/**
	 * Tests for the correct output when given a class with declarations and references.
	 */
	@Test
	void testClasses() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "Vehicle"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals(finder.outputString, "Vehicle. Declarations found: 1; references found: 3.");
		
	}
	
	
	/**
	 * Tests that the correct exception is thrown when passed 
	 */
	@Test
	void testFalseDir() {
		String[] args = {BASEDIR + "/TestFiles/NoDir", "Vehicle"};
		
		assertThrows(NullPointerException.class, () -> {
			TypeFinder finder = new TypeFinder();
			finder.run(args);
		});	
	}
	
	
	/**
	 * Tests that the correct output is given when the type is in multiple files.
	 */
	@Test
	void testMultipleFiles() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "String"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals(finder.outputString, "String. Declarations found: 0; references found: 6.");
	}
	
	
	/**
	 * Tests that basic parameter in methods and initializers are counted correctly.
	 */
	@Test
	void testParameters() {
		String[] args = {BASEDIR + "/TestFiles/testDir4", "int"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals(finder.outputString, "int. Declarations found: 0; references found: 8.");
	}
	
	
	
	
}
