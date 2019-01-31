import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class RT {
	private String[][] testCases;
	private String[] results;
	
	@Test
	public void testRT() throws Exception {
		Tester tester = Tester.getTester();
		testCases = tester.createTestCases();
		int failures = 0;
		results = new String[tester.getNumOfTestCases()];
		for(int i = 0; i < tester.getNumOfTestCases(); i++) {
			for(int j = 0; j < tester.getNumOfParameters(); j++) {
				results[i] = testCases[i][j];
				//System.out.println(results[i]);
				if(tester.putOracleEquals(results[i]) != true) failures += 1;
			}
		}
		//System.out.println(tester.putOracleEquals(results));
		assertEquals(0, failures);
	}

}
