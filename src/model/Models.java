package model;

import java.util.ArrayList;
import java.util.Set;

import org.reflections.*;

import model.define.AbstractModelDefine;

public class Models {
	private static boolean initialized = false;
	public static ArrayList<IModelDefine> models = new ArrayList<>();
	
	public static void initializeIfNeeded() {
		if(initialized) return;
		Reflections ref = new Reflections("modeldefine");
		Set<Class<? extends AbstractModelDefine>> modelClasses = ref.getSubTypesOf(AbstractModelDefine.class);
		modelClasses.forEach(model -> {
			try {
				IModelDefine m = model.newInstance();
				models.add(m);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		});
		initialized = true;
	}
	
	public static IModelDefine getModel(String modelName) {
		initializeIfNeeded();
		for(IModelDefine model : models) {
			if(model.getModelName().equals(modelName)) return model;
		}
		return null;
	}
	
}
