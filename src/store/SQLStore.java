package store;

import model.models.Company;
import model.models.Storable;
import model.modelstores.AbstractModelStore;
import store.system.SQLStoreSystem;

public class SQLStore<T extends Storable> extends AbstractStore<T> {

	public SQLStore(AbstractModelStore<T> parent) {
		super(parent);
	}
	
	@Override
	protected void initializeStoreSystem() {
		storeSystem = new SQLStoreSystem<T>(this);
	}
	
	@Override
	public void insert(T obj) {
		T insertedCompany = storeSystem.insert(obj);
		if(insertedCompany == null || !(insertedCompany instanceof Company)) {
			return;
		}
		records.add(insertedCompany);
	}
}
