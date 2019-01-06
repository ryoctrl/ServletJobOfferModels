package store;

import model.models.Path;
import store.system.AbstractStoreSystem;
import store.system.JsonStore;

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
