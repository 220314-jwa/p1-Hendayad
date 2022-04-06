package net.revature.app;
import net.revature.exception.AlreadySubmittedException;
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
		app.start();
		
		// GET to /pets: get available pets
		app.get("/storys", ctx -> {
			// .json() is an alternative to .result() that
			// sets the data type as JSON, the format that we
			// will be sending/receiving data in!
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
		
		// POST to /auth: log in
		app.post("/auth", ctx -> {
			// if the keys in JSON data don't exactly match a class,
			// we can just use a Map!
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
		
		// PUT to /pets/{id}/adopt where {id} will be a number (a pet ID): adopt pet
		app.put("/storys/{id}/submit", ctx -> {
			// first we can grab the ID from the URI
			// since it is part of the path (URI) it is a path parameter
			// so we use ctx.pathParam and use the name we specified in
			// the path above
			int storyId = Integer.parseInt(ctx.pathParam("id"));
			Story storyToSubmit = userServ.getStoryById(storyId);
			
			// now we need to get the User from the request body
			Users user = ctx.bodyAsClass(Users.class);
			
			try {
				// now we have everything we need to adopt the pet
				user = userServ.submitStory(user, storyToSubmit);
				
				// then we can return the updated user
				ctx.json(user);
			} catch (AlreadySubmittedException e) {
				ctx.status(HttpCode.CONFLICT); // 409 conflict
			}
		});

				
				
	}
}


