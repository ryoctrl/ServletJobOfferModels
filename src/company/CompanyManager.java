package company;

import java.util.ArrayList;
import java.util.Arrays;

import store.AbstractCompanyStore;
import store.AbstractPathStore;

public class CompanyManager {
	public final static String STORAGE_KEY = "STORAGE_TYPE";
	private AbstractCompanyStore cs;
	private AbstractPathStore ps;
	
	private static CompanyManager instance = null;
	public static CompanyManager getInstance() {
		if(instance == null) {
			instance = new CompanyManager();
		}
		return instance;
	}
	
	private CompanyManager() {
		initialize();
	}
	
	
	//WARNING: CompanyStore is dependence to PathStore. So should instantiate PathStore before instantiate CompanyStore
	private void initialize() {
		ps = AbstractPathStore.getInstance();
		cs = AbstractCompanyStore.getInstance();
	}
	
	public Company registerNewCompany(String name, String location, String type) {
		Company newCompany = new Company(name, location, type);
		cs.insert(newCompany);
		return newCompany;
	}
	
	public Path registerNewPath(String path, String name, int companyId) {
		Path newPath = new Path(path,name, companyId);
		ps.insert(newPath);
		cs.findOneById(companyId).addPath(newPath);
		return newPath;
	}
	
	public Company findOneByCompanyId(int companyId) {
		return cs.findOneById(companyId);
	}
	
	public ArrayList<Company> findAllByLocation(String location) {
		return cs.findAllByLocation(location);
	}
	
	public ArrayList<Company> findAllByType(String type) {
		return cs.findAllByType(type);
	}
	
	public ArrayList<Company> getAllCompanies() {
		return cs.getAll();
	}
	
	public ArrayList<Company> findAllByLocations(String[] locations) {
		return cs.findAllByLocations(locations);
	}

	public ArrayList<Company> findAllByTypes(String[] types) {
		return cs.findAllByTypes(types);
	}
}
