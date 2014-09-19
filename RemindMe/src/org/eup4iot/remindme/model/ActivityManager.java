package org.eup4iot.remindme.model;

import java.util.ArrayList;

import org.eup4iot.remindme.server.response.RemindMeBaseServerResponse;

public class ActivityManager extends RemindMeBaseServerResponse{
	
	ArrayList<Activity_> ActivityList;

	public ArrayList<Activity_> getActivityList() {
		return ActivityList;
	}

	public void setActivityList(ArrayList<Activity_> activityList) {
		ActivityList = activityList;
	}
}
