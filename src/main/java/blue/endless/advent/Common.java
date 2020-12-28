package blue.endless.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Common {
	public static Path getPath(String filename) throws FileNotFoundException {
		File dataFolder = new File("data");
		File result = new File(dataFolder, filename);
		if (result.exists()) {
			return result.toPath();
		} else {
			throw new FileNotFoundException("Can't find file "+filename);
		}
	}
	
	public static String readFileString(String filename) throws IOException {
		return new String(Files.readAllBytes(getPath(filename)), StandardCharsets.UTF_8);
	}
	
	public static List<String> readFileLines(String filename) throws IOException {
		return Files.readAllLines(getPath(filename), StandardCharsets.UTF_8);
	}
}
