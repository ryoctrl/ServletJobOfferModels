package storesystem;

import java.util.ArrayList;

import store.AbstractStore;
import store.Storable;

public abstract class AbstractStoreSystem<T extends Storable> {
	protected AbstractStore<T> store;
	public AbstractStoreSystem(AbstractStore<T> store) {
		this.store = store;
	}
	public abstract ArrayList<T> initialLoad(Class<T> modelClass);
	public abstract void insert(T obj);
}
