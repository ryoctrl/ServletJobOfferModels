package store;

import java.util.ArrayList;

import company.Path;

public class SQLPathStore extends AbstractPathStore{

	protected SQLPathStore() {
		
	}
	@Override
	public void insert(Path obj) {
		super.insert(obj);
		// TODO: SAVE TO SQL DB
	}

	@Override
	public ArrayList<Path> getAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected int getMaxId() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}


	@Override
	public Path findOneById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Path> findAllByCompanyId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
