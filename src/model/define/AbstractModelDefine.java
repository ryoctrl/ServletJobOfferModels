package model.define;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;

import model.IModelDefine;
import model.ModelOption;

public abstract class AbstractModelDefine implements IModelDefine {
	protected LinkedHashMap<String, ModelOption> columns = null;
	protected String modelName;
	protected AbstractModelDefine() {
		columns = new LinkedHashMap<>();
		defineModelName();
		defineColumns();
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
			if(option.getType().equals("External")) continue;
			count++;
		}
		return count;
	}

	@Override
	public String getCreateTableQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + modelName + " (");
		columns.forEach((key, value) -> {
			if(value.getType().equals("External")) return;
			String columnDefineStr = key.equals("id") ? "`id`" : key;
			String sqlType = "";
			switch(value.getType()) {
			case "String":
				sqlType = "TEXT";
				break;
			case "int":
				sqlType = "INT";
				break;
			}
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
}
