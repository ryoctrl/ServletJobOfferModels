package model;

import java.util.LinkedHashMap;
import java.util.Set;

public interface IModelDefine {
	public String getModelName();
	public LinkedHashMap<String, ModelOption> getModelDefine();
	public Set<String> getModelKeys();
	public String getCreateTableQuery();
	public int getNumberOfColumns();
}
