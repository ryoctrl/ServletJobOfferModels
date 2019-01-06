package store;

import java.util.ArrayList;

import model.models.Path;
import utilities.Constants;

public abstract class AbstractPathStore extends AbstractStore<Path> {
	private static AbstractPathStore instance = null;
	public static AbstractPathStore getInstance() {
		if(instance == null) {
			String storageEnv = Constants.Environments.STORAGE_TYPE;
			if(storageEnv == null || (!storageEnv.equals("json") && !storageEnv.equals("sql"))) storageEnv = "json";
			
			if(storageEnv.equals("json")) {
				instance = new JsonPathStore();
			} else {
				instance = new SQLPathStore();
			}
		}
		return instance;
	}
	
	
	protected AbstractPathStore() {
		super();
		records = storeSystem.initialLoad(Path.class);
	}
	
	@Override
	protected void initializeModelName() {
		modelName = "companies";
	}

	@Override
	public void includeExternalRecordIfNeeded(Path obj) {}
	
	public Path findOneById(int id) {
		for(Path record : records) {
			if(id == record.getId()) return record;
		}
		return null;
	}
	
	public ArrayList<Path> findAllByCompanyId(int id) {
		ArrayList<Path> paths = new ArrayList<>();
		for(Path record : records) {
			if(record.getCompanyId() == id) paths.add(record);
		}
		return paths;
	}
	

}
