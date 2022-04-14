package net.revature.app;
import net.revature.data.DeaFactory;
import net.revature.data.StoryDao;
import net.revature.exception.AlreadySubmittedException;

import java.util.List;
import java.util.Map;
import io.javalin.Javalin;
import io.javalin.http.HttpCode;
import net.revature.exception.IncorrectCredentialsException;
import net.revature.exception.UsernameAlreadyExistsException;
import net.revature.mymodle.Story;
import net.revature.mymodle.Users;
import net.revature.services.UserService;
import net.revature.services.UserServiceImpl;

public class StoryApp {

	private static UserService userServ = new UserServiceImpl();
	
	public static void main (String[] args) {
		Javalin app = Javalin.create();
		app.start(8081);
		
		app.post("/storys", ctx -> {
		
			
			Story story = ctx.bodyAsClass(net.revature.mymodle.Story.class);
		
			StoryDao storyDao = DeaFactory.getStoryDao();
			
			int id = storyDao.create(story);
			
			ctx.result("The story id is: " + id);
		});
	
		app.get("/storys/{id}", ctx -> {
			
			int id = Integer.parseInt(ctx.pathParam("id"));
			
			StoryDao petDAO = DeaFactory.getStoryDao();
			Story resultPet = petDAO.getById(id);
			ctx.json(resultPet);
		});

		app.get("/all_storyss", ctx -> {
			StoryDao storyDao = DeaFactory.getStoryDao();
			List<Story> storys = storyDao.getAll();
			ctx.json(storys);
		});

		app.put("/storys", ctx -> {
			Story story = ctx.bodyAsClass(net.revature.mymodle.Story.class);
			StoryDao storyDao = DeaFactory.getStoryDao();
			storyDao.update(story);
		});

		app.delete("/storys", ctx -> {
			int deleteId = Integer.parseInt(ctx.queryParam("id"));
			
			StoryDao petDAO = DeaFactory.getStoryDao();
			petDAO.deleteById(deleteId);
		});

		
		app.get("/storys", ctx -> {
		
			ctx.json(userServ.viewAvailableStorys());
		});
		
		// POST to /users: register a new user
		app.post("/users", ctx -> {
			// context bodyAsClass method will transform JSON data into
			// a Java object as long as the JSON keys match the Java fields
			Users newUser = ctx.bodyAsClass(Users.class);
			
			try {
				newUser = userServ.register(newUser);
				ctx.json(newUser);
			} catch (UsernameAlreadyExistsException e) {
				ctx.status(HttpCode.CONFLICT); // 409 conflict
			}
		});
		
		
		app.post("/auth", ctx -> {
			
			Map<String,String> credentials = ctx.bodyAsClass(Map.class);
			String username = credentials.get("username");
			String password = credentials.get("password");
			
			try {
				Users user = userServ.logIn(username, password);
				ctx.json(user);
			} catch (IncorrectCredentialsException e) {
				ctx.status(HttpCode.UNAUTHORIZED); // 401 unauthorized
			}
		});
		
	
		app.put("/storys/{id}/submit", ctx -> {
		
			
			int storyId = Integer.parseInt(ctx.pathParam("id"));
			Story storyToSubmit = userServ.getStoryById(storyId);
			
			
			Users user = ctx.bodyAsClass(Users.class);
			
			try {
				
				user = userServ.submitStory(user, storyToSubmit);
				
				
				ctx.json(user);
			} catch (AlreadySubmittedException e) {
				ctx.status(HttpCode.CONFLICT); // 409 conflict
			}
		});

				
				
	}
}


