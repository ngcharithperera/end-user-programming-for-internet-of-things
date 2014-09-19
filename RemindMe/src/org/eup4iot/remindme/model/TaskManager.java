package org.eup4iot.remindme.model;

import java.util.ArrayList;

import org.eup4iot.remindme.server.response.RemindMeBaseServerResponse;

public class TaskManager extends RemindMeBaseServerResponse{
	
	ArrayList<Task> TasksList;

	public ArrayList<Task> getTasksList() {
		return TasksList;
	}

	public void setTasksList(ArrayList<Task> tasksList) {
		TasksList = tasksList;
	}
}
