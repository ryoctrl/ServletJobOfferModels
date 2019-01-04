package store;

import java.util.ArrayList;

import company.Company;
import company.IStore;

public abstract class AbstractStore<T extends Storable> implements IStore<T> {
	protected ArrayList<T> records;
	
	protected AbstractStore() {
		records = new ArrayList<T>();
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
}
