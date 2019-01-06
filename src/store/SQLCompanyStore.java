package store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;
import java.beans.PropertyDescriptor;
import java.sql.*;

import company.Company;
import company.Path;
import company.SQLUtilities;
import model.ModelOption;
import model.Models;

public class SQLCompanyStore extends AbstractCompanyStore{
	private Connection conn = null;

	@Override
	protected void modelNameInitialize() {
		modelName = "companies";
	}
	
	protected SQLCompanyStore() {
		super();
		
		try {
			conn = SQLPool.getInstance().getConnection();
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getTables(null, "servlet", "companies", new String[] {"TABLE"});
			if(!rs.next()) {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(model.getCreateTableQuery());
				stmt.close();
				System.out.println("companies table created.");
			}
			System.out.println("companies table checked.");
			
			Statement stmt = conn.createStatement();
			ResultSet companiesRs = stmt.executeQuery(SQLUtilities.selectAllQuery(modelName));
			
			AbstractPathStore ps = AbstractPathStore.getInstance();
			Set<String> keys = Models.getModel(modelName).getModelKeys();
			while(companiesRs.next()) {
				Company c = new Company();
				keys.forEach(key -> {
					try {
						new PropertyDescriptor(key, c.getClass()).getWriteMethod().invoke(c, companiesRs.getObject(key));
					}catch(Exception e) {
						e.printStackTrace();
						System.exit(1);
					}
				});
				c.setPaths(ps.findAllByCompanyId(c.getId()));
				records.add(c);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	@Override
	public void insert(Company obj) {
		if(conn == null) return;
		
		try {
			PreparedStatement ps = conn.prepareStatement(SQLUtilities.insertAllValuesQuery(model));
			LinkedHashMap<String, ModelOption> modelDef = Models.getModel(modelName).getModelDefine();
			modelDef.forEach((key, option) -> {
				try {
					ps.setObject(option.getColumnIndex() + 1, new PropertyDescriptor(key, obj.getClass()).getReadMethod().invoke(obj));
				}catch(Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			});
			int count = ps.executeUpdate();
			ps.close();
			if(count < 1) {
				ps.close();
				return;
			}
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(SQLUtilities.selectAllQueryDesc(modelName));
			if(!rs.next()) return;
			obj.setId(rs.getInt("id"));
			records.add(obj);
			s.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
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
