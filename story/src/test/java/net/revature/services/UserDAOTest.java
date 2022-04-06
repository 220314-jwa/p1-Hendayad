package net.revature.services;



import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import net.revature.data.DeaFactory;
import net.revature.data.UserDao;
import net.revature.mymodle.Users;




public class UserDAOTest {
	private static UserDao userDao = DeaFactory.getUserDAO();
	private static Users testUser = new Users();
	private static Users testNewUser = new Users();
	
	@BeforeAll
	public static void setUp() {
		// this is the base test user used for most tests
		testUser.setUsername("test");
		
		// this is the user to test create and delete
		Random rand = new Random();
		testNewUser.setUsername("test_" + rand.nextLong());
		
		// TODO create user in DB with username "test"
		// and set the user's ID to the returned value
		testUser.setId(userDao.create(testUser));
	}
	
	@AfterAll
	public static void cleanUp() {
		// TODO remove users in DB with username containing "test"
		userDao.delete(testUser);
	}
	
	@Test
	public void getByUsernameExists() {
		Users user = userDao.getByUsername("test");
		assertEquals(testUser, user);
	}
	
	@Test
	public void getByUsernameDoesNotExist() {
		Users user = userDao.getByUsername("qwertyuiop");
		assertNull(user);
	}
	
	@Test
	@Order(1)
	public void createUserSuccessfully() {
		int id = userDao.create(testNewUser);
		
		assertNotEquals(0, id);
	}
	
	@Test
	public void createUserDuplicateUsername() {
		int id = userDao.create(testUser);
		
		assertEquals(0, id);
	}
	
	@Test
	public void getByIdExists() {
		int id = testUser.getId();
		
		Users user = userDao.getById(id);
		
		assertEquals(testUser, user);
	}
	
	@Test
	public void getByIdDoesNotExist() {
		Users user = userDao.getById(0);
		assertNull(user);
	}
	
	@Test
	public void getAll() {
		assertNotNull(userDao.getAll());
	}
	
	@Test
	public void updateUserExists() {
		assertDoesNotThrow(() -> {
			userDao.update(testUser);
		});
	}
	
	@Test
	public void updateUserDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			userDao.update(new Users());
		});
	}
	
	@Test
	@Order(2)
	public void deleteUserExists() {
		assertDoesNotThrow(() -> {
			userDao.delete(testNewUser);
		});
	}
	
	@Test
	public void deleteUserDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			userDao.delete(new Users());
		});
	}


}
