package model;

import model.models.Storable;
import utilities.Constants.ModelType;

public class ModelOption {
	private ModelType type;
	private boolean nullable;
	private boolean isId;
	private int columnIndex;
	private Class<? extends Storable> externalModel;
	
	public ModelOption(ModelType type, boolean nullable, int columnIndex) {
		this.type = type;
		this.nullable = nullable;
		this.isId = false;
		this.columnIndex = columnIndex;
	}
	
	public ModelOption(boolean isId) {
		if(!isId) {
			this.type = ModelType.STRING;
			this.nullable = true;
			this.isId = false;
			this.columnIndex = 0;
			return;
		}
		this.type = ModelType.INTEGER;
		this.nullable = false;
		this.isId = true;
		this.columnIndex = 0;
	}
	
	public ModelOption(Class<? extends Storable> externalModel) {
		this.type = ModelType.FOREIGN;
		this.externalModel = externalModel;
	}
	
	public ModelType getType() {
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
