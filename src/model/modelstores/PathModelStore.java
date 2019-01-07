package model.modelstores;

import java.util.ArrayList;

import model.models.Path;

public class PathModelStore extends AbstractModelStore<Path>{

	@Override
	public void includeForeignRecordIfNeeded(Path obj) {	}

	@Override
	public String getModelName() {
		return "paths";
	}

	@Override
	public Class<Path> getModelClass() {
		return Path.class;
	}
	
	public Path findOneById(int id) {
		for(Path record : store.getAll()) {
			if(id == record.getId()) return record;
		}
		return null;
	}
	
	public ArrayList<Path> findAllByCompanyId(int id) {
		ArrayList<Path> paths = new ArrayList<>();
		for(Path record : store.getAll()) {
			if(record.getCompanyId() == id) paths.add(record);
		}
		return paths;
	}

}
