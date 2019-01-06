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

import company.Company;
import company.Path;
import company.SQLUtilities;
import model.ModelOption;
import model.Models;
import storesystem.AbstractStoreSystem;
import storesystem.SQLStore;

public class SQLPathStore extends AbstractPathStore{
	private AbstractStoreSystem storeSystem;
	private Connection conn = null;
	
	@Override
	protected void modelNameInitialize() {
		modelName = "paths";
	}
	
	protected SQLPathStore() {
		super();
		storeSystem = new SQLStore<Path>(this);
		records = storeSystem.initialLoad(Path.class);
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

	@Override
	public void includeExternalRecordIfNeeded(Path obj) {}
}
