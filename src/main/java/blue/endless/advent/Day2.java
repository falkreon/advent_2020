package blue.endless.advent;

import java.io.IOException;

public class Day2 {
	
	public static boolean validate(int min, int max, char ch, String password) {
		int count = 0;
		for(int i=0; i<password.length(); i++) {
			if (password.charAt(i)==ch) count++;
		}
		
		return count>=min && count<=max;
	}
	
	public static boolean validate(String line) {
		String[] colonParts = line.split(":", 2);
		if (colonParts.length<2) throw new IllegalArgumentException("Can't find the colon separating the password from its validation spec in '"+line+"'.");
		
		String password = colonParts[1].trim();
		
		String[] specParts = colonParts[0].split(" ",2);
		if (specParts.length<2) throw new IllegalArgumentException("Can't interpret '"+colonParts[0]+"' as a password validation spec (can't find the space between the range and the character).");
		
		//Clean up any cruft the pattern might have left behind
		specParts[0] = specParts[0].trim();
		specParts[1] = specParts[1].trim();
		
		if (specParts[1].length()!=1) throw new IllegalArgumentException("Was expecting '"+specParts[1]+"' to be a character but its length is wrong.");
		char ch = specParts[1].charAt(0);
		
		//Decode range
		String[] rangeParts = specParts[0].split("\\-",2);
		if (rangeParts.length!=2) throw new IllegalArgumentException("Can't interpret '"+specParts[0]+"' as a range.");
		int min = Integer.parseInt(rangeParts[0]);
		int max = Integer.parseInt(rangeParts[1]);
		
		return validate(min, max, ch, password);
	}
	
	public static int countValid(String fileName) {
		try {
			int validCount = 0;
			for(String s : Common.readFileLines(fileName)) {
				String line = s.trim();
				if (line.isEmpty()) continue;
				
				if (validate(line)) validCount++;
			}
			
			return validCount;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void runDay2() {
		int answer = countValid("day2.dat");
		System.out.println("Number of valid passwords: "+answer);
	}
	
	//PART 2
	
	//Generalizing the above by extracting the validator into a function parameter:
	
	@FunctionalInterface
	public static interface PasswordValidator {
		public boolean validate(int a, int b, char ch, String s);
	}
	
	public static boolean validatePart2(int a, int b, char ch, String s) {
		s = s.trim();
		boolean aMatch = s.charAt(a-1)==ch;
		boolean bMatch = s.charAt(b-1)==ch;
		
		//return (aMatch||bMatch) && !(aMatch&&bMatch); //One but not both; we could alternatively say "aMatch^bMatch"
		//System.out.println(""+a+"-"+b+" "+ch+": "+s+" -> "+aMatch+" "+bMatch+" == "+(aMatch^bMatch));
		
		return aMatch^bMatch;
	}
	
	
	
	
	
	public static boolean validateGeneric(String line, PasswordValidator validator) {
		String[] colonParts = line.split(":", 2);
		if (colonParts.length<2) throw new IllegalArgumentException("Can't find the colon separating the password from its validation spec in '"+line+"'.");
		
		String password = colonParts[1].trim();
		
		String[] specParts = colonParts[0].split(" ",2);
		if (specParts.length<2) throw new IllegalArgumentException("Can't interpret '"+colonParts[0]+"' as a password validation spec (can't find the space between the range and the character).");
		
		//Clean up any cruft the pattern might have left behind
		specParts[0] = specParts[0].trim();
		specParts[1] = specParts[1].trim();
		
		if (specParts[1].length()!=1) throw new IllegalArgumentException("Was expecting '"+specParts[1]+"' to be a character but its length is wrong.");
		char ch = specParts[1].charAt(0);
		
		//Decode range
		String[] rangeParts = specParts[0].split("\\-",2);
		if (rangeParts.length!=2) throw new IllegalArgumentException("Can't interpret '"+specParts[0]+"' as a range.");
		int min = Integer.parseInt(rangeParts[0]);
		int max = Integer.parseInt(rangeParts[1]);
		
		return validator.validate(min, max, ch, password);
	}
	
	public static int countValidGeneric(String filename, PasswordValidator validator) {
		try {
			int validCount = 0;
			for(String s : Common.readFileLines(filename)) {
				String line = s.trim();
				if (line.isEmpty()) continue;
				
				if (validateGeneric(line, validator)) validCount++;
			}
			
			return validCount;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void genericDay2() {
		int answer = countValidGeneric("day2.dat", Day2::validate);
		System.out.println("Number of valid passwords: "+answer);
	}
	
	public static void genericDay2B() {
		int answer = countValidGeneric("day2.dat", Day2::validatePart2);
		System.out.println("Number of valid passwords: "+answer);
	}
}
