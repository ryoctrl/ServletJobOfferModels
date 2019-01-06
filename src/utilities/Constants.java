package utilities;

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
	
	public static class Keys {
		public static final String MODEL_DEFINE_PACKAGE = "model.define";
	}
	
	public static class ModelTypes {
		public static final String EXTERNAL_COLUMN = "External";
		public static final String STRING_COLUMN = "String";
		public static final String INTEGER_COLUMN = "int";
	}
}
