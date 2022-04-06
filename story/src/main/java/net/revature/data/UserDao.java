package net.revature.data;

import net.revature.mymodle.Users;

public interface UserDao extends GenericDAO<Users> {
	
	public Users getByUsername(String username);
		
	

	
}
