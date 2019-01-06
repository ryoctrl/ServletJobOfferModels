package store;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import company.Path;
import company.SQLUtilities;
import model.ModelOption;
import model.Models;

public class SQLPathStore extends AbstractPathStore{
	private Connection conn = null;
	
	@Override
	protected void modelNameInitialize() {
		modelName = "paths";
	}
	
	protected SQLPathStore() {
		super();
		
		try {
			conn = SQLPool.getInstance().getConnection();
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getTables(null, "servlet", modelName, new String[] {"TABLE"});
			if(!rs.next()) {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(model.getCreateTableQuery());
				stmt.close();
				System.out.println("paths table created.");
			}
			System.out.println("paths table checked");
			
			Statement stmt = conn.createStatement();
			ResultSet pathsRs = stmt.executeQuery(SQLUtilities.selectAllQuery(modelName));
			Set<String> keys = Models.getModel(modelName).getModelKeys();
			while(pathsRs.next()) {
				Path p = new Path();
				keys.forEach(key -> {
					try {
						new PropertyDescriptor(key, p.getClass()).getWriteMethod().invoke(p, pathsRs.getObject(key));
					}catch(Exception e) {
						e.printStackTrace();
						System.exit(1);
					}
				});
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
