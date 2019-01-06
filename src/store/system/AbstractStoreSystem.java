package store.system;

import java.util.ArrayList;

import model.IModelDefine;
import model.models.Storable;
import store.AbstractStore;

public abstract class AbstractStoreSystem<T extends Storable> {
	protected IModelDefine model;
	protected String modelName;
	protected AbstractStore<T> store;
	public AbstractStoreSystem(AbstractStore<T> store) {
		this.store = store;
		model = store.getModelDefine();
		modelName = store.getModelName();
	}
	public abstract ArrayList<T> initialLoad(Class<T> modelClass);
	public abstract T insert(T obj);
}
