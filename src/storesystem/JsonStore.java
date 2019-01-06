package storesystem;

import java.util.ArrayList;

import store.AbstractStore;
import store.Storable;

public class JsonStore<T extends Storable> extends AbstractStoreSystem<T> {

	public JsonStore(AbstractStore<T> store) {
		super(store);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insert(T obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<T> initialLoad(Class<T> modelClass) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
