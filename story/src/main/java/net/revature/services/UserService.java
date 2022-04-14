package net.revature.services;


	import java.util.List;

import net.revature.exception.AlreadySubmittedException;
import net.revature.exception.IncorrectCredentialsException;
import net.revature.exception.UsernameAlreadyExistsException;
import net.revature.mymodle.Story;
import net.revature.mymodle.Users;


	

	 
	public interface UserService {
		
	 public Users logIn(String username, String password) throws IncorrectCredentialsException;
	
	
		 
		 
		public Users register(Users newUser) throws UsernameAlreadyExistsException;
		
		
		 
		public List<Story> viewAvailableStorys();
		
	
		 
		 
		public List<Story> searchStorysBygenre(String genre);
		
		
		
		
		public Users submitStory(Users user, Story storyTosubmit) throws AlreadySubmittedException, Exception;




		 public Story getStoryById(int id);




		 public Users getById(int id);
		
	

		 
	




		 




	




		
		

}
