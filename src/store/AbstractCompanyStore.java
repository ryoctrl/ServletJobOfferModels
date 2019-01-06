package store;

import java.util.ArrayList;
import java.util.Arrays;

import company.Company;
import company.CompanyManager;
import company.Constants;

public abstract class AbstractCompanyStore extends AbstractStore<Company> {
	private static AbstractCompanyStore instance = null;
	public static AbstractCompanyStore getInstance() {
		if(instance == null) {
			String storageEnv = Constants.Environments.STORAGE_TYPE;
			if(storageEnv == null || (!storageEnv.equals("json") && !storageEnv.equals("sql"))) storageEnv = "json";
			
			if(storageEnv.equals("json")) {
				instance = new JsonCompanyStore();
			} else {
				instance = new SQLCompanyStore();
			}
		}
		return instance;
	}
	
	protected AbstractCompanyStore() {
		super();
		initializeStoreSystem();
		records = storeSystem.initialLoad(Company.class);
	}
	
	@Override
	public void insert(Company obj) {
		obj.setId(getMaxId() + 1);
	}
	
	@Override
	public void includeExternalRecordIfNeeded(Company obj) {
		obj.setPaths(AbstractPathStore.getInstance().findAllByCompanyId(obj.getId()));
	}
	
	public Company findOneById(int id) {
		for(Company record : records) {
			if(record.getId() == id) return record;
		}
		return null;
	}
	
	public Company findOneByName(String name) {
		for(Company record : records) {
			if(record.getName().equals(name)) return record;
		}
		return null;
	}
	
	public ArrayList<Company> findAllByLocation(String location) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : records) {
			if(record.getLocation().equals(location)) companies.add(record);
		}
		return companies;
	}
	
	public ArrayList<Company> findAllByLocations(String[] locations) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : records) {
			if(Arrays.asList(locations).contains(record.getLocation())) companies.add(record);
		}
		return companies;
	}
	
	public ArrayList<Company> findAllByType(String type) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : records) {
			if(record.getType().equals(type)) companies.add(record);
		}
		return companies;
	}
	
	public ArrayList<Company> findAllByTypes(String[] types) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : records) {
			if(Arrays.asList(types).contains(record.getType())) companies.add(record);
		}
		return companies;
	}
}
