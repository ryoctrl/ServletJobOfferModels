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
		public static final String MODEL_STORE_PACKAGE = "model.modelstores";
	}
	
	public enum StoreType {
		JSON("json"),
		MY_SQL("sql");
		
		private final String storageType;
		
		private StoreType(final String storageType) {
			this.storageType = storageType;
		}
		
		public String getStorageType() {
			return this.storageType;
		}
		
		public static StoreType getCurrentStoreType() {
			for(StoreType s : StoreType.values()) {
				if(s.getStorageType().equals(Constants.Environments.STORAGE_TYPE)) return s;
			}
			return StoreType.JSON;
		}
	}
	
	public enum ModelType {
		FOREIGN(null),
		STRING("TEXT"),
		INTEGER("INT");
		
		private final String sqlType;
		
		private ModelType(final String sqlType) {
			this.sqlType = sqlType;
		}
		
		public String getSqlType() {
			return this.sqlType;
		}
		
	}
}
