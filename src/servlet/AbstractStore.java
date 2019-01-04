package servlet;

public abstract class AbstractStore<T> implements IStore<T> {
	protected abstract int getMaxId();
}
