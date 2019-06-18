package aab2apk;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.lingala.zip4j.model.ZipParameters;

/**
 * @author Gorav Gupta
 *
 */
public class ZipUtils {

	/***
	 * @param scr
	 * @param des
	 * @param sEntry
	 * @throws Exception
	 */
	static void unZipBase(Path scr, Path des, String sEntry) throws Exception {
		Files.createDirectories(des);
		try (ZipInputStream zIS = new ZipInputStream(new FileInputStream(scr.toAbsolutePath().toString()))) {
			ZipEntry entry = zIS.getNextEntry();

			while (entry != null) {

				if (entry.getName().startsWith(sEntry + "/") && !entry.getName().equals(sEntry + "/assets.pb")
						&& !entry.getName().equals(sEntry + "/native.pb")) {

					Path filePath = Paths.get(des.toAbsolutePath().toString(), entry.getName().replace(sEntry + "/", ""));
					if (entry.getName().startsWith(sEntry + "/dex/")) {
						filePath = Paths.get(des.toAbsolutePath().toString(), entry.getName().replace(sEntry + "/dex/", ""));
					} else if (entry.getName().startsWith(sEntry + "/manifest/")) {
						filePath = Paths.get(des.toAbsolutePath().toString(),
								entry.getName().replace(sEntry + "/manifest/", ""));
					} else if (entry.getName().startsWith(sEntry + "/root/")) {
						filePath = Paths.get(des.toAbsolutePath().toString(),
								entry.getName().replace(sEntry + "/root/", ""));
					}

					if (!entry.isDirectory()) {
						Path parent = filePath.getParent();
						if (parent != null && !Files.exists(parent))
							Files.createDirectories(parent);
						try (BufferedOutputStream bos = new BufferedOutputStream(
								new FileOutputStream(filePath.toAbsolutePath().toString()))) {
							byte[] buf = new byte[1024];
							int read = 0;
							while ((read = zIS.read(buf)) != -1) {
								bos.write(buf, 0, read);
							}
						}
					} else {
						Files.createDirectories(filePath);
					}
				}
				zIS.closeEntry();
				entry = zIS.getNextEntry();
			}
		}
	}

	/***
	 * @param src
	 * @param dst
	 * @throws Exception
	 */
	static void zipDir(String src, String dst) throws Exception {
		net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(dst);
		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setIncludeRootFolder(false);
		zipParameters.setCompressionLevel(0);
		Files.deleteIfExists(Paths.get(dst));
		zipFile.createZipFileFromFolder(src, zipParameters, false, 0);
	}
}
