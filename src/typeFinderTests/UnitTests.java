package typeFinderTests;

import mainFiles.*;


import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

class UnitTests {

	@Test
	void testEmptyFile() {
		String[] args = {"/Users/zachalbers/eclipse-workspace/SENG300-Iteration1/TestFiles", "MyInterface"};

		TypeFinder finder = new TypeFinder();
		finder.main(args);
		assertEquals("MyInterface", finder.javaType);
		
	}

}
