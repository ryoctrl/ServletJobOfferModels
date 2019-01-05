package store;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import company.Path;

public class SQLPathStore extends AbstractPathStore{
	private Connection conn = null;
	
	protected SQLPathStore() {
		super();
		try {
			conn = SQLPool.getInstance().getConnection();
			
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getTables(null, "servlet", "paths", new String[] {"TABLE"});
			if(!rs.next()) {
				String sqlStr = 
						"CREATE TABLE paths (" +
						"`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
						"path TEXT NOT NULL, " + 
						"name TEXT NOT NULL," +
						"company_id INT NOT NULL" +
						");";
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sqlStr);
				stmt.close();
				System.out.println("paths table created.");
			}
			System.out.println("paths table checked");
			
			String selectSqlStr = "SELECT * FROM paths";
			Statement stmt = conn.createStatement();
			ResultSet companiesRs = stmt.executeQuery(selectSqlStr);
			while(companiesRs.next()) {
				int id = companiesRs.getInt("id");
				String path = companiesRs.getString("path");
				String name = companiesRs.getString("name");
				int companyId = companiesRs.getInt("company_id");
				Path p = new Path(id, path, name, companyId);
				records.add(p);
			}
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	@Override
	public void insert(Path obj) {
		if(conn == null) return;
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO paths (path, name, company_id) VALUES (?, ?, ?)");
			ps.setString(1, obj.getPath());
			ps.setString(2, obj.getName());
			ps.setInt(3, obj.getCompanyId());
			int count = ps.executeUpdate();
			obj.setId(count);
			ps.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
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
