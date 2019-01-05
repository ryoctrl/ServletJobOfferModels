package store;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import company.Path;
import company.Utilities;

public class JsonPathStore extends AbstractPathStore{
	public static final String jsonFileName = "paths.json";
	private String jsonStr = "";
	
	protected JsonPathStore() {
		super();
		jsonStr = Utilities.readJsonFromFile(jsonFileName);
		
		JSONArray jsonArray = new JSONArray(jsonStr);
		for(Object o : jsonArray) {
			if( o instanceof JSONObject) {
				JSONObject obj = (JSONObject) o;
				int id = obj.getInt("id");
				String path = obj.getString("path");
				String name = obj.getString("name");
				int companyId = obj.getInt("companyId");
				Path p = new Path(id, path, name, companyId);
				records.add(p);
			}
		}
	}
	
	private void saveToJson() {
		ArrayList<HashMap> jsonList = new ArrayList<>();
		for(Path p : records) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("id", p.getId());
			map.put("path", p.getPath());
			map.put("name", p.getName());
			map.put("companyId", p.getCompanyId());
			jsonList.add(map);
		}
		JSONArray arr = new JSONArray(jsonList);
		Utilities.writeJsonToFile(jsonFileName, arr.toString());
	}

	@Override
	public void insert(Path obj) {
		super.insert(obj);
		records.add(obj);
		saveToJson();
	}

	@Override
	public Path findOneById(int id) {
		for(Path record : records) {
			if(id == record.getId()) return record;
		}
		return null;
	}

	@Override
	public ArrayList<Path> findAllByCompanyId(int id) {
		ArrayList<Path> paths = new ArrayList<>();
		for(Path record : records) {
			if(record.getCompanyId() == id) paths.add(record);
		}
		return paths;
	}

}
