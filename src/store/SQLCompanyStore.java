package store;

import java.util.ArrayList;
import java.sql.*;

import company.Company;
import company.Path;

public class SQLCompanyStore extends AbstractCompanyStore{
	private Connection conn = null;

	protected SQLCompanyStore() {
		super();
		
		try {
			conn = SQLPool.getInstance().getConnection();
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getTables(null, "servlet", "companies", new String[] {"TABLE"});
			if(!rs.next()) {
				Statement stmt = conn.createStatement();
				String sqlStr = 
						"CREATE TABLE companies (" +
						"`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
						"name TEXT NOT NULL, " + 
						"location TEXT NOT NULL," +
						"type TEXT NOT NULL" +
						");";
				stmt.executeUpdate(sqlStr);
				stmt.close();
				System.out.println("companies table created.");
			}
			System.out.println("companies table checked.");
			String selectSqlStr = "SELECT * FROM companies";
			Statement stmt = conn.createStatement();
			ResultSet companiesRs = stmt.executeQuery(selectSqlStr);
			AbstractPathStore ps = AbstractPathStore.getInstance();
			while(companiesRs.next()) {
				int id = companiesRs.getInt("id");
				String name = companiesRs.getString("name");
				String location = companiesRs.getString("location");
				String type = companiesRs.getString("type");
				ArrayList<Path> paths = ps.findAllByCompanyId(id);
				Company c = new Company(id, name, location, type, paths);
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
			PreparedStatement ps = conn.prepareStatement("INSERT INTO companies (name, location, type) VALUES (?, ?, ?)");
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getLocation());
			ps.setString(3, obj.getType());
			int count = ps.executeUpdate();
			ps.close();
			if(count < 1) {
				ps.close();
				return;
			}
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM companies ORDER BY id DESC");
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
