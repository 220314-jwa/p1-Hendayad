package net.revature.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;


import net.revature.mymodle.Story;
@TestMethodOrder(OrderAnnotation.class)
public class StoryDaoTest {
	private static StoryDao storyDao=  DeaFactory.getStoryDao();
	
	private static Story teststory = new Story();
	private static Story testNewStory = new Story();

	@BeforeAll
	public static void setUp() {
		
	teststory.setAuthorname("test");

		
		Random rand = new Random();
		testNewStory.setAuthorname("test_" + rand.nextLong());

		// TODO 
		teststory.setId(storyDao.create(teststory));
		
	}

	@AfterAll
	public static void cleanUp() throws SQLException {
	
		storyDao.delete(teststory);
	}
	@Test
	@Disabled
	public void getByAuthorExists() {
		// TODO
	}

	@Test
	@Disabled
	public void getByAuthorDoesNotExist() {
		// TODO
	}

	@Test
public void getByStatus()
{		String testStatus = "pending";
	List<Story> storys = storyDao.getByStatus(testStatus);

		boolean onlyCorrectStatus = true;
	for (Story story : storys) {
		if (!(story.getStatus().equals(testStatus))) {
			onlyCorrectStatus = false;
				break;
			}
		}
		assertTrue(onlyCorrectStatus);	}

	@Test
	@Order(1)
	public void SubmitStoryCorrectly() {
		int id = storyDao.create(testNewStory);
		testNewStory.setId(id);
		assertNotEquals(0, id);
	}

	@Test
	public void getByIdExists() {
		int id = teststory.getId();

	Story story = storyDao.getById(id);

		assertEquals(teststory, story);
	}

	@Test
	public void getByIdDoesNotExist() {
		Story story = storyDao.getById(0);
		assertNull(story);
	}

	@Test
	public void getAll() {
		assertNotNull(storyDao.getAll());
	}

	@Test
	public void updateStoryExists() {
	assertDoesNotThrow(() -> {
			storyDao.update(teststory);
		});
	}

	@Test
	public void updateStoryDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			storyDao.update(new Story());		});
	}

	@Test
	@Order(2)
	public void deleteStoryExists() {
		assertDoesNotThrow(() -> {
			storyDao.delete(testNewStory);
		});
	}

	@Test
	public void deleteStoryDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			storyDao.delete(new Story());
		});
}


}
