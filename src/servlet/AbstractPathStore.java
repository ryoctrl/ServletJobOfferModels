package servlet;

import java.util.ArrayList;

public abstract class AbstractPathStore extends AbstractStore<Path> {
	private static AbstractPathStore instance = null;
	public static AbstractPathStore getInstance() {
		if(instance == null) {
			String storageEnv = System.getenv(CompanyManager.STORAGE_KEY);
			if(storageEnv == null || (!storageEnv.equals("json") && !storageEnv.equals("sql"))) storageEnv = "json";
			
			if(storageEnv.equals("json")) {
				instance = new JsonPathStore();
			} else {
				instance = new SQLPathStore();
			}
		}
		return instance;
	}
	
	@Override
	public void insert(Path obj) {
		obj.setId(getMaxId() + 1);
	}
	
	public abstract Path findOneById(int id);
	public abstract ArrayList<Path> findAllByCompanyId(int id);
}
