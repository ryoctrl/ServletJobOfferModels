package store;

import java.util.ArrayList;

import model.IModelDefine;
import model.Models;
import model.models.Storable;
import store.system.AbstractStoreSystem;

public abstract class AbstractStore<T extends Storable> implements IStore<T> {
	protected ArrayList<T> records;
	protected IModelDefine model;
	protected String modelName;
	protected AbstractStoreSystem<T> storeSystem;
	
	protected AbstractStore() {
		records = new ArrayList<T>();
		initializeModelName();
		model = Models.getModel(modelName);
		initializeStoreSystem();
	}
	
	protected int getMaxId() {
		int maxId = 0;
		for(T record : records) {
			int currentId = record.getId();
			maxId = maxId < currentId ? currentId : maxId;
		}
		System.out.println("AbstractStore returning : " + maxId);
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
	
	public abstract void includeExternalRecordIfNeeded(T obj);
	
	protected abstract void initializeModelName();
	protected abstract void initializeStoreSystem();
}
