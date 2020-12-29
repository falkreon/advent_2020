package blue.endless.advent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day4 {
	
	public static ArrayList<HashMap<String, String>> getPassports(List<String> lines) {
		ArrayList<HashMap<String, String>> result = new ArrayList<>();
		
		HashMap<String, String> cur = new HashMap<>();
		for(String line : lines) {
			line = line.trim();
			if (line.isEmpty()) {
				if (!cur.isEmpty()) result.add(cur);
				cur = new HashMap<>();
			} else {
				String[] mappings = line.split(" ");
				for(String mapping : mappings) {
					String[] kv = mapping.split(":");
					if (kv.length==2) {
						String key = kv[0].trim();
						String value = kv[1].trim();
						cur.put(key, value);
					} else {
						throw new IllegalArgumentException("Could not understand '"+mapping+"' as a key-value pair.");
					}
				}
			}
		}
		if (!cur.isEmpty()) result.add(cur);
		
		return result;
	}
	
	public static String[] requiredKeys = { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" }; //but not cid
	
	public static boolean validatePassport(HashMap<String, String> passport, String[] requiredKeys) {
		for(String s : requiredKeys) {
			if (!passport.containsKey(s)) return false;
		}
		
		return true;
	}
	
	
	
	public static void run() {
		try {
			ArrayList<HashMap<String, String>> passports = getPassports(Common.readFileLines("day4.dat"));
			int validPassports = 0;
			for(HashMap<String, String> passport : passports) {
				if (validatePassport(passport, requiredKeys)) validPassports++;
			}
			
			System.out.println("Valid passports: "+validPassports);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Extract as much validation information up to constants as possible
	//TODO: Also extract passport keys?
	public static final List<Character> VALID_HEX_CHARS = Arrays.asList(new Character[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'});
	public static final List<Character> VALID_DIGITS = Arrays.asList(new Character[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
	public static final List<String> VALID_EYE_COLORS = Arrays.asList(new String[]{ "amb", "blu", "brn", "gry", "grn", "hzl", "oth" });
	public static final Range VALID_BIRTH_YEARS = new Range(1920, 2002);
	public static final Range VALID_ISSUE_YEARS = new Range(2010, 2020);
	public static final Range VALID_EXPIRATION_YEARS = new Range(2020, 2030);
	public static final Range VALID_HEIGHT_CM = new Range(150, 193);
	public static final Range VALID_HEIGHT_IN = new Range(59, 76);
	
	public static boolean validateYear(String yearString, Range validRange) {
		if (yearString.length()!=4) return false;
		try {
		int year = Integer.parseInt(yearString);
		return validRange.contains(year);
		
		} catch (Exception ex) { //mostly NumberFormatException and IllegalArgumentException from Integer.parseInt
			return false;
		}
	}
	
	public static boolean validatePassportComprehensive(HashMap<String, String> passport) {
		for(String s : requiredKeys) {
			if (!passport.containsKey(s)) return false;
		}
		
		if (!validateYear(passport.get("byr"), VALID_BIRTH_YEARS)) return false;
		if (!validateYear(passport.get("iyr"), VALID_ISSUE_YEARS)) return false;
		if (!validateYear(passport.get("eyr"), VALID_EXPIRATION_YEARS)) return false;
		String hgt = passport.get("hgt");
		int heightValue = 0;
		try {
			heightValue = Integer.parseInt(hgt.substring(0, hgt.length()-2));
		} catch (Exception ex) {
			return false;
		}
		
		if (hgt.endsWith("cm")) {
			if (!VALID_HEIGHT_CM.contains(heightValue)) return false;
		} else if (hgt.endsWith("in")) {
			if (!VALID_HEIGHT_IN.contains(heightValue)) return false;
		} else {
			return false;
		}
		
		String hcl = passport.get("hcl");
		if (!hcl.startsWith("#")) return false;
		hcl = hcl.substring(1);
		if (hcl.length()!=6) return false;
		if (!hcl.chars().allMatch(it->VALID_HEX_CHARS.contains((char)it))) return false;
		
		if (!VALID_EYE_COLORS.contains(passport.get("ecl"))) return false;
		
		String pid = passport.get("pid");
		if (pid.length()!=9) return false;
		if (!pid.chars().allMatch(it->VALID_DIGITS.contains((char)it))) return false;
		
		return true;
	}
	
	public static void runPartB() {
		try {
			ArrayList<HashMap<String, String>> passports = getPassports(Common.readFileLines("day4.dat"));
			int validPassports = 0;
			for(HashMap<String, String> passport : passports) {
				if (validatePassportComprehensive(passport)) validPassports++;
			}
			
			System.out.println("Valid passports: "+validPassports);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
