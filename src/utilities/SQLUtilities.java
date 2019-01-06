package utilities;

import java.util.LinkedHashMap;

import model.IModelDefine;
import model.ModelOption;

public class SQLUtilities {
	public static String selectAllQuery(String from) {
		return "SELECT * FROM " + from;
	}
	
	public static String selectAllQueryDesc(String from) {
		return selectAllQuery(from) + " ORDER BY id DESC";
	}
	
	public static String insertAllValuesQuery(IModelDefine def) {
		String query = "INSERT INTO ";
		query += def.getModelName();
		query += " (";
		LinkedHashMap<String, ModelOption> columns = def.getModelDefine();
		StringBuilder sb = new StringBuilder(query);
		columns.forEach((key, option) -> {
			if(option.getType().equals("External")) return;
			sb.append(key + ", ");
		});
		sb.setLength(sb.length() - 2);
		sb.append(") VALUES (");
		for(int i = 0; i < def.getNumberOfColumns(); i++) {
			sb.append("?");
			if(i != def.getNumberOfColumns() - 1) {
				sb.append(", ");
			}
		}
		sb.append(")");
		return sb.toString();
	}

}
