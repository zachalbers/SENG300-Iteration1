package typeFinderTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import mainFiles.TypeFinder;

class DownloadedTests {
	
	private static String BASEDIR = "/Users/zachalbers/eclipse-workspace/SENG300-Iteration1"; 

	/**
	 * test1
	 */
	@Test
	void test1() {
		String[] args = {BASEDIR + "/TestFiles/testDir8", "A"};



		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("A. Declarations found: 1; references found: 0.", finder.outputString);
		
	}
	
	/**
	 * test2
	 */
	@Test
	void test2() {
		String[] args = {BASEDIR + "/TestFiles/testDir8", "B"};



		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("B. Declarations found: 1; references found: 9.", finder.outputString);
		
	}
	
	/**
	 * test3
	 */
	@Test
	void test3() {
		String[] args = {BASEDIR + "/TestFiles/testDir8", "C"};



		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("C. Declarations found: 1; references found: 5.", finder.outputString);
		
	}
	
	/**
	 * test4
	 */
	@Test
	void test4() {
		String[] args = {BASEDIR + "/TestFiles/testDir8", "D"};



		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("D. Declarations found: 1; references found: 0.", finder.outputString);
		
	}
	
	/**
	 * test5
	 */
	@Test
	void test5() {
		String[] args = {BASEDIR + "/TestFiles/testDir8", "D.E"};



		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("D.E. Declarations found: 1; references found: 2.", finder.outputString);
		
	}
	
	/**
	 * test6
	 */
	@Test
	void test6() {
		String[] args = {BASEDIR + "/TestFiles/testDir8", "E"};



		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("E. Declarations found: 0; references found: 0.", finder.outputString);
		
	}
	
	/**
	 * test7
	 */
	@Test
	void test7() {
		String[] args = {BASEDIR + "/TestFiles/testDir8", "foo.E"};



		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("foo.E. Declarations found: 1; references found: 4.", finder.outputString);
		
	}
	
	/**
	 * test8
	 */
	@Test
	void test8() {
		String[] args = {BASEDIR + "/TestFiles/testDir8", "F"};



		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("F. Declarations found: 0; references found: 0.", finder.outputString);
		
	}
	
	/**
	 * test9
	 */
	@Test
	void test9() {
		String[] args = {BASEDIR + "/TestFiles/testDir8", "int"};



		TypeFinder finder = new TypeFinder();
		finder.run(args);
		assertEquals("int. Declarations found: 0; references found: 1.", finder.outputString);
		
	}

}
