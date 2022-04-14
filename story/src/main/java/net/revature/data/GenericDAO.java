package net.revature.data;

import java.sql.SQLException;
import java.util.List;

import net.revature.mymodle.Users;

public interface GenericDAO<T>{
	public int create(T newObj); // returns the generated ID
	public T getById(int id); // read one
	public List<T> getAll(); // read all
	public void update(T updatedObj)throws SQLException;
	public void delete(T objToDelete)throws SQLException;
	void deleteById(int id);
	



	

}
