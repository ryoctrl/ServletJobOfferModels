package store;

import model.models.Storable;
import model.modelstores.AbstractModelStore;
import store.system.JsonStoreSystem;

public class JsonStore<T extends Storable> extends AbstractStore<T>{

	public JsonStore(AbstractModelStore<T> parent) {
		super(parent);
	}
	
	@Override
	protected void initializeStoreSystem() {
		storeSystem = new JsonStoreSystem<T>(this);
	}
	
	@Override
	public void insert(T obj) {
		super.insert(obj);
		records.add(obj);
		((JsonStoreSystem<T>)storeSystem).saveToJson(records);
	}
	
}
