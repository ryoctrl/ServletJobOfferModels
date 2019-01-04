package test;

import java.util.ArrayList;

import company.Company;
import company.CompanyManager;
import company.Path;

public class Test {
	public static void main(String[] args) {
		CompanyManager cm = CompanyManager.getInstance();
		ArrayList<Company> companies = cm.getAllCompanies();
		for(Company c : companies) {
			System.out.println("Company : " + c.getId() +"," + c.getName() + "," + c.getLocation() + "," + c.getType());
			ArrayList<Path> paths = c.getPaths();
			for(Path p : paths) {
				System.out.println("    Path : " + p.getId() + "," + p.getCompanyId() + "," + p.getPath());
			}
		}
		
		Company yahoo = cm.registerNewCompany("Yahoo", "TOKYO", "WebVenture");
		cm.registerNewPath("/path/to/YahooIntern", yahoo.getId());
		for(Company c : companies) {
			System.out.println("Company : " + c.getId() +"," + c.getName() + "," + c.getLocation() + "," + c.getType());
			ArrayList<Path> paths = c.getPaths();
			for(Path p : paths) {
				System.out.println("    Path : " + p.getId() + "," + p.getCompanyId() + "," + p.getPath());
			}
		}
	}
}
