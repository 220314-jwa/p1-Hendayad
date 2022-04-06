package net.revature.data;

import java.util.List;

import net.revature.mymodle.Users;

public interface GenericDAO<T>{
	public int create(T newObj); // returns the generated ID
	public T getById(int id); // read one
	public List<T> getAll(); // read all
	public void update(T updatedObj);
	public void delete(T objToDelete);
	

	

}
