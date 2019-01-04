package servlet;

import java.util.ArrayList;

public class CompanyManager {
	public final static String STORAGE_KEY = "STORAGE_TYPE";
	private ArrayList<Company> companies;
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
	
	private void initialize() {
		String storageEnv = System.getenv(STORAGE_KEY);
		if(storageEnv == null || (!storageEnv.equals("json") && !storageEnv.equals("sql"))) storageEnv = "json";
		
		if(storageEnv.equals("json")) {
			cs = new JsonCompanyStore();
			ps = new JsonPathStore();
		} else {
			cs = new SQLCompanyStore();
			ps = new SQLPathStore();
		}
		
		companies = cs.findAll();
	}
	
	public void registerNewCompany(String name, String location, String type, ArrayList<Path> paths) {
		cs.insert(new Company(name, location, type, paths));
	}
	
	public void registerNewPath(String path, int companyId) {
		Path newPath = new Path(path,companyId);
		ps.insert(newPath);
		cs.findOne(companyId).addPath(newPath);;
	}
}
