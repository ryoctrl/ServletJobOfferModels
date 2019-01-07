package store;

import java.util.ArrayList;

import model.IModelDefine;
import model.Models;
import model.models.Storable;
import model.modelstores.AbstractModelStore;
import store.system.AbstractStoreSystem;

public abstract class AbstractStore<T extends Storable> implements IStore<T> {
	protected ArrayList<T> records;
	protected IModelDefine model;
	protected String modelName;
	protected AbstractStoreSystem<T> storeSystem;
	protected AbstractModelStore<T> parent;
	
	protected AbstractStore(AbstractModelStore<T> parent) {
		this.parent = parent;
		modelName = parent.getModelName();
		model = Models.getModel(modelName);
		initializeStoreSystem();
	}
	
	public void recordsInitialize() {
		records = storeSystem.initialLoad(parent.getModelClass());
	}
	
	protected int getMaxId() {
		int maxId = 0;
		for(T record : records) {
			int currentId = record.getId();
			maxId = maxId < currentId ? currentId : maxId;
		}
		return maxId;
	}
	
	@Override
	public void insert(T obj) {
		obj.setId(getMaxId() + 1);
	}
	
	@Override
	public ArrayList<T> getAll() {
		return records;
	}
	
	public String getModelName() {
		return this.modelName;
	}
	
	public IModelDefine getModelDefine() {
		return this.model;
	}
	
	public void includeForeignRecordIfNeeded(T obj) {
		parent.includeForeignRecordIfNeeded(obj);
	}
	
	protected abstract void initializeStoreSystem();
}
