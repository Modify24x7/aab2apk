package aab2apk;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Gorav Gupta
 *
 */
public class AABProcess {

	/***
	 * @param inputAAB
	 * @param workingDir
	 * @param sEntry
	 * @return
	 * @throws Exception
	 */
	static boolean convertAAB2AABApk(String inputAAB, String workingDir, String sEntry) throws Exception {

		Path aabDir = new File(workingDir, "AAB").toPath();

		ZipUtils.unZipBase(Paths.get(inputAAB), aabDir, sEntry);
		ZipUtils.zipDir(aabDir.toString(), workingDir + File.separator + "aab.apk");
		Utils.cleanTempDir(aabDir);
		return true;
	}
}
