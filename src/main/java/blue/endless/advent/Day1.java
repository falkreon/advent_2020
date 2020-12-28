package blue.endless.advent;

import java.io.IOException;
import java.util.List;

public class Day1 {
	public static void reportRepair(String filename) {
		try {
			List<String> input = Common.readFileLines(filename);
			int[] entries = input.stream().mapToInt(Integer::parseInt).toArray();
			
			for(int i=0; i<entries.length; i++) {
				for(int j=0; j<entries.length; j++) {
					if (i==j) continue; //The wording says "find the *two* entries" (emphasis mine), so matching an entry with itself is verboten.
					
					int sum = entries[i]+entries[j];
					if (sum==2020) {
						long product = (long) entries[i] * (long) entries[j];
						System.out.println(""+entries[i]+" + "+entries[j]+" = "+product);
						return;
					}
				}
			}
			System.out.println("We did not find any two entries that sum to 2020! :o");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reportAdvanced(String filename) {
		try {
			List<String> input = Common.readFileLines(filename);
			int[] entries = input.stream().mapToInt(Integer::parseInt).toArray();
			
			for(int i=0; i<entries.length; i++) {
				for(int j=0; j<entries.length; j++) {
					for(int k=0; k<entries.length; k++) {
						if (i==j) continue;
						if (j==k) continue;
						if (i==k) continue;
						
						int sum = entries[i]+entries[j]+entries[k];
						if (sum==2020) {
							long product = (long) entries[i] * (long) entries[j] * (long) entries[k];
							System.out.println(""+entries[i]+" + "+entries[j]+" + "+entries[k]+" = "+product);
							return;
						}
					}
				}
			}
			System.out.println("We did not find any three entries that sum to 2020! :o");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
