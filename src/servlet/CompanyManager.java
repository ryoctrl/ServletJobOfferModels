package servlet;

import java.util.ArrayList;

public class CompanyManager {
	public final static String STORAGE_KEY = "STORAGE_TYPE";
	private AbstractCompanyStore cs;
	private AbstractPathStore ps;
	
	public static CompanyManager instance = null;
	public static CompanyManager getInstance() {
		if(instance == null) {
			instance = new CompanyManager();
		}
		return instance;
	}
	
	private CompanyManager() {
		initialize();
	}
	
	
	//WARNING: CompanyStore is depenpdencies to PathStore. So should instantiate PathStore before instantiate CompanyStore
	private void initialize() {
		ps = AbstractPathStore.getInstance();
		cs = AbstractCompanyStore.getInstance();
	}
	
	public Company registerNewCompany(String name, String location, String type) {
		Company newCompany = new Company(name, location, type);
		cs.insert(newCompany);
		return newCompany;
	}
	
	public Path registerNewPath(String path, int companyId) {
		Path newPath = new Path(path,companyId);
		ps.insert(newPath);
		cs.findOneById(companyId).addPath(newPath);
		return newPath;
	}
	
	public Company findOneByCompanyId(int companyId) {
		return cs.findOneById(companyId);
	}
	
	public ArrayList<Company> getAllCompanies() {
		return cs.getAll();
	}
}
