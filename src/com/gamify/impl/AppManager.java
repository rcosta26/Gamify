package com.gamify.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import com.gamify.data.AppData;
import com.gamify.data.ErrorData;
import com.gamify.data.UserData;
import com.gamify.interf.InterfaceApp;
import com.gamify.model.App;
import com.gamify.model.Error;
import com.gamify.model.User;

public class AppManager implements InterfaceApp {

	static AppManager am = null;

	public static AppManager getInstance() {
		if (am == null) {
			am = new AppManager();
		}
		return am;
	}

	// Create new app

	@Override
	public Response createApp(String userID, String appName, String type, String description, String userAuth) {

		AppData appData = AppData.getInstance();
		List<App> apps = appData.getAllData();

		boolean exists = false;
		for (int i = 0; i < apps.size(); i++) {
			if (apps.get(i).getAppName().equals(appName)) {
				exists = true;
				break;
			}
		}

		if (exists) {
			ErrorData errorData = ErrorData.getInstance();
			Error error = errorData.getData("13");
			return Response.serverError().status(Integer.parseInt(error.getHttp_status())).type("text/plain")
					.entity(error.getMessage()).build();
		} else {
			int newID = Integer.parseInt(apps.get(apps.size() - 1).getAppID().replace("app", "")) + 1;
			String appID = "app" + Integer.toString(newID);
			App app = new App(appID, userID, appName, type, description);
			appData.insertData(app);		
			// The user is created with success
			return Response.ok().entity(appName + " created!").build();
		}

	}

	// Get all apps

	@Override
	public Object getApps(String userRequested, String userAuth) {

		if (userRequested.equals(userAuth)) {
			AppData appData = AppData.getInstance();
			return appData.getData(userRequested);
		} else {
			// The user is not authorized to see apps from another user
			ErrorData errorData = ErrorData.getInstance();
			return errorData.getData("3");
		}

	}

	// Get specific app

	@Override
	public Object getApp(String userRequested, String appID, String userAuth) {
		if (userRequested.equals(userAuth)) {
			AppData appData = AppData.getInstance();
			return appData.getSpecificData(userRequested, appID);
		} else {
			// The user is not authorized to see apps from another user
			ErrorData errorData = ErrorData.getInstance();
			return errorData.getData("3");
		}

	}

	// Change app

	@Override
	public void changeApp(String appID, String appName, String type, String description, String userAuth) {

		boolean exists = false;
		AppData appData = AppData.getInstance();
		List<App> apps = appData.getAllData();

		for (App app : apps) {
			// Check if app exists
			if (app.getAppID().equals(appID)) {
				exists = true;
				// Check if the user have permission to change the app
				if (app.getUserID().equals(userAuth)) {
					appData.changeData(appID, appName, type, description);
				} else {
					// The user is not authorized to change the apps from another user
					ErrorData errorData = ErrorData.getInstance();
					errorData.getData("3");
				}
			}
		}

		if (exists == false) {
			// There are no app with that ID
			ErrorData errorData = ErrorData.getInstance();
			errorData.getData("7");
		}
	}

	// Remove app

	@Override
	public void removeApp(String appID, String userAuth) {

		boolean exists = false;
		AppData appData = AppData.getInstance();
		List<App> apps = appData.getAllData();

		for (App app : apps) {
			// Check if app exists
			if (app.getAppID().equals(appID)) {
				exists = true;
				// Check if the user have permission to change the app
				if (app.getUserID().equals(userAuth)) {
					appData.removeData(appID);
				} else {
					// The user is not authorized to change the apps from another user
					ErrorData errorData = ErrorData.getInstance();
					errorData.getData("3");
				}
			}
		}

		if (exists == false) {
			// There are no app with that ID
			ErrorData errorData = ErrorData.getInstance();
			errorData.getData("7");
		}

	}
}
