package net.revature.data;

import java.util.List;


import net.revature.mymodle.Story;
import net.revature.mymodle.Users;

public interface StoryDao extends GenericDAO<Story> {

	public List<Story> getByOwner(Users owner);
	public List<Story> getByStatus(String Status);
	

}
