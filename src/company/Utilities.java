package company;

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
import java.util.stream.Collectors;

import org.json.JSONArray;

public class Utilities {
	public static String companiesToJson(ArrayList<Company> companies) {
		if(companies == null) return null;
		ArrayList<HashMap> jsonList = new ArrayList<>();
		for(Company c : companies) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("id", c.getId());
			map.put("name", c.getName());
			map.put("location", c.getLocation());
			map.put("type", c.getType());
			jsonList.add(map);
		}
		JSONArray arr = new JSONArray(jsonList);
		return arr.toString();
	}
	
	public static String pathsToJson(ArrayList<Path> paths) {
		if(paths == null) return null;
		ArrayList<HashMap> jsonList = new ArrayList<>();
		for(Path p : paths) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("id", p.getId());
			map.put("path", p.getPath());
			map.put("name", p.getName());
			map.put("companyId", p.getCompanyId());
			jsonList.add(map);
		}
		JSONArray arr = new JSONArray(jsonList);
		return arr.toString();
	}
	
	public static String readJsonFromFile(String filename) {
		String jsonDir = System.getenv("JSON_DIR");
		if(jsonDir == null) {
			System.err.println("JSON_DIR ENV IS NOT SETTED!");
			System.exit(1);
		}
		
		File jsonFile = new File(jsonDir, filename);
		
		System.out.println("reading : " + jsonFile.getAbsolutePath());
		
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
		} else {
			System.out.println("File not found!");
			str = "[]";
		}	
		if(str.equals("")) {
			str = "[]";
			writeJsonToFile(filename, str);
		}
		return str;
	}
	
	public static void writeJsonToFile(String filename, String str) {
		String jsonDir = System.getenv("JSON_DIR");
		if(jsonDir == null) {
			System.err.println("JSON_DIR ENV IS NOT SETTED!");
			System.exit(1);
		}
		
		File jsonFile = new File(jsonDir, filename);
		
		if(jsonFile.exists()) {
			 try {
		            // FileWriterクラスのオブジェクトを生成する
		            FileWriter file = new FileWriter(jsonFile);
		            // PrintWriterクラスのオブジェクトを生成する
		            PrintWriter pw = new PrintWriter(new BufferedWriter(file));
		            
		            //ファイルに書き込む
		            pw.println(str);
		            
		            //ファイルを閉じる
		            pw.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		}
		
	}
}
