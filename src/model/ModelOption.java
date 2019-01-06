package model;

import store.Storable;

public class ModelOption {
	private String type;
	private boolean nullable;
	private boolean isId;
	private int columnIndex;
	private Class<? extends Storable> externalModel;
	
	public ModelOption(String type, boolean nullable, int columnIndex) {
		this.type = type;
		this.nullable = nullable;
		this.isId = false;
		this.columnIndex = columnIndex;
	}
	
	public ModelOption(boolean isId) {
		if(!isId) {
			this.type = "String";
			this.nullable = true;
			this.isId = false;
			this.columnIndex = 0;
			return;
		}
		this.type = "int";
		this.nullable = false;
		this.isId = true;
		this.columnIndex = 0;
	}
	
	public ModelOption(Class<? extends Storable> externalModel) {
		this.type = "External";
		this.externalModel = externalModel;
	}
	
	public String getType() {
		return this.type;
	}
	
	public boolean isNullable() {
		return this.nullable;
	}
	
	public boolean isId() {
		return this.isId;
	}
	
	public int getColumnIndex() {
		return columnIndex;
	}
	
	public Class<? extends Storable> getExternalModel() {
		return externalModel;
	}
}
