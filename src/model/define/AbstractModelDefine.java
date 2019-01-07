package model.define;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import model.IModelDefine;
import model.ModelOption;
import utilities.Constants.ModelType;

public abstract class AbstractModelDefine implements IModelDefine {
	protected LinkedHashMap<String, ModelOption> columns = null;
	protected String modelName;
	protected ArrayList<String> dependencies;
	protected AbstractModelDefine() {
		columns = new LinkedHashMap<>();
		defineModelName();
		defineColumns();
		dependencies = new ArrayList<String>();
		registerDependencies(dependencies);
	}
	
	public ArrayList<String> getDependencies() {
		return dependencies;
	}
	
	
	public String getModelName() {
		return modelName;
	}
	
	@Override
	public LinkedHashMap<String, ModelOption> getModelDefine() {
		return columns;
	}
	
	@Override
	public Set<String> getModelKeys() {
		return columns.keySet();
	}
	
	@Override
	public int getNumberOfColumns() {
		int count = 0;
		for(ModelOption option : columns.values()) {
			if(option.getType() == ModelType.FOREIGN) continue;
			count++;
		}
		return count;
	}

	@Override
	public String getCreateTableQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + modelName + " (");
		columns.forEach((key, value) -> {
			if(value.getType() == ModelType.FOREIGN) return;
			String columnDefineStr = key.equals("id") ? "`id`" : key;
			String sqlType = value.getType().getSqlType();
			columnDefineStr += " " + sqlType;
			if(value.isId()) {
				columnDefineStr += " NOT NULL AUTO_INCREMENT PRIMARY KEY,";
			} else if(!value.isNullable()) {
				columnDefineStr += " NOT NULL,";
			} else {
				columnDefineStr  += ",";
			}
			sb.append(columnDefineStr);
		});
		//remove last ","
		sb.setLength(sb.length() - 1);
		sb.append(");");
		return sb.toString();
	}
	
	protected abstract void defineColumns();
	protected abstract void defineModelName();
	protected abstract void registerDependencies(ArrayList<String> dependencies);
}
