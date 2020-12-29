package blue.endless.advent;

public class Range {
	private final int min;
	private final int max;
	
	/** Creates a closed range of the integers [min..max] */
	public Range(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public boolean contains(int i) {
		return i>=min && i<=max;
	}
	
	/** Creates a closed range of the integers [min..max-1], which is equivalent to the half-open range of integers [min..max) */
	public static Range halfOpen(int min, int max) {
		return new Range(min, max-1);
	}
}
