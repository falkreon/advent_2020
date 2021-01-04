package blue.endless.advent;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class Day6 {
	
	public static int sumOfCounts(List<String> lines) {
		int sum = 0;
		HashSet<Character> curGroup = new HashSet<Character>();
		for(String line : lines) {
			if (line.trim().isEmpty()) {
				sum += curGroup.size();
				curGroup.clear();
			} else {
				line.chars().forEach(it->curGroup.add((char)it));
			}
		}
		
		if (!curGroup.isEmpty()) sum+= curGroup.size();
		
		return sum;
	}
	
	public static int sumOfUnanimousAgreements(List<String> lines) {
		int sum = 0;
		boolean firstInGroup = true;
		HashSet<Character> curGroup = new HashSet<Character>();
		for(String line : lines) {
			if (line.trim().isEmpty()) {
				firstInGroup = true;
				sum += curGroup.size();
				System.out.println("Adding "+curGroup.size()+" to sum (now "+sum+")");
				curGroup.clear();
				System.out.println("---New Group---");
			} else {
				
				if (firstInGroup) {
					//Add EVERY character to the set
					line.chars().forEach(it->curGroup.add((char)it));
					
					firstInGroup = false;
				} else {
					//Pare down the set to only those unanimously contained
					HashSet<Character> unionGroup = new HashSet<Character>();
					line.chars().forEach(it->unionGroup.add((char)it));
					System.out.println("curGroup: "+curGroup+" UnionGroup: "+unionGroup);
					curGroup.retainAll(unionGroup);
					System.out.println("Retained: "+curGroup);
					
				}
			}
		}
		
		if (!curGroup.isEmpty()) sum+= curGroup.size();
		
		return sum;
	}
	
	
	
	
	public static void run() {
		try {
			int answer = sumOfCounts(Common.readFileLines("day6.dat"));
			System.out.println("Sum of yes-answered questions in each group: "+answer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void runPartB() {
		try {
			int answer = sumOfUnanimousAgreements(Common.readFileLines("day6.dat"));
			System.out.println("Sum of unanimously yes-answered questions in each group: "+answer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
