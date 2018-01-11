package com.gamify.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.gamify.impl.UserManager;
import com.gamify.model.User;

@Path("/users")
public class UsersResource {
	// Create new user
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response createUser(
			@FormParam("userID") String userID,
			@FormParam("password") String password,
			@FormParam("email") String email,
			@Context UriInfo uriInfo) {

		UserManager um = UserManager.getInstance();
		um.createUser(userID, password, email);

		UriBuilder builder = uriInfo.getAbsolutePathBuilder();	
		builder.path(userID);
		return Response.created(builder.build()).build();
	}

	// Get all users
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {

		UserManager um = UserManager.getInstance();		
		return um.getUsers();
	}

	// GET a specific user
	@Path("/{userID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUser(@PathParam("userID") String userID) {

		UserManager um = UserManager.getInstance();		
		return um.getUser(userID);
	}

	// Change a specific user
	@Path("/{userID}")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response changeUser(@PathParam("userID") String userID, @FormParam("password") String password, @FormParam("email") String email) {

		UserManager um = UserManager.getInstance();		
		um.changeUser(userID, password, email);

		return Response.ok().entity("").build(); // Send response * TO DO *
	}

	// DELETE a specific user
	@Path("/{userID}")
	@DELETE	
	public Response removeUser(@PathParam("userID") String userID) {

		UserManager um = UserManager.getInstance();		
		um.removeUser(userID);

		return Response.ok().entity("").build(); // Send response * TO DO *
	}

}


