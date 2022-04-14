package net.revature.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import net.revature.exception.AlreadySubmittedException;
import net.revature.mymodle.Story;
import net.revature.mymodle.Users;
import net.revature.services.UserService;
import net.revature.services.UserServiceImpl;

public class StorysController {
	private static UserService userServ = new UserServiceImpl();

	// GET to /pets
	public static void getPets(Context ctx) {
		ctx.json(userServ.viewAvailableStorys());
	}
	
	// GET to /pets/{id} where {id} is the ID of the pet
	public static void getPetById(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		Story story = userServ.getStoryById(id);
		if (story != null)
			ctx.json(story);
		else
			ctx.status(HttpCode.NOT_FOUND); // 404 not found
	}
	
	public static void submitStory(Context ctx) {
		int petId = Integer.parseInt(ctx.pathParam("id"));
		Story storyToSubmit = userServ.getStoryById(petId);
		
		Users users = ctx.bodyAsClass(Users.class);
		
		try {
			users = userServ.submitStory(users, storyToSubmit);
			
			ctx.json(users);
		} catch (AlreadySubmittedException e) {
			ctx.status(HttpCode.CONFLICT); // 409 conflict
		} catch (Exception e) {
			ctx.status(HttpCode.BAD_REQUEST); // 400 bad request
		}
	}


}
