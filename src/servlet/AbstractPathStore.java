package servlet;

public abstract class AbstractPathStore extends AbstractStore<Path> {
	@Override
	public void insert(Path obj) {
		obj.setId(getMaxId() + 1);
	}
}
