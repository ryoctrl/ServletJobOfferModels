package store;

import model.models.Company;
import store.system.JsonStore;

public class JsonCompanyStore extends AbstractCompanyStore{
	protected JsonCompanyStore() {
		super();
	}
	
	protected void initializeStoreSystem() {
		storeSystem = new JsonStore<Company>(this);
	}

	@Override
	public void insert(Company obj) {
		super.insert(obj);
		records.add(obj);
		((JsonStore)storeSystem).saveToJson(records);
	}
}
