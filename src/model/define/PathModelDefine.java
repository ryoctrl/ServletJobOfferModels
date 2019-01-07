package model.define;

import java.util.ArrayList;

import model.ModelOption;
import utilities.Constants.ModelType;

public class PathModelDefine extends AbstractModelDefine {
	
	@Override
	protected void defineModelName() {
		this.modelName = "paths";
	}

	@Override
	protected void defineColumns() {
		columns.put("id", new ModelOption(true));
		columns.put("path", new ModelOption(ModelType.STRING, false, 1));
		columns.put("name", new ModelOption(ModelType.STRING, false, 2));
		columns.put("company_id", new ModelOption(ModelType.INTEGER, false, 3));
	}

	@Override
	protected void registerDependencies(ArrayList<String> dependencies) {}
}
