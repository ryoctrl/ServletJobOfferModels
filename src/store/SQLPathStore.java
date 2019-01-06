package store;

import model.models.Path;
import model.models.Storable;
import storesystem.SQLStore;

public class SQLPathStore extends AbstractPathStore{
	
	protected SQLPathStore() {
		super();
	}
	
	@Override
	protected void initializeModelName() {
		modelName = "paths";
	}
	
	@Override
	public void insert(Path obj) {
		Storable insertedPath = storeSystem.insert(obj);
		if(insertedPath == null || !(insertedPath instanceof Path)) {
			return;
		}
		records.add((Path)insertedPath);
	}

	@Override
	protected void initializeStoreSystem() {
		storeSystem = new SQLStore<Path>(this);
	}
}
