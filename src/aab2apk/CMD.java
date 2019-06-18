package aab2apk;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Gorav Gupta
 *
 */
public class CMD {
	
	/***
	 * @param args
	 * @return
	 */
	static String execute(String... args) {
		StringBuilder sb = new StringBuilder();
		Exception exception = null;
		int exitValue = -1;
		try {
			ProcessBuilder pb = new ProcessBuilder(args);
			pb.redirectErrorStream(true);
			Process process = pb.start();
			try(BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))){
				String s;
				while ((s = br.readLine()) !=null) {
					if (!s.isEmpty()) sb.append(s).append("\n");
				}
			}
			process.waitFor();
			exitValue = process.exitValue();
		} catch(Exception ex){
			exception = ex;
		}
		
		if (exception != null)
		return "exitValue:" + String.valueOf(exitValue) + "\n" + exception.toString();
		return sb.toString();
	}

}
