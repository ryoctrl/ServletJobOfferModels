package store;

import company.Path;
import storesystem.AbstractStoreSystem;
import storesystem.JsonStore;

public class JsonPathStore extends AbstractPathStore{	
	
	protected JsonPathStore() {
		super();
	}
	
	@Override
	protected void initializeModelName() {
		modelName = "paths";
	}

	@Override
	public void insert(Path obj) {
		super.insert(obj);
		records.add(obj);
		((JsonStore)storeSystem).saveToJson(records);
	}

	@Override
	protected void initializeStoreSystem() {
		storeSystem = new JsonStore<Path>(this);
	}
}
