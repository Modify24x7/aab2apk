package aab2apk;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

/**
 * @author Gorav Gupta
 *
 */
public class Main {

	private static final String ARG_IN = "-in";
	private static final String ARG_OUT = "-out";
	private static final String ARG_ENTRY = "-entry";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 6) {
			printUsage();
		} else {
			try {
				executeProcess(readArgs(args));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
			}
		}
	}

	/***
	 * @param argsList
	 * @throws Exception
	 */
	private static void executeProcess(HashMap<String, String> argsList) throws Exception {

		String wd = Files.createTempDirectory("aab2apk").toString();
		
		Files.copy(Main.class.getResourceAsStream("/aapt2.exe"), 
				Paths.get(wd + File.separator + "aapt2.exe"), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(Main.class.getResourceAsStream("/libwinpthread-1.dll"), 
				Paths.get(wd + File.separator + "libwinpthread-1.dll"), StandardCopyOption.REPLACE_EXISTING);
		
		if (AABProcess.convertAAB2AABApk(argsList.get(ARG_IN), wd, argsList.get(ARG_ENTRY)))
			System.out.println(CMD.execute(wd + File.separator + "aapt2.exe", "convert", "-o",
					argsList.get(ARG_OUT), wd + File.separator + "aab.apk"));
		
		Utils.cleanTempDir(Paths.get(wd));
	}

	private static void printUsage() {
		String command = "aab2apk.jar";
		System.out.println("AAB2APK Version: 0.1");
		System.out.println();
		System.out.println("Usage: java -jar " + command + " " + ARG_IN + " app.aab " + ARG_OUT + " app.apk " + ARG_ENTRY + " base");
		System.out.println();
		System.out.println("https://github.com/Modify24x7");
	}

	/***
	 * @param args
	 * @return
	 */
	private static HashMap<String, String> readArgs(String[] args) {
		HashMap<String, String> argsList = new HashMap<>();

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(ARG_IN)) {
				
				argsList.put(ARG_IN, args[++i]);

			} else if (args[i].equals(ARG_OUT)) {
				
				argsList.put(ARG_OUT, args[++i]);

				File parent = new File(args[i]).getParentFile();
				if (parent != null && (!parent.exists()))
					parent.mkdirs();
			} else if (args[i].equals(ARG_ENTRY)) {
				
				argsList.put(ARG_ENTRY, args[++i]);
			}
		}
		return argsList;
	}

}
