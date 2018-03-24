package testsTypeFinder;

import static org.junit.Assert.*;

import org.junit.Test;

import mainFiles.TypeFinder;

public class UnitTestsSimple {

	// BASEDIR should be the directory to the 'SENG300-Iteration1' folder
	private static String BASEDIR = "/Users/zachalbers/eclipse-workspace/SENG300-Iteration1"; 

	
	/**
	 * Tests that the correct exception is thrown when passed too few arguments
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTooFewArgs() {
		String[] args = {BASEDIR + "/TestFiles/testDir1"};
		
			TypeFinder finder = new TypeFinder();
			finder.run(args);

	}
	
	
	/**
	 * Tests that the correct exception is thrown when passed too many arguments
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTooManyArgs() {
		String[] args = {BASEDIR + "/TestFiles/testDir1"};
		
			TypeFinder finder = new TypeFinder();
			finder.run(args);

	}
	
	
	/**
	 * Tests for the correct output when given an empty file.
	 */
	@Test
	public void testEmptyFile() {
		String[] args = {BASEDIR + "/TestFiles/testDir1", "String"};



		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("String. Declarations found: 0; references found: 0.", finder.outputString);
		
	}

	
	/**
	 * Tests for the correct output when given files with comments containing declarations and references.
	 */
	@Test
	public void testComments() {
		String[] args = {BASEDIR + "/TestFiles/testDir2", "String"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("String. Declarations found: 0; references found: 1.", finder.outputString);
		
	}
	
	
	/**
	 * Tests for the correct output when given an enum with declarations and references.
	 */
	@Test
	public void testEnum() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "Day"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("Day. Declarations found: 1; references found: 2.", finder.outputString);
		
	}	
	
	/**
	 * Tests for the correct output when given a class with declarations and references.
	 */
	@Test
	public void testClasses() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "Vehicle"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("Vehicle. Declarations found: 1; references found: 3.", finder.outputString);
		
	}
	
	
	/**
	 * Tests that the correct exception is thrown when passed an invalid directory.
	 */
	@Test(expected = NullPointerException.class)
	public void testFalseDir() {
		String[] args = {BASEDIR + "/TestFiles/NoDir", "Vehicle"};
		
			TypeFinder finder = new TypeFinder();
			finder.run(args);
	}
	
	
	/**
	 * Tests that the correct output is given when the java-type is in multiple files.
	 */
	@Test
	public void testMultipleFiles() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "String"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("String. Declarations found: 0; references found: 6.", finder.outputString);
	}
	
	
	/**
	 * Tests that basic parameters in methods and initializers are counted correctly.
	 */
	@Test
	public void testParameters() {
		String[] args = {BASEDIR + "/TestFiles/testDir4", "int"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("int. Declarations found: 0; references found: 9.",  finder.outputString);
	}
	
	/**
	 * Tests that the correct output is given when the type is in an import statement.
	 */
	@Test
	public void testImport() {
		String[] args = {BASEDIR + "/TestFiles/testDir5", "BufferedReader"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("BufferedReader. Declarations found: 0; references found: 3.", finder.outputString);
	}
	
	/**
	 * Tests that the correct output is given when the type is an exception used in a try-catch-statement.
	 */
	@Test
	public void testCatchExceptions() {
		String[] args = {BASEDIR + "/TestFiles/testDir5", "IOException"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("IOException. Declarations found: 0; references found: 3.", finder.outputString);
	}
	
	/**
	 * Tests that the correct output is given when throwing a new exception.
	 */
	@Test
	public void testThrowNewExceptions() {
		String[] args = {BASEDIR + "/TestFiles/testDir5", "IllegalArgumentException"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("IllegalArgumentException. Declarations found: 0; references found: 1.", finder.outputString);
	}
	
	/**
	 * Tests that the correct output is given when a method throws an exception.
	 */
	@Test
	public void testThrowsExceptions() {
		String[] args = {BASEDIR + "/TestFiles/testDir5", "NullPointerException"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("NullPointerException. Declarations found: 0; references found: 1.", finder.outputString);
	}
	
	/**
	 * Tests that the correct output is given when a method throws an exception.
	 */
	@Test
	public void testReturnTypes() {
		String[] args = {BASEDIR + "/TestFiles/testDir6", "Double"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("Double. Declarations found: 0; references found: 3.", finder.outputString);
	}
	
	
	
	
	/**
	 * The following tests test full java-type names, including packages and import statements.
	 */
	
	
	
	
	/**
	 * Tests for the correct output when given files with comments containing declarations and references
	 * for full java-type names.
	 */
	@Test
	public void testCommentsWithFullName() {
		String[] args = {BASEDIR + "/TestFiles/testDir2", "java.lang.String"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("java.lang.String. Declarations found: 0; references found: 1.",  finder.outputString);
		
	}
	
	
	/**
	 * Tests for the correct output when given an enum with a full java-type name with declarations and references.
	 */
	@Test
	public void testEnumWithFullName() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "package3.Vehicle.Day"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("package3.Vehicle.Day. Declarations found: 1; references found: 2.", finder.outputString);
		
	}	
	
	/**
	 * Tests for the correct output when given a specific class with declarations and references.
	 */
	@Test
	public void testClassesWithFullName() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "package3.Vehicle"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("package3.Vehicle. Declarations found: 1; references found: 3.", finder.outputString);
		
	}

	
	
	/**
	 * Tests that the correct output is given when the type is in multiple files, but packages are different.
	 */
	@Test
	public void testMultiplePackages() {
		String[] args = {BASEDIR + "/TestFiles/testDir3", "package3.Person"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("package3.Person. Declarations found: 1; references found: 0.", finder.outputString);
	}
	
	
	/**
	 * Tests that the correct output is given when the type is in an import statement for full java-type names.
	 */
	@Test
	public void testImportWithPackages() {
		String[] args = {BASEDIR + "/TestFiles/testDir5", "java.io.BufferedReader"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("java.io.BufferedReader. Declarations found: 0; references found: 3.", finder.outputString);
	}
	
	/**
	 * Tests that the correct output is given when the type is an exception used in a try-catch-statement for full java-type names.
	 */
	@Test
	public void testCatchExceptionsWithFullName() {
		String[] args = {BASEDIR + "/TestFiles/testDir5", "java.io.IOException"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("java.io.IOException. Declarations found: 0; references found: 3.", finder.outputString);
	}

	/**
	 * Tests that the correct output is given when throwing a new exception for full java-type names.
	 */
	@Test
	public void testThrowNewExceptionsWithFullName() {
		String[] args = {BASEDIR + "/TestFiles/testDir5", "java.lang.IllegalArgumentException"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("java.lang.IllegalArgumentException. Declarations found: 0; references found: 1.", finder.outputString);
	}
	
	/**
	 * Tests that the correct output is given when a method throws an exception for full java-type names.
	 */
	@Test
	public void testThrowsExceptionsWithFullName() {
		String[] args = {BASEDIR + "/TestFiles/testDir5", "java.lang.NullPointerException"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("java.lang.NullPointerException. Declarations found: 0; references found: 1.", finder.outputString);
	}
	
	/**
	 * Tests that the correct output is given when a method throws an exception for full java-type names.
	 */
	@Test
	public void testReturnTypeswithFullName() {
		String[] args = {BASEDIR + "/TestFiles/testDir6", "Double"};

		TypeFinder finder = new TypeFinder();
		finder.run(args);
		
		assertEquals("Double. Declarations found: 0; references found: 3.", finder.outputString);
	}

}
