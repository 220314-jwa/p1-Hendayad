package net.revature.data;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.sql.SQLException;
import net.revature.mymodle.Users;


@TestMethodOrder(OrderAnnotation.class)
public class UserDAOTest {
	private static UserDao userDao = DeaFactory.getUserDAO();
	private static Users testUser = new Users();
	private static Users testNewUser = new Users();




	@BeforeAll
	public static void setUp() {
		
		testUser.setUsername("test");
		
		
		Random rand = new Random();
		testNewUser.setUsername("test_" + rand.nextLong());
		
		
		
		testUser.setId(userDao.create(testUser));
	}
	
	@AfterAll
	public static void cleanUp() throws SQLException {
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
		testNewUser.setId(id);
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
