package model.modelstores;

import java.util.ArrayList;
import java.util.Arrays;

import model.models.Company;

public class CompanyModelStore extends AbstractModelStore<Company>{

	@Override
	public void includeForeignRecordIfNeeded(Company obj) {
		obj.setPaths(((PathModelStore)StoreProvider.getModelStore("paths")).findAllByCompanyId(obj.getId()));
	}

	@Override
	public String getModelName() {
		return "companies";
	}

	@Override
	public Class<Company> getModelClass() {
		return Company.class;
	}

	public Company findOneById(int id) {
		for(Company record : store.getAll()) {
			if(record.getId() == id) return record;
		}
		return null;
	}
	
	public Company findOneByName(String name) {
		for(Company record : store.getAll()) {
			if(record.getName().equals(name)) return record;
		}
		return null;
	}
	
	public ArrayList<Company> findAllByLocation(String location) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : store.getAll()) {
			if(record.getLocation().equals(location)) companies.add(record);
		}
		return companies;
	}
	
	public ArrayList<Company> findAllByLocations(String[] locations) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : store.getAll()) {
			if(Arrays.asList(locations).contains(record.getLocation())) companies.add(record);
		}
		return companies;
	}
	
	public ArrayList<Company> findAllByType(String type) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : store.getAll()) {
			if(record.getType().equals(type)) companies.add(record);
		}
		return companies;
	}
	
	public ArrayList<Company> findAllByTypes(String[] types) {
		ArrayList<Company> companies = new ArrayList<>();
		for(Company record : store.getAll()) {
			if(Arrays.asList(types).contains(record.getType())) companies.add(record);
		}
		return companies;
	}
}
