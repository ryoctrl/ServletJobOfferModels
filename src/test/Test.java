package test;

import java.util.ArrayList;

import company.Company;
import company.CompanyManager;
import company.Path;
import company.Utilities;

public class Test {
	private CompanyManager cm = null;
	
	public Test() {
		cm = CompanyManager.getInstance();
	}
	public static void main(String[] args) {
		Test t = new Test();
		//t.writeTest();
		t.readTest();
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
	}
}
