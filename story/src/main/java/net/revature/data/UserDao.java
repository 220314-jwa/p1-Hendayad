package net.revature.data;

import java.sql.SQLException;

import net.revature.mymodle.Users;

public interface UserDao extends GenericDAO<Users> {
	
	public Users getByUsername(String username);
	public void updateStorys(int storyId, int userId) throws SQLException;
	

	
}
