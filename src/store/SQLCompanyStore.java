package store;

import model.models.Company;
import model.models.Storable;
import store.system.SQLStore;

/**
 * There is the problem of can't access to same object when register new path object if override query methods By SQL Query.
 * @author mosin
 *
 */
public class SQLCompanyStore extends AbstractCompanyStore{
	
	protected SQLCompanyStore() {
		super();
	}
	
	@Override
	protected void initializeStoreSystem() {
		storeSystem = new SQLStore<Company>(this);
	}
	
	@Override
	public void insert(Company obj) {
		Storable insertedCompany = storeSystem.insert(obj);
		if(insertedCompany == null || !(insertedCompany instanceof Company)) {
			return;
		}
		records.add((Company)insertedCompany);
	}
}
