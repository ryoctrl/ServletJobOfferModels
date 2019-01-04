package store;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import company.Company;
import company.Path;
import company.Utilities;

import org.json.JSONArray;

public class JsonCompanyStore extends AbstractCompanyStore{
	public static final String jsonFileName = "companies.json";
	private String jsonStr = "";
	
	protected JsonCompanyStore() {
		super();
		jsonStr = Utilities.readJsonFromFile(jsonFileName);
		
		JSONArray jsonArray = new JSONArray(jsonStr);
		AbstractPathStore ps = AbstractPathStore.getInstance();
		for(Object o : jsonArray) {
			if( o instanceof JSONObject) {
				JSONObject obj = (JSONObject) o;
				int id = obj.getInt("id");
				String name = obj.getString("name");
				String location = obj.getString("location");
				String type = obj.getString("type");
				ArrayList<Path> paths = ps.findAllByCompanyId(id);
				Company c = new Company(id, name, location, type, paths);
				records.add(c);
			}
		}
	}
	
	private void saveToJson() {
		ArrayList<HashMap> jsonList = new ArrayList<>();
		for(Company c : records) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("id", c.getId());
			map.put("name", c.getName());
			map.put("location", c.getLocation());
			map.put("type", c.getType());
			jsonList.add(map);
		}
		JSONArray arr = new JSONArray(jsonList);
		Utilities.writeJsonToFile(jsonFileName, arr.toString());
	}

	@Override
	public void insert(Company obj) {
		super.insert(obj);
		records.add(obj);
		saveToJson();
	}

	@Override
	public Company findOneById(int id) {
		for(Company record : records) {
			if(record.getId() == id) return record;
		}
		return null;
	}

	@Override
	public Company findOneByName(String name) {
		for(Company record : records) {
			if(record.getName().equals(name)) return record;
		}
		return null;
	}

	@Override
	public ArrayList<Company> findAllByLocation(String location) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : records) {
			if(record.getName().equals(location)) companies.add(record);
		}
		return companies;
	}

	@Override
	public ArrayList<Company> findAllByType(String type) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : records) {
			if(record.getName().equals(type)) companies.add(record);
		}
		return companies;
	}

}
