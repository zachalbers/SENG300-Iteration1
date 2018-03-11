package typeFinderTests;

import mainFiles.*;


import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

class UnitTests {
	
	// BASEDIR should be the directory to the 'SENG300-Iteration1' folder
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
	 * Tests for the correct output when given an empty file.
	 */
	@Test
	void testEmptyFile2() {
		String[] args = {BASEDIR + "/TestFiles/testDir2", "String"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals(finder.outputString, "String. Declarations found: 0; references found: 1.");
		
	}
	
	
	
	
	
	
	
	
	
}
