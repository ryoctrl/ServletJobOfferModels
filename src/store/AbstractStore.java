package store;

import java.util.ArrayList;

import model.IModelDefine;
import model.Models;
import model.models.Company;
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
	}
	
	protected int getMaxId() {
		int maxId = 0;
		for(T record : records) {
			int currentId = record.getId();
			if(record instanceof Company) {
				Company c = (Company) record;
			}
			maxId = maxId < currentId ? currentId : maxId;
		}
		return maxId;
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
