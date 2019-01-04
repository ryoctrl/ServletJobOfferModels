package servlet;

public abstract class AbstractCompanyStore extends AbstractStore<Company> {
	@Override
	public void insert(Company obj) {
		obj.setId(getMaxId() + 1);
	}
}
