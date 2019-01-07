package store.system;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import model.ModelOption;
import model.Models;
import model.models.Storable;
import store.AbstractStore;
import utilities.Constants.ModelType;
import utilities.Logger;
import utilities.Utilities;

public class JsonStoreSystem<T extends Storable> extends AbstractStoreSystem<T> {

	public JsonStoreSystem(AbstractStore<T> store) {
		super(store);
	}

	@Override
	public T insert(T obj) {
		return null;
	}
	
	public void saveToJson(ArrayList<T> records) {
		ArrayList<HashMap<String, Object>> jsonList = new ArrayList<>();
		LinkedHashMap<String, ModelOption> columns = Models.getModel(modelName).getModelDefine();
		for(T c : records) {
			HashMap<String, Object> map = new HashMap<>();
			columns.forEach((key, option) -> {
				if(option.getType() == ModelType.FOREIGN) return;
				try {
					map.put(key, new PropertyDescriptor(key, c.getClass()).getReadMethod().invoke(c));
				}catch(Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			});
			jsonList.add(map);
		}
		JSONArray arr = new JSONArray(jsonList);
		Utilities.writeJsonToFileByModelName(modelName, arr.toString());
	}
	
	

	@Override
	public ArrayList<T> initialLoad(Class<T> modelClass) {
		ArrayList<T> records = new ArrayList<>();
		try {
			JSONArray jsonArray = new JSONArray(Utilities.readJsonFromFileByModelName(modelName));
			LinkedHashMap<String, ModelOption> columns = Models.getModel(modelName).getModelDefine();
			for(Object o : jsonArray) {
				if( o instanceof JSONObject) {
					T modelObj = modelClass.newInstance();
					JSONObject obj = (JSONObject) o;
					columns.forEach((key, option) -> {
						if(option.getType() == ModelType.FOREIGN) return;
						try {
							new PropertyDescriptor(key, modelObj.getClass()).getWriteMethod().invoke(modelObj, obj.get(key));
						}catch(Exception e) {
							e.printStackTrace();
							Logger.fatal("Failed initial load " + modelName + "model from json");
							System.exit(1);
						}
					});
					store.includeForeignRecordIfNeeded(modelObj);
					records.add(modelObj);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			Logger.fatal("Failed initial load " + modelName + "model when object insitantiate");
			System.exit(1);
		}
		return records;
	}
	
}
