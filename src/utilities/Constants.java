package utilities;

import java.util.HashMap;
import model.ModelOption;

public final class Constants {
	private Constants() {
	}
	public static class Environments {
		public static final String STORAGE_TYPE = System.getenv("STORAGE_TYPE");
		public static final String JSON_DIR = System.getenv("JSON_DIR");
		public static final String DB_NAME = System.getenv("DB_NAME");
		public static final String DB_HOST = System.getenv("DB_HOST");
		public static final String DB_USER = System.getenv("DB_USER");
		public static final String DB_PASS = System.getenv("DB_PASS");
		
	}
}
