package model.define;

import model.ModelOption;
import model.models.Path;

public class CompanyModelDefine extends AbstractModelDefine {
	
	@Override
	protected void defineModelName() {
		this.modelName = "companies";
	}

	@Override
	protected void defineColumns() {
		columns.put("id", new ModelOption(true));
		columns.put("name", new ModelOption("String", false, 1));
		columns.put("location", new ModelOption("String", false, 2));
		columns.put("type", new ModelOption("String", false, 3));
		columns.put("description", new ModelOption("String", true, 4));
		columns.put("paths", new ModelOption(Path.class));
		//columns.put("employees", new ModelOption("int", true, 5));
	}
}
