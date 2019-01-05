package test;

import java.util.ArrayList;

import company.Company;
import company.CompanyManager;
import company.Path;
import company.Utilities;

public class Test {
	public static void main(String[] args) {
		CompanyManager cm = CompanyManager.getInstance();
		
		String str = Utilities.companiesToJson(cm.getAllCompanies());
		System.out.println(str);

		str = Utilities.companiesToJson(cm.findAllByLocation("東京"));
		System.out.println(str);
		
		str = Utilities.companiesToJson(cm.findAllByLocations(new String[] {"大阪", "東京"}));
		System.out.println(str);
		
		str = Utilities.companiesToJson(cm.findAllByType("SIer"));
		System.out.println(str);
		
		str = Utilities.companiesToJson(cm.findAllByTypes(new String[] {"SIer", "Webベンチャー"}));
		System.out.println(str);
		
//		ArrayList<Company> companies = cm.getAllCompanies();
//		for(Company c : companies) {
//			System.out.println("Company : " + c.getId() +"," + c.getName() + "," + c.getLocation() + "," + c.getType());
//			ArrayList<Path> paths = c.getPaths();
//			for(Path p : paths) {
//				System.out.println("    Path : " + p.getId() + "," + p.getCompanyId() + "," + p.getPath());
//			}
//		}
//		
//		Company yahoo = cm.registerNewCompany("Yahoo", "TOKYO", "WebVenture");
//		System.out.println(yahoo.getId());
//		cm.registerNewPath("/path/to/YahooIntern", "Yahooのインターン", yahoo.getId());
//		for(Company c : companies) {
//			System.out.println("Company : " + c.getId() +"," + c.getName() + "," + c.getLocation() + "," + c.getType());
//			ArrayList<Path> paths = c.getPaths();
//			for(Path p : paths) {
//				System.out.println("    Path : " + p.getId() + "," + p.getCompanyId() + "," + p.getPath());
//			}
//		}
//		
//		
//		Company tgl = cm.findOneByCompanyId(1);
//		System.out.println(tgl.getPaths().size());
//		
//		cm.registerNewPath("Test","Testファイル", 1);
//		System.out.println(tgl.getPaths().size());
	}
}
