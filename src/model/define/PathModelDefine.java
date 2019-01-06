package model.define;

import model.ModelOption;

public class PathModelDefine extends AbstractModelDefine {
	
	@Override
	protected void defineModelName() {
		this.modelName = "paths";
	}

	@Override
	protected void defineColumns() {
		columns.put("id", new ModelOption(true));
		columns.put("path", new ModelOption("String", false, 1));
		columns.put("name", new ModelOption("String", false, 2));
		columns.put("company_id", new ModelOption("int", false, 3));
	}
}
