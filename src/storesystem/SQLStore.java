package storesystem;

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
import company.SQLUtilities;
import model.ModelOption;
import model.Models;
import store.AbstractPathStore;
import store.AbstractStore;
import store.SQLPool;
import store.Storable;
import utilities.Logger;

public class SQLStore<T extends Storable> extends AbstractStoreSystem<T>{
	private Connection conn = null;
	
	public SQLStore(AbstractStore<T> store) {
		super(store);
	}
	
	public ArrayList<T> initialLoad(Class<T> modelClass) {
		ArrayList<T> records = new ArrayList<>();
		try {
			conn = SQLPool.getInstance().getConnection();
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getTables(null, "servlet", store.getModelName(), new String[] {"TABLE"});
			if(!rs.next()) {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(store.getModelDefine().getCreateTableQuery());
				stmt.close();
				Logger.info(store.getModelName() + " Table created.");
			}
			Logger.info(store.getModelName() + " Table checked.");
			
			Statement stmt = conn.createStatement();
			ResultSet companiesRs = stmt.executeQuery(SQLUtilities.selectAllQuery(store.getModelName()));
			
			Set<String> keys = Models.getModel(store.getModelName()).getModelKeys();
			while(companiesRs.next()) {
				T obj = modelClass.newInstance();
				keys.forEach(key -> {
					try {
						new PropertyDescriptor(key, obj.getClass()).getWriteMethod().invoke(obj, companiesRs.getObject(key));
					}catch(Exception e) {
						e.printStackTrace();
						System.exit(1);
					}
				});
				store.includeExternalRecordIfNeeded(obj);
				records.add(obj);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return records;
	}

	@Override
	public T insert(T obj) {
		if(conn == null) return null;
		
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
				return null;
			}
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(SQLUtilities.selectAllQueryDesc(modelName));
			if(!rs.next()) return null;
			obj.setId(rs.getInt("id"));
			s.close();
			return obj;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
