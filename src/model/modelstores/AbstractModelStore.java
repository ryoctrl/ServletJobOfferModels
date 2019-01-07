package model.modelstores;

import java.util.ArrayList;

import model.Models;
import model.models.Storable;
import store.JsonStore;
import store.SQLStore;
import store.AbstractStore;
import store.IStore;
import utilities.Constants.StoreType;

public abstract class AbstractModelStore<T extends Storable> implements IStore<T>{
	protected AbstractStore<T> store;
	
	protected AbstractModelStore() {
		
	}
	
	protected void recordsInitialize() {
		if(StoreType.getCurrentStoreType() == StoreType.JSON) {
			store = new JsonStore<T>(this);
		} else {
			store = new SQLStore<T>(this);
		}
		store.recordsInitialize();
	}
	
	@Override
	public void insert(T obj) {
		store.insert(obj);
	}
	
	@Override
	public ArrayList<T> getAll() {
		return store.getAll();
	}
	
	public abstract void includeForeignRecordIfNeeded(T obj);
	public abstract String getModelName();
	public abstract Class<T> getModelClass();
}
