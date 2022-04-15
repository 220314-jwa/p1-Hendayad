//set up mockito,tell the junit that i am using mockito ,static assertion, extend mockito
package net.revature.services;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import net.revature.data.StoryDao;
import net.revature.data.UserDao;
import net.revature.exception.IncorrectCredentialsException;
import net.revature.exception.UsernameAlreadyExistsException;
import net.revature.mymodle.Story;
import net.revature.mymodle.Users;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock 
	//mock 2 dataaccess object so when we test we donot actually hit database
	//so if test fail i know it is from service.
	private UserDao userDao;
	@Mock
	private StoryDao storyDao;
	@InjectMocks 
	//where i am supposted to put this mockes ,injected in userservice implementation
	private UserService userServ = new UserServiceImpl();
	@Test
	public void exampleTest() {
		assertTrue(true);
	}
	@Test
	//test the login 
	public void logInSuccessfully() throws IncorrectCredentialsException {
		//we expecting the user to login in with this username and pass
		String username = "snicholes";
		String password = "pass";
		Users mockUser = new Users();
		mockUser.setUsername(username);
		mockUser.setPassword(password);
		when(userDao.getByUsername(username)).thenReturn(mockUser);

		
		Users result = userServ.logIn(username, password);//call the method we are testing
		
		// assertion to verify that throws to prevent fail test
		assertEquals(username, result.getUsername());
	}
	
	@Test
	//test login when user login with incrorrect username
	public void logInWrongUsername() {
		String username = "abc123";
		String password = "1234567890";
		//we see what service supposte to do when user login with incorrect
		when(userDao.getByUsername(username)).thenReturn(null);
		assertThrows(IncorrectCredentialsException.class, () -> {
			// put the code that we're expecting to throw the exception
			userServ.logIn(username, password);
		});
	}
	@Test
	//test logn when user login with wrong pass
	public void logInWrongPassword() {
		String username = "snicholes";
		String password = "1234567890";
		
		Users mockUser = new Users();
		mockUser.setUsername(username);
		mockUser.setPassword("fake_password");
		when(userDao.getByUsername(username)).thenReturn(mockUser);
		
		assertThrows(IncorrectCredentialsException.class, () -> {
			// put the code that we're expecting to throw the exception
			userServ.logIn(username, password);
		});
	}

		
	

	
	@Test
	
	public void registerSuccessfully() throws UsernameAlreadyExistsException {
		Users newUser = new Users();
		when(userDao.create(newUser)).thenReturn(1);
		Users result = userServ.register(newUser);
		
		// the behavior that i'm looking for is that the
		// method returns the User with their newly generated ID,
		// so i want to make sure the ID was generated (not the default)
		assertNotEquals(0, result.getId());
	}
	@Test
	
	public void registerUsernameTaken() {
		Users newUser = new Users();
		newUser.setUsername("snicholes");

		when(userDao.create(newUser)).thenReturn(0);

		assertThrows(UsernameAlreadyExistsException.class, () -> {
			userServ.register(newUser);
		});
	
		
	}

	@Test
	//didnot mocked this
	public void viewStorysSuccessfully() {
		when(storyDao.getByStatus("pending")).thenReturn(Collections.emptyList());
		List<Story> Storys = userServ.viewAvailableStorys();
		
		assertNotNull(Storys);
	}
	
	@Test
	
	public void searchStoryBygenre() {
		String genre = "drama";
		List<Story> mockStorys = new LinkedList<>();
		Story drama = new Story();
		drama.setGenre(genre);
		Story notDrama = new Story();
		notDrama.setGenre("real");
		mockStorys.add(drama);
		mockStorys.add(notDrama);
		
		when(storyDao.getAll()).thenReturn(mockStorys);

		List<Story> storybygenre = userServ.searchStorysBygenre(genre);
	
		boolean onlydramaInList = true;
		for (net.revature.mymodle.Story story: storybygenre) {
			String storygenre = story.getGenre().toLowerCase();
	
			if (!storygenre.contains(genre)) {
				
				onlydramaInList = false;
				
				break;
			}
		}
		
		assertTrue(onlydramaInList);
	}
	
	@Test
	public void SubmitStoryCorrectly() throws  Exception {
		Users testUser = new Users();
		Story testStory = new Story();
		// petDao.getById: return testPet
		when(storyDao.getById(testStory.getId())).thenReturn(testStory);
				
		when(userDao.getById(testUser.getId())).thenReturn(testUser);
		doNothing().when(storyDao).update(any(Story.class));
			
		doNothing().when(userDao).update(any(Users.class));
				
		doNothing().when(userDao).updateStorys(testStory.getId(), testUser.getId());

		Users result = userServ.submitStory(testUser, testStory);
		
		
		testStory.setStatus("Approved");
		List<Story> usersstorys = result.getStorys();
		assertTrue(usersstorys.contains(testStory));
		verify(storyDao, times(1)).update(any(Story.class));
		

}	
	@Test
	public void submitStoryAlreadySubmited() throws SQLException {
		Users testUser = new Users();
		Story testPet = new Story();
		testPet.setStatus("Submitted");
		
		
		when(storyDao.getById(testPet.getId())).thenReturn(testPet);
		
		assertThrows(Exception.class, () -> {
			userServ.submitStory(testUser, testPet);
		});
		
		verify(storyDao, never()).update(any(Story.class));
		verify(userDao, never()).update(any(Users.class));
		verify(userDao, never()).updateStorys(testPet.getId(), testUser.getId());
	}

}
