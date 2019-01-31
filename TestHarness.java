import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// %TestHarness -p PUT -o Oracle -s 7 -n 10000 -a 2 -r 0.0 1.0 -1.0 0.0
// %TestHarness -m RRT -k 10 -p PUT -o Oracle -s 7 -n 10 -a 2 -r 1 10 30 50

import org.junit.Test;

public class TestHarness {
	Tester tester = new Tester();
	@Test
	public void hasTester(){
		assertNotNull(tester);
	}
	@Test
	public void testGetInput() {
		tester.getInput();
		System.out.println("Test Harness getInput()");
		assertNotNull(tester.getString());
	}
	@Test
	public void testIsEmpty() {
		assertNotNull(tester.getString());
	}
	@Test
	public void testStringArray() {
		assertNotNull(tester.stringArray(tester.getString()));
	}
	@Test
	public void testHasPRNG() {
		SevenPRNG prng = new SevenPRNG();
		assertNotNull(prng);
	}
	@Test
	public void seedEquals() {
		SevenPRNG prng = new SevenPRNG(7);
		//System.out.println(prng.getSeed());
		assertEquals(7, prng.getSeed());
	}
	@Test
	public void testRandomNumber() {
		SevenPRNG prng = new SevenPRNG(7);
		prng.setRandomNumber(1.0, 3.0);
		int random = prng.getRandomNumber();
		//System.out.println(random);
		assertNotNull(random);
	}
	@Test
	public void testHasAllElements() {
		String[] arr = new String[] {"shaina", "has", "a", "headache"};
		assertTrue(tester.hasAllElements(arr, "shaina"));
	}
	@Test
	public void testAsk() {
		assertFalse(tester.ask("s"), "Not y or n.");
	}
	@Test
	public void testAskTrue() {
		assertTrue(tester.ask("y"));
	}
	@Test
	public void testIsValidInput() {
		String[] flags = new String[] {"-p", "-o", "-s", "-n", "-a", "-r"};
		String[] inputs = tester.stringArray(tester.getString());
		assertTrue(tester.isValidInput(inputs, flags));
	}
/*	@Test
	public void testFileExist() {
		assertTrue(tester.fileExists("C:\\Users\\shain\\eclipse-workspace\\SQA Final\\Oracle.jar"));
	}*/
/*	@Test
	public void testRunProg() throws Exception {
		assertFalse(tester.runProg("PUT.jar"), "PUT run failed.");
	}*/
	@Test
	public void testRunProg() throws Exception{
		String input = "1 2 3";
		assertNull(tester.runProg("PUT.jar", input), "PUT run failed.");
	}
/*	@Test
	public void testCandidate() {
		RRT rrt = new RRT();
		rrt.setCandidates(tester.createTestCases());
		for(int i = 0; i < rrt.getCandidates().length; i++) {
			for(int j = 0; j < rrt.getCandidates()[0].length; j++) {
				for(int k = 0; k < rrt.getCandidates()[0][0].length; k++) {
					if(rrt.getCandidates()[i][j][k] == null) 
						isNull = true;
				}
			}
		}
		
		assertNotNull(rrt.getCandidates(), "Set is empty.");
		
	}*/
	
	
}
