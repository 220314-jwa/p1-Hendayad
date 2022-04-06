package net.revature.services;




import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



import net.revature.exception.AlreadySubmittedException;
import net.revature.exception.IncorrectCredentialsException;
import net.revature.exception.UsernameAlreadyExistsException;
import net.revature.mymodle.Story;
import net.revature.mymodle.Users;

public class UserServiceTest {
	
	
	private UserService userServ;
	
	

	

	public void logInSuccessfully() throws IncorrectCredentialsException {
		// setup (arguments, expected result, etc.)
		String username = "sierra";
		String password = "pass";
		
		// call the method we're testing
		Users result = userServ.logIn(username, password);
		
		// assertion
		assertEquals(username, result.getUsername());
	}
	
	@Test
	public void logInWrongUsername() {
		String username = "abc123";
		String password = "1234567890";
		
		assertThrows(IncorrectCredentialsException.class, () -> {
			// put the code that we're expecting to throw the exception
			userServ.logIn(username, password);
		});
	}
	

		
	

	
	@Test
	public void registerSuccessfully() throws UsernameAlreadyExistsException {
		Users newUser = new Users();
		
		Users result = userServ.register(newUser);
		
		// the behavior that i'm looking for is that the
		// method returns the User with their newly generated ID,
		// so i want to make sure the ID was generated (not the default)
		assertNotEquals(0, result.getId());
	}
	@Test
	public void registerUsernameTaken() {
		Users newUser = new Users();
		newUser.setUsername("sierra");
		
		assertThrows(UsernameAlreadyExistsException.class, () -> {
			userServ.register(newUser);
		});
	
		
	}

	@Test
	public void viewStoryssSuccessfully() {
		List<Story> Storys = userServ.viewAvailableStorys();
		
		// i just want to make sure that the pets are returned -
		// i don't need to check that the pets are all available
		// because that filtering happens in the database. i just
		// need to check that the pets list isn't null
		assertNotNull(Storys);
	}
	
	@Test
	public void searchStoryBygenre() {
		String genre = "drama";
		List<Story> storybygenre = userServ.searchStorysBygenre(genre);
	
		boolean onlydramaInList = true;
		for (net.revature.mymodle.Story story: storybygenre) {
			String storygenre = story.getGenre().toLowerCase();
	// if the pet species doesn't contain the species passed in
			if (!storygenre.contains(genre)) {
				// then we'll set the boolean to false
				onlydramaInList = false;
				// and stop the loop because we don't need to continue
				break;
			}
		}
		
		assertTrue(onlydramaInList);
	}
	
	@Test
	public void SubmitStory() throws  AlreadySubmittedException {
		Users testUser = new Users();
		Story testStory = new Story();
		
		Users result = userServ.submitStory(testUser, testStory);
		
		// there are two behaviors i'm looking for:
		// that the user now has the pet in their list of pets,
		// and that the pet in the list has its status updated.
		// to check this, i'm checking that the pet with the Adopted
		// status is in the user's list.
		testStory.setStatus("Approved");
		List<Story> usersstorys = result.getStorys();
		assertTrue(usersstorys.contains(testStory));
}
}
