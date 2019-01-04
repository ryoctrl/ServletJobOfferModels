package servlet;

import java.util.ArrayList;

public class SQLCompanyStore extends AbstractCompanyStore{

	protected SQLCompanyStore() {
		
	}
	@Override
	public void insert(Company obj) {
		super.insert(obj);
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public ArrayList<Company> getAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected int getMaxId() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public Company findOneById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Company findOneByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Company> findAllByLocation(String location) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Company> findAllByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
