package utilities;

import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;

import model.Models;
import model.models.Company;
import model.models.Path;
import model.models.Storable;

/**
 * 
 * @author mosin
 */
public class Utilities {
	/**
	 * CompanyObjectのArrayListからjson文字列を生成する.
	 * @param companies
	 * @return
	 */
	public static String companiesToJson(ArrayList<Company> companies) {
		return modelsToJsonByModelArray("companies", companies);
	}
	
	public static String pathsToJson(ArrayList<Path> paths) {
		return modelsToJsonByModelArray("paths", paths);
	}
	
	private static String modelsToJsonByModelArray(String modelName, ArrayList<? extends Storable> modelArray) {
		if(modelArray == null) return "";
		ArrayList<HashMap<String, Object>> jsonList = new ArrayList<>();
		Set<String> keys = Models.getModel(modelName).getModelKeys();
		for(Storable c : modelArray) {
			HashMap<String, Object> map = new HashMap<>();
			keys.forEach(key -> {
				try {
					map.put(key, new PropertyDescriptor(key, c.getClass()).getReadMethod().invoke(c));
				}catch(Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			});
			jsonList.add(map);
		}
		JSONArray arr = new JSONArray(jsonList);
		return arr.toString();
	}
	
	public static String readJsonFromFileByModelName(String modelName) {
		return readJsonFromFile(modelName + ".json");
	}
	
	public static void writeJsonToFileByModelName(String modelName, String str) {
		writeJsonToFile(modelName + ".json", str);
	}
	
	public static String readJsonFromFile(String filename) {
		String jsonDir = Constants.Environments.JSON_DIR;
		if(jsonDir == null) {
			Logger.fatal("JSON_DIR ENV IS NOT SETTED!");
			System.exit(1);
		}
		
		File jsonFile = new File(jsonDir, filename);
		
		String str = "";
		if(jsonFile.exists()) {
			try {
				str = Files.lines(Paths.get(jsonFile.getAbsolutePath()), Charset.forName("UTF-8"))
				        .collect(Collectors.joining(System.getProperty("line.separator")));
			} catch(IOException e) {
				System.out.println("An error has ocucred");
				e.printStackTrace();
				str = "[]";
			}
			Logger.info(filename + " loaded");
		} else {
			
			try {
				jsonFile.createNewFile();
			} catch (IOException e) {
				Logger.fatal("Failed to create " + jsonFile.getPath());
				System.exit(1);
			}
			Logger.info(filename + " created.");
			str = "[]";
		}	
		if(str.equals("")) {
			str = "[]";
			writeJsonToFile(filename, str);
		}
		return str;
	}
	
	public static void writeJsonToFile(String filename, String str) {
		String jsonDir = Constants.Environments.JSON_DIR;
		if(jsonDir == null) {
			Logger.fatal("JSON_DIR ENV IS NOT SETTED");
			System.exit(1);
		}
		
		File jsonFile = new File(jsonDir, filename);
		
		if(jsonFile.exists()) {
			 try {
		            FileWriter file = new FileWriter(jsonFile);
		            PrintWriter pw = new PrintWriter(new BufferedWriter(file));	            
		            pw.println(str);
		            pw.close();
		            Logger.info(jsonFile.getName() + " updated.");
		        } catch (IOException e) {
		        	Logger.fatal(jsonFile.getName() + " " + e.getMessage());
		            System.exit(1);
		        }
		}
		
	}
}
