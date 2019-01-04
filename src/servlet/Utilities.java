package servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Utilities {
	public static String readJsonFromFile(String filename) {
		String jsonDir = System.getenv("JSON_DIR");
		if(jsonDir == null) {
			System.err.println("JSON_DIR ENV IS NOT SETTED!");
			System.exit(1);
		}
		
		File jsonFile = new File(jsonDir, filename);
		
		String str = "";
		if(jsonFile.exists()) {
			try {
				str = Files.lines(Paths.get(jsonFile.getAbsolutePath()), Charset.forName("UTF-8"))
				        .collect(Collectors.joining(System.getProperty("line.separator")));
			} catch(IOException e) {
				str = "[]";
			}
		} else {
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
