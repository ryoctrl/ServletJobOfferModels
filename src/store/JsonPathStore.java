package store;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import company.Company;
import company.Path;
import company.Utilities;
import model.Models;

public class JsonPathStore extends AbstractPathStore{
	public static final String jsonFileName = "paths.json";
	private String jsonStr = "";
	
	@Override
	protected void modelNameInitialize() {
		modelName = "paths";
	}
	
	protected JsonPathStore() {
		super();
		jsonStr = Utilities.readJsonFromFile(jsonFileName);
		
		JSONArray jsonArray = new JSONArray(jsonStr);
		Set<String> keys = Models.getModel(modelName).getModelKeys();
		for(Object o : jsonArray) {
			if( o instanceof JSONObject) {
				Path p = new Path();
				JSONObject obj = (JSONObject) o;
				keys.forEach(key -> {
					try {
						new PropertyDescriptor(key, p.getClass()).getWriteMethod().invoke(p, obj.get(key));
					}catch(Exception e) {
						e.printStackTrace();
						System.exit(1);
					}
				});
				records.add(p);
			}
		}
	}
	
	private void saveToJson() {
		ArrayList<HashMap> jsonList = new ArrayList<>();
		Set<String> keys = Models.getModel(modelName).getModelKeys();
		for(Path p : records) {
			HashMap<String, Object> map = new HashMap<>();
			keys.forEach(key -> {
				try {
					map.put(key, new PropertyDescriptor(key, p.getClass()).getReadMethod().invoke(p));
				}catch(Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			});
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
