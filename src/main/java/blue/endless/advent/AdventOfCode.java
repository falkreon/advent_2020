package blue.endless.advent;

public class AdventOfCode {
	public static void main(String... args) {
		if (args.length==0) {
			System.out.println("usage: java -jar AdventOfCode.jar <day> [params]");
			System.exit(-1);
		}
		
		switch(args[0]) {
		case "1":
		case "report_repair":
			Day1.reportRepair("day1.dat");
			
			
			break;
		case "1b":
			Day1.reportAdvanced("day1.dat");
			
			break;
		
		case "2":
			Day2.runDay2();
			break;
			
		case "2b":
			Day2.genericDay2B();
			break;
			
		case "3":
			Day3.run();
			break;
			
		case "3b":
			Day3.runPartB();
			break;
		}
	}
}
