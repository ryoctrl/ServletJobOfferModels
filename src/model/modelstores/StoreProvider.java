package model.modelstores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

import model.Models;
import model.models.Storable;
import utilities.Constants;
import utilities.Logger;

public class StoreProvider {
	private static HashMap<String, AbstractModelStore<? extends Storable>> stores;
	private static boolean initialized = false;
	
	public static AbstractModelStore getModelStore(String key) {
		if(!initialized) storeInitialize(key);
		return stores.get(key);
	}
	
	private static void storeInitialize(String key) {
		if(initialized) return;
		initialized = true;
		stores = new HashMap<String, AbstractModelStore<? extends Storable>>();
		Reflections ref = new Reflections(Constants.Keys.MODEL_STORE_PACKAGE);
		Set<Class<? extends AbstractModelStore>> modelStoreClasses = ref.getSubTypesOf(AbstractModelStore.class);
		ArrayList<String> registered = new ArrayList<>();
		ArrayList<AbstractModelStore<? extends Storable>> waitingInitialize = new ArrayList<>();
		modelStoreClasses.forEach(modelStore -> {
			try {
				AbstractModelStore<? extends Storable> ms = modelStore.newInstance();
				for(String depKey : Models.getModel(ms.getModelName()).getDependencies()) {
					if(!registered.contains(depKey)) {
						waitingInitialize.add(ms);
						return;
					};
				}
				ms.recordsInitialize();
				registered.add(ms.getModelName());
				stores.put(ms.getModelName(), ms);
			}catch(Exception e) {
				Logger.fatal(e.getMessage());
				System.exit(1);
			}
		});
		for(AbstractModelStore<? extends Storable> ms : waitingInitialize) {
			ms.recordsInitialize();
			stores.put(ms.getModelName(), ms);
		}
	}
}
