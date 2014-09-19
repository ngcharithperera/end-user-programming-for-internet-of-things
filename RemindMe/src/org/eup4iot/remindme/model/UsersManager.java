package org.eup4iot.remindme.model;

import java.util.ArrayList;

import org.eup4iot.remindme.server.response.RemindMeBaseServerResponse;

public class UsersManager extends RemindMeBaseServerResponse{
	ArrayList<User> UsersList;

	public ArrayList<User> getUsersList() {
		return UsersList;
	}

	public void setUsersList(ArrayList<User> usersList) {
		UsersList = usersList;
	}
}
