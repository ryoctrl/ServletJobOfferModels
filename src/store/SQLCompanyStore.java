package store;

import company.Company;
import storesystem.AbstractStoreSystem;
import storesystem.SQLStore;

public class SQLCompanyStore extends AbstractCompanyStore{
	
	protected SQLCompanyStore() {
		super();
	}
	
	@Override
	protected void initializeModelName() {
		modelName = "companies";
	}
	
	@Override
	protected void initializeStoreSystem() {
		storeSystem = new SQLStore<Company>(this);
	}
	
	@Override
	public void insert(Company obj) {
		Storable insertedCompany = storeSystem.insert(obj);
		if(insertedCompany == null || !(insertedCompany instanceof Company)) {
			return;
		}
		records.add((Company)insertedCompany);
	}

//There is the problem of can't access to same object when register new path object
//	@Override
//	public Company findOneById(int id) {
//		if(id < 1) return null;
//		String sql = "SELECT * FROM companies WHERE id = " + id;
//		try {
//			Statement s = conn.createStatement();
//			ResultSet result = s.executeQuery(sql);
//			if(!result.next()) return null;
//			return new Company(result.getInt("id"), result.getString("name"), result.getString("location"), result.getString("type"), null);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
}
