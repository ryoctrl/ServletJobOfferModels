package utilities;

import java.util.Date;

public class Logger {
	public static void info(String message) {
		Date now = new Date();
		message = now.toString() + " " +message;
		System.out.println(message);
	}
	
	public static void fatal(String message) {
		
	}
}
