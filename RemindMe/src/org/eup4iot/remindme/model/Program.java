/**********************************************
This class store the data that is required to 
built an 'item' in the 'MyProgrammes' Tab.

Each Program consists of:
-------------------------

1 program Id
n associated Smart Objects
n associated Properties
1 associated Task
1 associated Calendar
n associated Locations
n associated Activities
n associated Users
n associated Communication Channels

1 associated Validation Class
 
 ***********************************************/

package org.eup4iot.remindme.model;

import java.util.ArrayList;

public class Program {
	
	public enum Status {
		SUCCESSFUL, REQUIRE_INFO, OPTIONAL
	}

	String userNote = null;
	
	String programID = null;
	
	String programIconURL = null;	
	
	String programName = null;
	
	String programDesc = null;

	ArrayList<SmartObject> SmartObjectList = null;
	
	SmartObject smartObject = null;

	ArrayList<ObjectProperty> ObjectPropertyList = null;
	
	Task task = null;
	
	Calender calender = null;
	
	ArrayList<Location> LocationList = null;
	
	ArrayList<Activity_> ActivityList = null;
	
	ArrayList<User> UserList = null;
	
	ArrayList<Channel> ChannelList = null;

	Status objectStatus;
	
	Status taskStatus;
	
	Status propertyStatus;
	
	Status calenderStatus;
	
	Status locationStatus;
	
	Status activityStatus;
	
	Status userStatus;
	
	Status channelStatus;

	public String getUserNote() {
		return userNote;
	}

	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}

	public String getProgramID() {
		return programID;
	}

	public void setProgramID(String programID) {
		this.programID = programID;
	}

	public String getProgramIconURL() {
		return programIconURL;
	}

	public void setProgramIconURL(String programIconURL) {
		this.programIconURL = programIconURL;
	}

	public ArrayList<SmartObject> getSmartObjectList() {
		return SmartObjectList;
	}

	public void setSmartObjectList(ArrayList<SmartObject> smartObjectList) {
		SmartObjectList = smartObjectList;
	}

	public ArrayList<ObjectProperty> getObjectPropertyList() {
		return ObjectPropertyList;
	}

	public void setObjectPropertyList(ArrayList<ObjectProperty> objectPropertyList) {
		ObjectPropertyList = objectPropertyList;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Calender getCalender() {
		if(calender == null) {
			calender = new Calender();
		}
		return calender;
	}

	public void setCalender(Calender calender) {
		this.calender = calender;
	}

	public ArrayList<Location> getLocationList() {
		return LocationList;
	}

	public void setLocationList(ArrayList<Location> locationList) {
		LocationList = locationList;
	}

	public ArrayList<Activity_> getActivityList() {
		return ActivityList;
	}

	public void setActivityList(ArrayList<Activity_> activityList) {
		ActivityList = activityList;
	}

	public ArrayList<User> getUserList() {
		return UserList;
	}

	public void setUserList(ArrayList<User> userList) {
		UserList = userList;
	}

	public ArrayList<Channel> getChannelList() {
		return ChannelList;
	}

	public void setChannelList(ArrayList<Channel> channelList) {
		ChannelList = channelList;
	}

	public Status getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(Status objectStatus) {
		this.objectStatus = objectStatus;
	}

	public Status getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Status taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Status getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(Status propertyStatus) {
		this.propertyStatus = propertyStatus;
	}

	public Status getCalenderStatus() {
		return calenderStatus;
	}

	public void setCalenderStatus(Status calenderStatus) {
		this.calenderStatus = calenderStatus;
	}

	public Status getLocationStatus() {
		return locationStatus;
	}

	public void setLocationStatus(Status locationStatus) {
		this.locationStatus = locationStatus;
	}

	public Status getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Status activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Status getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Status userStatus) {
		this.userStatus = userStatus;
	}

	public Status getChannelStatus() {
		return channelStatus;
	}

	public void setChannelStatus(Status channelStatus) {
		this.channelStatus = channelStatus;
	}
	
	public SmartObject getSmartObject() {
		return smartObject;
	}

	public void setSmartObject(SmartObject smartObject) {
		this.smartObject = smartObject;
	}
	
	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getProgramDesc() {
		return programDesc;
	}

	public void setProgramDesc(String programDesc) {
		this.programDesc = programDesc;
	}

}
