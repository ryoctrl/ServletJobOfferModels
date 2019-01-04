package store;

import java.util.ArrayList;

import company.Company;
import company.CompanyManager;

public abstract class AbstractCompanyStore extends AbstractStore<Company> {
	private static AbstractCompanyStore instance = null;
	public static AbstractCompanyStore getInstance() {
		if(instance == null) {
			String storageEnv = System.getenv(CompanyManager.STORAGE_KEY);
			if(storageEnv == null || (!storageEnv.equals("json") && !storageEnv.equals("sql"))) storageEnv = "json";
			
			if(storageEnv.equals("json")) {
				instance = new JsonCompanyStore();
			} else {
				instance = new SQLCompanyStore();
			}
		}
		return instance;
	}
	
	@Override
	public void insert(Company obj) {
		obj.setId(getMaxId() + 1);
	}
	
	public abstract Company findOneById(int id);
	public abstract Company findOneByName(String name);
	public abstract ArrayList<Company> findAllByLocation(String location);
	public abstract ArrayList<Company> findAllByType(String type);
}
