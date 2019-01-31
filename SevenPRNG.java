
import java.util.Random;


public class SevenPRNG {
	static long seed;
	private Random random;
	private int rand;
	public SevenPRNG() {
		random = new Random();
	}
	public SevenPRNG(long seed) {
		random = new Random();
		random.setSeed(seed);
		SevenPRNG.seed = seed;
	}
	public long getSeed() {
		return seed; 
	}
	// takes in min and max as doubles and casts to output pseudo random integer
	// use int because Random's nextInt method takes in a bound
	public void setRandomNumber(double min, double max) {
		int minCeil = (int)Math.ceil(min);
		int maxCeil = (int)Math.ceil(max);
		int bound = maxCeil - minCeil;
		rand = random.nextInt(bound) + minCeil;
	}
	public int getRandomInt() {
		return random.nextInt();
	}
	public int getRandomNumber(){
		return rand;
	}
	
	
	
}
