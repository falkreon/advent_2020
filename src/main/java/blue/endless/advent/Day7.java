package blue.endless.advent;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Day7 {
	public static final int MAX_SEARCH_DEPTH = 99;
	
	public static class BagEntry {
		public final String color;
		public final boolean mustBeEmpty;
		HashMap<String, Integer> requirements = new HashMap<>();
		
		public BagEntry(String spec) {
			String[] specParts = spec.split(Pattern.quote("bags contain"), 2);
			
			if (specParts.length != 2) throw new IllegalArgumentException();
			
			this.color = specParts[0].trim();
			specParts[1] = specParts[1].trim();
			if (specParts[1].endsWith(".")) specParts[1] = specParts[1].substring(0,specParts[1].length()-1);
			
			if (specParts[1].equals("no other bags")) {
				mustBeEmpty = true;
				return;
			} else {
				mustBeEmpty = false;
				
				String[] requirements = specParts[1].trim().split(Pattern.quote(","));
				for(String requirement : requirements) {
					requirement = requirement.trim();
					if (requirement.endsWith("bags")) {
						requirement = requirement.substring(0, requirement.length()-"bags".length());
					} else if (requirement.endsWith("bag")) {
						requirement = requirement.substring(0, requirement.length()-"bag".length());
					}
					
					String[] requirementPieces = requirement.split(" ", 2);
					if (requirementPieces.length<2) throw new IllegalArgumentException();
					
					Integer count = Integer.parseInt(requirementPieces[0].trim());
					String key = requirementPieces[1].trim();
					this.requirements.put(key, count);
				}
			}
		}
		
		public String getColor() {
			return color;
		}
		
		public boolean canContain(String color) {
			if (mustBeEmpty) return false;
			
			return requirements.containsKey(color);
		}
		
		public String toString() {
			StringBuilder result = new StringBuilder(color);
			result.append(": { ");
			if (mustBeEmpty) return result.append("no other bags }").toString();
			
			int limit = requirements.size();
			int cur = 0;
			for(Map.Entry<String, Integer> entry : requirements.entrySet()) {
				result.append(entry.getValue());
				result.append(' ');
				result.append(entry.getKey());
				result.append(' ');
				if (entry.getValue()==1) {
					result.append("bag");
				} else {
					result.append("bags");
				}
				cur++;
				if (cur<limit) result.append(", ");
			}
			result.append(" }");
			return result.toString();
		}
	}
	
	public static ArrayList<BagEntry> buildRules(List<String> rules) {
		ArrayList<BagEntry> result = new ArrayList<>();
		for(String rule : rules) {
			if (rule.trim().isEmpty()) continue;
			
			result.add(new BagEntry(rule));
		}
		
		return result;
	}
	
	/** Finds all the colors of bag which could immediately contain a bag of the specified color */
	public static Set<String> immediateContainers(String color, List<BagEntry> rules) {
		HashSet<String> result = new HashSet<>();
		for(BagEntry rule : rules) {
			if (rule.canContain(color)) result.add(rule.getColor());
		}
		return result;
	}
	
	public static Set<String> allContainers(String color, List<BagEntry> rules) {
		ArrayDeque<String> queue = new ArrayDeque<>();
		HashSet<String> result = new HashSet<>();
		
		queue.add(color);
		
		while(!queue.isEmpty()) {
			Set<String> found = immediateContainers(queue.pop(), rules);
			for(String s : found) {
				if (!result.contains(s)) {
					System.out.println(s+" can be contained by "+found);
					result.add(s);
					queue.add(s);
				}
			}
		}
		
		
		return result;
	}
	
	
	
	public static void run() {
		try {
			List<BagEntry> rules = buildRules(Common.readFileLines("day7.dat"));
			System.out.println("There are "+rules.size()+" rules.");
			System.out.println(rules);
			
			Set<String> result = allContainers("shiny gold", rules);
			
			System.out.println("There are "+result.size()+" options for outermost bag: "+result);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Returns the TOTAL number of bags required to be carried if you carry the given color bag.
	 * If the bag contains no other bags, the result is 1: the bag itself. If the bag contains one empty bag,
	 * the result is 2: the bag itself plus the inner bag.
	 * 
	 */
	public static int sizeOfOneBag(String color, List<BagEntry> rules, int depth) {
		for(BagEntry entry : rules) {
			if(entry.color.equals(color)) {
				if (entry.mustBeEmpty) return 1;
				if (depth>MAX_SEARCH_DEPTH) return -1;
				
				int result = 1;
				
				for(Map.Entry<String, Integer> requirement : entry.requirements.entrySet()) {
					int nestedSize = sizeOfOneBag(requirement.getKey(), rules, depth+1);
					if (nestedSize==-1) return -1;
					System.out.println("  Adding "+requirement.getKey()+"("+nestedSize+") * "+requirement.getValue()+" = "+(nestedSize*requirement.getValue())+" bags to "+color);
					result += nestedSize * requirement.getValue();
				}
				
				System.out.println("Found that "+color+" has size "+result+" with rules "+entry);
				return result;
			}
		}
		
		throw new IllegalArgumentException("Bag '"+color+"' not found!");
	}
	
	public static void runPartB() {
		try {
			List<BagEntry> rules = buildRules(Common.readFileLines("day7.dat"));
			System.out.println("There are "+rules.size()+" rules.");
			System.out.println(rules);
			
			int result = sizeOfOneBag("shiny gold", rules, 0);
			if (result==-1) {
				System.out.println("Shiny gold bags appear to be either topologically circular or extremely deep.");
			} else {
				System.out.println("Size of the shiny gold bag is "+(result-1));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
