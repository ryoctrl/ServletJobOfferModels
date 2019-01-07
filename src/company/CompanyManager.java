package company;

import java.util.ArrayList;

import model.models.Company;
import model.models.Path;
import model.modelstores.CompanyModelStore;
import model.modelstores.PathModelStore;
import model.modelstores.StoreProvider;

/**
 * ServletからCompanyをあれこれするためのシングルトンなインタフェース
 * @author Ryo Fujii
 *
 */
public class CompanyManager {
	private CompanyModelStore companies;
	private PathModelStore paths;
	
	private static CompanyManager instance = null;
	public static CompanyManager getInstance() {
		if(instance == null) {
			instance = new CompanyManager();
		}
		return instance;
	}
	
	private CompanyManager() {
		initialize();
	}
	
	private void initialize() {
		companies = (CompanyModelStore) StoreProvider.getModelStore("companies");
		paths = (PathModelStore) StoreProvider.getModelStore("paths");
	}
	
	/**
	 * 新たな企業情報を登録する
	 * @param name 企業名
	 * @param location 所在地
	 * @param type 業種
	 * @param description 詳細/備考
	 * @return 登録後の企業のオブジェクト
	 */
	public Company registerNewCompany(String name, String location, String type, String description) {
		Company newCompany = new Company(name, location, type, description);
		companies.insert(newCompany);
		return newCompany;
	}
	
	/**
	 * 新たな企業用のファイルリソースを登録する
	 * @param path ファイルパス
	 * @param name ファイル名
	 * @param companyId 紐づける企業ID
	 * @return 登録後のパスオブジェクト
	 */
	public Path registerNewPath(String path, String name, int companyId) {
		Path newPath = new Path(path,name, companyId);
		paths.insert(newPath);
		companies.findOneById(companyId).addPath(newPath);
		return newPath;
	}
	
	/**
	 * 企業IDを用いて企業を取得する
	 * @param companyId 企業ID
	 * @return 該当する企業IDの企業.無ければnull
	 */
	public Company findOneByCompanyId(int companyId) {
		return companies.findOneById(companyId);
	}
	
	/**
	 * 所在地文字列を用いて企業を検索する.
	 * DBやjsonに登録されている所在地の文字列と完全一致で検索する
	 * @param location 所在地文字列
	 * @return 該当する企業のリスト.無ければ要素数0のArrayList
	 */
	public ArrayList<Company> findAllByLocation(String location) {
		return companies.findAllByLocation(location);
	}
	
	/**
	 * 業種文字列を用いて企業を検索する.
	 * DBやjsonに登録されている業種の文字列と完全一致で検索する
	 * @param type 業種文字列
	 * @return 該当する企業のリスト.無ければ要素数0のArrayList
	 */
	public ArrayList<Company> findAllByType(String type) {
		return companies.findAllByType(type);
	}
	
	/**
	 * DBやjsonに登録されているすべての企業を取得する
	 * @return 全ての企業リスト.無ければ要素数0のArrayList
	 */
	public ArrayList<Company> getAllCompanies() {
		return companies.getAll();
	}
	
	/**
	 * 複数の所在地文字列を用いて企業を検索する
	 * DBやJsonに登録されている所在地の文字列と完全一致で検索する
	 * @param locations 所在地文字列配列
	 * @return 該当する企業のリスト.無ければ要素数0のArrayList
	 */
	public ArrayList<Company> findAllByLocations(String[] locations) {
		return companies.findAllByLocations(locations);
	}

	/**
	 * 複数の業種文字列を用いて企業を検索する
	 * DBやJsonに登録されている業種の文字列と完全一致で検索する
	 * @param types 業種文字列配列
	 * @return 該当する企業のリスト.無ければ要素数0のArrayList
	 */
	public ArrayList<Company> findAllByTypes(String[] types) {
		return companies.findAllByTypes(types);
	}
}
