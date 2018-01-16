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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.gamify.impl.AppManager;
import com.gamify.impl.AuthManager;
import com.gamify.impl.UserManager;
import com.gamify.model.App;
import com.gamify.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Path("/users/{userID}/apps")
public class AppsResource {

	// Create new app
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response createApp(@FormParam("appID") String appID, @FormParam("appName") String appName,
			@FormParam("type") String type, @FormParam("description") String description,
			@FormParam("apiKey") String apiKey, @Context UriInfo uriInfo) {

		AuthManager authManager = AuthManager.getInstance();
		Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
		String userAuth = claims.get("username").toString();

		AppManager am = AppManager.getInstance();

		am.createApp(appID, userAuth, appName, type, description, userAuth);

		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(appID);
		return Response.created(builder.build()).build();
	}

	// Get all apps
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object getApps(@PathParam("userID") String userID, @QueryParam("apiKey") String apiKey) {

		AuthManager authManager = AuthManager.getInstance();
		Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
		String userAuth = claims.get("username").toString();

		AppManager am = AppManager.getInstance();
		return am.getApps(userID, userAuth);
	}

	// GET a specific app
	@Path("/{appID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object getApp(@PathParam("userID") String userID, @PathParam("appID") String appID,
			@QueryParam("apiKey") String apiKey) {

		AuthManager authManager = AuthManager.getInstance();
		Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
		String userAuth = claims.get("username").toString();

		AppManager am = AppManager.getInstance();
		return am.getApp(userID, appID, userAuth);
	}

	// Change a specific app
	@Path("/{appID}")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response changeApp(@PathParam("appID") String appID, @FormParam("appName") String appName,
			@FormParam("type") String type, @FormParam("description") String description,
			@FormParam("apiKey") String apiKey) {

		AuthManager authManager = AuthManager.getInstance();
		Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
		String userAuth = claims.get("username").toString();

		AppManager am = AppManager.getInstance();
		am.changeApp(appID, appName, type, description, userAuth);

		return Response.ok().entity("").build(); // Send response * TO DO *
	}

	// DELETE a specific app
	@Path("/{appID}")
	@DELETE
	public Response removeUser(@PathParam("appID") String appID, @QueryParam("apiKey") String apiKey) {

		AuthManager authManager = AuthManager.getInstance();
		Claims claims = Jwts.parser().setSigningKey(authManager.getKey()).parseClaimsJws(apiKey).getBody();
		String userAuth = claims.get("username").toString();

		AppManager am = AppManager.getInstance();
		am.removeApp(appID, userAuth);

		return Response.ok().entity("").build(); // Send response * TO DO *
	}

}
