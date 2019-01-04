package servlet;

import java.util.ArrayList;

public interface IStore<T> {
	public void insert(T obj);
	public ArrayList<T> getAll();
}
