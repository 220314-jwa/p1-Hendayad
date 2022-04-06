package net.revature.services;
import java.util.LinkedList;
import java.util.List;

import net.revature.data.DeaFactory;
import net.revature.data.StoryDao;
import net.revature.data.UserDao;
import net.revature.exception.AlreadySubmittedException;
import net.revature.exception.IncorrectCredentialsException;
import net.revature.exception.UsernameAlreadyExistsException;
import net.revature.mymodle.Story;
import net.revature.mymodle.Users;

public class UserServiceImpl implements UserService {
	private UserDao userDao = DeaFactory.getUserDAO();
	private StoryDao storyDao = DeaFactory.getStoryDao();

	@Override
	public Users logIn(String username, String password) throws IncorrectCredentialsException {
		Users user = userDao.getByUsername(username);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		} else {
			throw new IncorrectCredentialsException();
		}

	}

	@Override
	public Users register(Users newUser) throws UsernameAlreadyExistsException {
		int id = userDao.create(newUser);
		if (id != 0) {
			newUser.setId(id);
			return newUser;
		} // else
		return null;

	}

	@Override
	public List<Story> viewAvailableStorys() {
		return storyDao.getByStatus("Pending");
	}

	@Override
	
	public List<Story> searchStorysBygenre(String genre) {
		List<Story> storys = storyDao.getAll();
		List<Story> storysWithGenre = new LinkedList<>();
		for (int i=0; i<storys.size(); i++) {
			// if the pet's species is equal to the species passed in
			// (toLowerCase to allow it to be case-insensitive)
			if (storys.get(i).getGenre().toLowerCase().equals(genre.toLowerCase())) {
				storysWithGenre.add(storys.get(i));
			}
		}
		
		return storysWithGenre;


	}

	@Override
	public Users submitStory(Users users, Story storyToSubmit) throws AlreadySubmittedException {
		
storyToSubmit = storyDao.getById(storyToSubmit.getId());
		
		// make sure the pet is not already adopted
		if (storyToSubmit.getStatus().equals("Approved")) {
			throw new AlreadySubmittedException();
		} else {
			// check user to make sure account is valid
			users = userDao.getById(users.getId());
			if (users != null) {
				// proceed with adopting
				storyToSubmit.setStatus("Approved");
				users.getStorys().add(storyToSubmit);
				storyDao.update(storyToSubmit);
				userDao.update(users);
			} 
			return users;
	}
	}
	
	public Story getStoryById(int id) {
		
			return storyDao.getById(id);

		
	}
	
	

}
