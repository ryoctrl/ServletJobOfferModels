package store;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.json.JSONObject;

import company.Company;
import company.Path;
import company.Utilities;
import model.Models;

import org.json.JSONArray;

public class JsonCompanyStore extends AbstractCompanyStore{
	public static final String jsonFileName = "companies.json";
	private String jsonStr = "";
	
	@Override
	protected void modelNameInitialize() {
		modelName = "companies";
	}
	
	protected JsonCompanyStore() {
		super();
		jsonStr = Utilities.readJsonFromFile(jsonFileName);
		
		JSONArray jsonArray = new JSONArray(jsonStr);
		AbstractPathStore ps = AbstractPathStore.getInstance();
		Set<String> keys = Models.getModel(modelName).getModelKeys();
		for(Object o : jsonArray) {
			if( o instanceof JSONObject) {
				Company c = new Company();
				JSONObject obj = (JSONObject) o;
				keys.forEach(key -> {
					try {
						new PropertyDescriptor(key, c.getClass()).getWriteMethod().invoke(c, obj.get(key));
					}catch(Exception e) {
						e.printStackTrace();
						System.exit(1);
					}
				});
				c.setPaths(ps.findAllByCompanyId(c.getId()));
				records.add(c);
			}
		}
	}
	
	private void saveToJson() {
		ArrayList<HashMap> jsonList = new ArrayList<>();
		Set<String> keys = Models.getModel(modelName).getModelKeys();
		for(Company c : records) {
			HashMap<String, Object> map = new HashMap<>();
			keys.forEach(key -> {
				try {
					map.put(key, new PropertyDescriptor(key, c.getClass()).getReadMethod().invoke(c));
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
			if(record.getLocation().equals(location)) companies.add(record);
		}
		return companies;
	}

	@Override
	public ArrayList<Company> findAllByType(String type) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : records) {
			if(record.getType().equals(type)) companies.add(record);
		}
		return companies;
	}

	@Override
	public ArrayList<Company> findAllByLocations(String[] locations) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : records) {
			if(Arrays.asList(locations).contains(record.getLocation())) companies.add(record);
		}
		return companies;
	}

	@Override
	public ArrayList<Company> findAllByTypes(String[] types) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : records) {
			if(Arrays.asList(types).contains(record.getType())) companies.add(record);
		}
		return companies;
	}

}
