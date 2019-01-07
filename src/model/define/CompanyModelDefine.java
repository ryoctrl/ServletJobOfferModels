package model.define;

import java.util.ArrayList;

import model.IModelDefine;
import model.ModelOption;
import model.models.Path;
import utilities.Constants.ModelType;

public class CompanyModelDefine extends AbstractModelDefine {
	
	@Override
	protected void defineModelName() {
		this.modelName = "companies";
	}

	@Override
	protected void defineColumns() {
		columns.put("id", new ModelOption(true));
		columns.put("name", new ModelOption(ModelType.STRING, false, 1));
		columns.put("location", new ModelOption(ModelType.STRING, false, 2));
		columns.put("type", new ModelOption(ModelType.STRING, false, 3));
		columns.put("description", new ModelOption(ModelType.STRING, true, 4));
		columns.put("paths", new ModelOption(Path.class));
		//columns.put("employees", new ModelOption("int", true, 5));
	}

	@Override
	protected void registerDependencies(ArrayList<String> dependencies) {
		dependencies.add("paths");
	}
}
