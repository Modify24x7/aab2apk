package aab2apk;

import java.io.File;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * @author Gorav Gupta
 *
 */
public class Utils {
	/***
	 * @param tempDir
	 * @throws Exception
	 */
	static void cleanTempDir(Path tempDir) throws Exception {
		Files.walk(tempDir, FileVisitOption.FOLLOW_LINKS).sorted(Comparator.reverseOrder()).map(Path::toFile)
				.forEach(File::delete);
	}
	
}
