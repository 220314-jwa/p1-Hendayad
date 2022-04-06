package net.revature.services;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import net.revature.data.StoryDao;
import net.revature.mymodle.Story;

public class StoryDaoTest {
	private static StoryDao storyDao; // TODO instantiate
	private static Story teststory = new Story();
	private static Story testNewStory = new Story();

	@BeforeAll
	public static void setUp() {
		// this is the base test story used for most tests
	teststory.setAuthorname("test");

		// this is the pet to test create and delete
		Random rand = new Random();
		testNewStory.setAuthorname("test_" + rand.nextLong());

		// TODO create pet in DB with name "test"
		// and set the pet's ID to the returned value
	}

	@AfterAll
	public static void cleanUp() {
		storyDao.delete(teststory);
		// TODO remove story's in DB with name containing "test"
	}
	@Test
	public void getByAuthorExists() {
		// TODO
	}

	@Test
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
	public void updateUserExists() {
	assertDoesNotThrow(() -> {
			storyDao.update(teststory);
		});
	}

	@Test
	public void updateUserDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			storyDao.update(new Story());		});
	}

	@Test
	@Order(2)
	public void deleteUserExists() {
		assertDoesNotThrow(() -> {
			storyDao.delete(testNewStory);
		});
	}

	@Test
	public void deleteUserDoesNotExist() {
		assertThrows(SQLException.class, () -> {
			storyDao.delete(new Story());
		});
}


}
