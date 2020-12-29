package blue.endless.advent;

import java.io.IOException;
import java.util.List;

public class Day3 {
	
	public static void run() {
		try {
			Map map = new Map(Common.readFileLines("day3.dat"));
			
			int treesHit = 0;
			int x = 0;
			for(int y=0; y<map.getHeight(); y++) {
				if (map.isTree(x, y)) treesHit++;
				x += 3;
			}
			
			System.out.println("Trees hit: "+treesHit);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static class Map {
		final List<String> data;
		
		public Map(List<String> data) {
			this.data = data;
		}
		
		public boolean isTree(int x, int y) {
			if (y>=data.size()) return false;
			String line = data.get(y);
			char cell = line.charAt(x % line.length());
			return cell=='#';
		}
		
		public int getHeight() {
			return data.size();
		}
	}
	
	//PART B
	
	public static int run(Map map, int dx, int dy) {
		int treesHit = 0;
		int x = 0;
		for(int y=0; y<map.getHeight(); x += dx, y += dy) {
			if (map.isTree(x, y)) treesHit++;
		}
		
		return treesHit;
	}
	
	public static void runPartB() {
		try {
			Map map = new Map(Common.readFileLines("day3.dat"));
			
			long product = 1;
			
			long treesHit = 0L; //Just always stuff it into a long to prevent cast weirdness or overflow
			treesHit = run(map, 1, 1); product *= treesHit;
			treesHit = run(map, 3, 1); product *= treesHit;
			treesHit = run(map, 5, 1); product *= treesHit;
			treesHit = run(map, 7, 1); product *= treesHit;
			treesHit = run(map, 1, 2); product *= treesHit;
			
			System.out.println("Product of trees hit: "+product);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
