package test;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;

import company.CompanyManager;
import model.Models;
import model.models.Company;
import model.models.Path;
import utilities.SQLUtilities;
import utilities.Utilities;

public class Test {
	private CompanyManager cm = null;
	
	public Test() {
		cm = CompanyManager.getInstance();
	}
	public static void main(String[] args) {
		Test t = new Test();
		//t.writeTest();
		t.readTest();
		//t.modelDefineSystemTest();
		//t.propertyDescriptorTest();
		//t.sqlQueryBuilder();
		
	}
	
	public void propertyDescriptorTest() {
		try {
			Company c = new Company();
			PropertyDescriptor nameP = new PropertyDescriptor("name", c.getClass());
			nameP.getWriteMethod().invoke(c, "test");
			System.out.println(c.getName());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void sqlQueryBuilder() {
		String pathsInsert = "INSERT INTO paths (id, path, name, company_id) VALUES (?, ?, ?, ?)";
		String companiesInsert = "INSERT INTO companies (id, name, location, type, description) VALUES (?, ?, ?, ?, ?)";
		
		String companiesBuilder = SQLUtilities.insertAllValuesQuery(Models.getModel("companies"));
		String pathsBuilder = SQLUtilities.insertAllValuesQuery(Models.getModel("paths"));
		
		System.out.println(pathsInsert);
		System.out.println(pathsBuilder);
		System.out.println(pathsInsert.equals(pathsBuilder));
		
		System.out.println(companiesInsert);
		System.out.println(companiesBuilder);
		System.out.println(companiesBuilder.equals(companiesInsert));
	}
	
	public void modelDefineSystemTest() {
		String companiesQuery = Models.getModel("companies").getCreateTableQuery();
		System.out.println(companiesQuery);
		String str = "CREATE TABLE companies (" +
				"`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
				"name TEXT NOT NULL," + 
				"location TEXT NOT NULL," +
				"type TEXT NOT NULL," +
				"description TEXT" +
				");";
		System.out.println(str);
		System.out.println("companies is suucess : " + str.equals(companiesQuery));
		
		String pathsQuery = Models.getModel("paths").getCreateTableQuery();
		System.out.println(pathsQuery);
		
		str = 		"CREATE TABLE paths (" +
				"`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
				"path TEXT NOT NULL," + 
				"name TEXT NOT NULL," +
				"company_id INT NOT NULL" +
				");";
		
		System.out.println(str);
		System.out.println("paths is success : " + str.equals(pathsQuery));
		
	}
	
	public void writeTest() {
		Company tgl = cm.registerNewCompany("TGL", "大阪", "SIer", "大阪のシステムインテグレーター.");
		Company yahoo = cm.registerNewCompany("Yahoo", "東京", "Webベンチャー", "検索エンジンポータルや各種Webサービスを展開している");
		Company ca = cm.registerNewCompany("CyberAgent", "東京", "Webベンチャー", "スマホ向けコンテンツを主とした若年層向けサービスを多く展開");
		Company il = cm.registerNewCompany("InfiniteLoop", "北海道", "Webベンチャー", "VTuber事業を展開.みゅみゅが所属している");
		
		cm.registerNewPath("tgl/intern", "TGLのインターン案内", tgl.getId());
		cm.registerNewPath("tgl/college", "TGL-Collegeの案内", tgl.getId());
		cm.registerNewPath("Yahoo/Intern", "Yahooのインターン案内", yahoo.getId());
	}
	
	public void readTest() {
		String str = Utilities.companiesToJson(cm.getAllCompanies());
		System.out.println("全ての企業を表示します");
		System.out.println(str);

		str = Utilities.companiesToJson(cm.findAllByLocation("東京"));
		System.out.println("所在地が東京の企業を表示します");
		System.out.println(str);
		
		str = Utilities.companiesToJson(cm.findAllByLocations(new String[] {"大阪", "東京"}));
		System.out.println("所在地が大阪と東京の企業を表示します");
		System.out.println(str);
		
		str = Utilities.companiesToJson(cm.findAllByType("SIer"));
		System.out.println("業種がSIerの企業を表示します");
		System.out.println(str);
		
		str = Utilities.companiesToJson(cm.findAllByTypes(new String[] {"SIer", "Webベンチャー"}));
		System.out.println("業種がSIerとWebベンチャーの企業を表示します");
		System.out.println(str);
		
		ArrayList<Company> companies = cm.getAllCompanies();
		for(Company c : companies) System.out.println(c.getDescription());
	}
}
