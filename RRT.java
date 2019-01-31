import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class RRT {
	private String[][] candidates;
	private String[][] executed;
	int exRatio;
/*	public RRT() {
		//gotta populate that constructor ya know
		Tester tester = Tester.getTester();
		this.setCandidates(tester.createTestCases());
		this.executed = new String[this.getCandidates().length][getCandidates()[0].length][getCandidates()[0][0].length];
	}*/

	public void setCandidates() {
		this.candidates = Tester.getTester().createTestCases();
	}
	public String[][] getCandidates(){
		return candidates;
	}
	@Test
	public void initRRT() {
		RRT rrt = new RRT();
		assertNotNull(rrt);
	}
	@Test
	public void testCreateTestCases() {
		//boolean b = false;
		RRT rrt = new RRT();
		Tester tester = new Tester();
		rrt.candidates = tester.createTestCases();
		for(int i = 0; i < rrt.candidates.length; i++) {
			for(int j = 0; j < rrt.candidates[0].length; j++) {
				//System.out.println(rrt.executed[i][j]);
			}
		}
		assertNotNull(rrt.candidates);
	}	

}
