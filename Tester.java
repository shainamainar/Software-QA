import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.runner.JUnitCore;


public class Tester {
	//variables
	private static String input;
	private static String[] strings;
	private static String[] flags = new String[] {"-p", "-o", "-s", "-n", "-a", "-r"};
	private static String[] rtFlags = new String[] {"-m", "-R", "-p", "-o", "-s", "-n", "-a", "-r"};
	private static String[] rFlags = new String[] {"-m", "-p", "-o", "-s", "-n", "-a", "-r"};
	
	private static double[][] bound;
	private String put, oracle;
	private long seed;
	private int numOfTestCases, numOfParameters;
	private double exRatio, originalExRatio;
	private final String datFile = "TestResults.dat";
	private static Tester tester;
	private String className;
	//methods
	public static void main(String[] args) throws Exception {
		boolean cont = true; // use ask method later. 
		tester = new Tester();
		BufferedWriter out = new BufferedWriter(new FileWriter(tester.getFileName()));
		out.append("=================Test Results=================");
		out.newLine();
		while(cont) {
			tester.getInput();
			String input = getString();
			out.append("Input is: " + tester.getString());
			out.newLine();
			String[] arr = stringArray(input);
			String[] flags = tester.getFlags();
			System.out.println("Writing to file...");
			Scanner scanner = new Scanner(System.in);
			//System.out.println("End.");
			try {
				tester.parseString(input);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//for(int i = 0; i < strings.length; i++) System.out.println(strings[i]);
			//System.out.println(tester.oracle);
			if(Arrays.asList(arr).contains("-m")) flags = tester.getRFlags();
			if(Arrays.asList(arr).contains("-R")) flags = tester.getRTFlags();
			if(isValidInput(arr, flags)) {
				org.junit.runner.Result result2 = JUnitCore.runClasses(Class.forName(tester.getClassName()));
				out.append("Test Class: " + tester.getClassName());
				out.newLine();
				int fail = result2.getFailureCount();
				//out.println("reach");
				out.append("Runs: " + Integer.toString(result2.getRunCount()));
				out.newLine();
				out.append("Failure Count: " + fail);
				out.newLine();
				out.append("Runtime: " + Long.toString(result2.getRunTime()) + "ms");
				out.newLine();
				for(org.junit.runner.notification.Failure failure : result2.getFailures()) {
					try {
						System.out.println(failure.toString());
						out.append(failure.toString());
						out.newLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else {
				System.out.println("Invalid input. Try again? Enter 'y' or 'n'");
				cont = ask(scanner.nextLine());
			}
			System.out.println("Enter another input? 'y' or 'n'");
			cont = ask(scanner.nextLine());
		}
		System.out.println("End.");
		out.append("======== \r ========== \n ");
		out.flush();
		out.close();
	}
	public static void getInput(){
		System.out.println("Enter input: ");
		Scanner scanner = new Scanner(System.in);
		setString(scanner.nextLine());
	}
	public static boolean isEmpty(String string) {
		return !string.isEmpty();
	}
	public static String[] stringArray(String input) {
		strings = input.split(" ");
		return strings;
	}
	public static String getString() {
		return input;
	}
	public static void setString(String string) {
		Tester.input = string;
	}
	public static boolean hasAllElements(String[] arr, String target) {
		return Arrays.asList(arr).contains(target);
	}
	public static boolean ask(String string) {
		string.toLowerCase();
		switch(string) {
		case "y":
			return true;
		case "n":
			return false;
		default:
			return false;
		}
	
	}
	public static boolean isValidInput(String[] inputs, String[] flags) {
		int i = 0;
		boolean decision = true;
		while(i < flags.length && decision) {
			//System.out.println(flags[i]);
			if(hasAllElements(inputs, flags[i])){
				i++;
			}
			else {
				System.out.println("Wrong input. Please try again. Missing: " + flags[i]);
				decision = false;
			}
			
		}
		return decision;
	}
	public void parseString(String string) throws Exception {
		String[] strings = stringArray(string);
		if(!Arrays.asList(strings).contains("-m")) className = "TestHarness";
		for(int i = 0; i < strings.length; i++) {
			switch (strings[i].toLowerCase()) {
			case "-p":
				put = strings[i+1];
				if(!put.endsWith(".jar")) put += ".jar";
				break; 
			case "-o":
				oracle = strings[i+1];
				if(!oracle.endsWith(".jar")) oracle += ".jar";
				break;
			case "-s":
				try {
					seed = Long.parseLong(strings[i+1]); 
				}catch(Exception e){
					System.out.println("Invalid seed.");
					e.printStackTrace();
				}break;
			case "-n":
				try {
					numOfTestCases = Integer.parseInt(strings[i+1]);
				}catch(Exception e) {
					System.out.println("Invalid number of test cases.");
					e.printStackTrace();
				}break;
			case "-a":
				try {
					numOfParameters = Integer.parseInt(strings[i+1]);
				}catch(Exception e) {
					System.out.println("Invalid number of parameters.");
					e.printStackTrace();
				}
				break;
			case "-r":
				try {
					//expect 4 doubles.
					int numNums = 0;
					for(int j = i+1; j < strings.length; j++) {
						if(strings[j] != null) numNums += 1;
					}
					//System.out.println(numNums);
					if(numOfParameters == numNums/2){
						bound = new double[numOfParameters][2];
						for(int offSet = 0, l = 0; offSet < numNums; offSet += 2, l++) {
							bound[l][0] = Double.parseDouble(strings[i+offSet+1]);
							bound[l][1] = Double.parseDouble(strings[i+offSet+2]);
							//System.out.println(bound[l][0] + " " + bound[l][1]);
						}
					}else throw new Exception();
				}catch(Exception e) {
					System.out.println("Must have " + numOfParameters*2 + " doubles.");
					e.printStackTrace();
				}
				break;
			case "-m":
				className = strings[i+1];
				break;
			case "-R":
				try {
					originalExRatio = Double.parseDouble(strings[i+1]);
				}catch (Exception e) {
					System.out.println("Invalid exclusion ratio.");
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}
	public boolean fileExists(String string) {
		File file = new File(string);
		return file.exists();
	}

	public static Tester getTester() {
		return tester;
	}
	public boolean runProg(String program) throws Exception {
		boolean b = false;
		String in = "1 2 3";
		//program = put;
		String command = "java -jar " + program + " " + in;
		Process process = Runtime.getRuntime().exec(command);
		InputStream inputStream = process.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		if(bufferedReader.readLine()!=null) b = true;
		return b;
	}
	public String runProg(String program, String in) throws IOException {
		String command = "java -jar " + program + " " + in;
		//System.out.println(command);
		Process process = Runtime.getRuntime().exec(command);
		InputStream inputStream = process.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String out = bufferedReader.readLine();
		System.out.println(out);
		return out;
	}
	public String[][] createTestCases(){
		String[][] testCases = new String[numOfTestCases][numOfParameters];
		SevenPRNG sevenPRNG = new SevenPRNG(seed);
		for(int i = 0; i < numOfTestCases; i++) {
			for(int j = 0; j < numOfParameters; j++) {
				sevenPRNG.setRandomNumber(bound[j][0], bound[j][1]);
				testCases[i][j] = Integer.toString(sevenPRNG.getRandomNumber());
				//System.out.println(testCases[i][j]);
			}
		}
		return testCases;
	}
	public boolean putOracleEquals(String in) throws Exception {
		return tester.runProg(put, in).equals(tester.runProg(oracle, in));
	}
	public void setExRadius(String[][] exec) {
		int count = 0;
		int i = 0;
		while(i < exec.length && exec[i][0]!=null) {
			count++;
			i++;
		}
		if(count == 0) setExRatio(Math.sqrt(getTester().getOriginalExRatio() / Math.PI));
		else setExRatio(Math.sqrt(getTester().getOriginalExRatio() / count /Math.PI));
	}
	public double getOriginalExRatio() {
		return originalExRatio;
	}
	
	public void setExRatio(double ratio) {
		this.exRatio = ratio;
	}
	public double getExRatio() {
		return exRatio;
	}

	public String[] getFlags() {
		return flags;
	}
	public String[] getRFlags() {
		return rFlags;
	}
	public String[] getRTFlags() {
		return rtFlags;
	}
	public String getClassName() {
		return className;
	}
	public String getFileName() {
		return datFile;
	}
	public int getNumOfTestCases() {
		return numOfTestCases;
	}
	public int getNumOfParameters() {
		return numOfParameters;
	}

	
}


