package org.eup4iot.remindme;

import java.util.ArrayList;

import org.eup4iot.remindme.db.DatabaseManager;
import org.eup4iot.remindme.model.Activity_;
import org.eup4iot.remindme.model.Channel;
import org.eup4iot.remindme.model.Location;
import org.eup4iot.remindme.model.ObjectProperty;
import org.eup4iot.remindme.model.Program;
import org.eup4iot.remindme.model.RecommendedProgram;
import org.eup4iot.remindme.model.SmartObject;
import org.eup4iot.remindme.model.Task;
import org.eup4iot.remindme.model.User;

import android.app.Application;
import android.util.Log;

public class RemindMeApplicationContext extends Application {
	
	private final String TAG = RemindMeApplicationContext.this.getClass().getSimpleName();

    private static RemindMeApplicationContext remindMeApplicationContext;

    private ArrayList<RecommendedProgram> recommendedProgramsList = null;
	
    private ArrayList<SmartObject> smartObjectsList = null;
    
    private ArrayList<Task> tasksList = null;
    
    private ArrayList<ObjectProperty> propertyList = null;
    
    private ArrayList<Location> locationsList = null;
    
    private ArrayList<Activity_> ActivityList = null;
    
    private ArrayList<User> UsersList = null;
    
    private ArrayList<Channel> ChannelsList = null;
    
    private Program program = null;
    
    private ArrayList<Program> myProgramsList = null;

	private boolean isSetPrgData = false;
    
    private RecommendedProgram selectedRecomProgram = null;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		
		remindMeApplicationContext = this;
		DatabaseManager.getInstance(RemindMeApplicationContext.this);
	}
	
	public static void setRemindMeApplicationContext(RemindMeApplicationContext remindMeApplicationContext) {
		RemindMeApplicationContext.remindMeApplicationContext = remindMeApplicationContext;
	}
	
    public static RemindMeApplicationContext getRemindMeApplicationContext() {
        if (remindMeApplicationContext == null) {
        	remindMeApplicationContext = new RemindMeApplicationContext();
        }
		return remindMeApplicationContext;
	}
    
	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}
    
	public ArrayList<RecommendedProgram> getRecommendedProgramsList() {
		return recommendedProgramsList;
	}

	public void setRecommendedProgramsList(
			ArrayList<RecommendedProgram> recommendedProgramsList) {
		this.recommendedProgramsList = recommendedProgramsList;
	}
	
	public ArrayList<SmartObject> getSmartObjectsList() {
		return smartObjectsList;
	}

	public void setSmartObjectsList(ArrayList<SmartObject> smartObjectsList) {
		this.smartObjectsList = smartObjectsList;
	}
	
	public ArrayList<Task> getTasksList() {
		return tasksList;
	}

	public void setTasksList(ArrayList<Task> tasksList) {
		this.tasksList = tasksList;
	}
	
	public ArrayList<ObjectProperty> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(ArrayList<ObjectProperty> propertyList) {
		this.propertyList = propertyList;
	}
	
	public ArrayList<Location> getLocationsList() {
		return locationsList;
	}

	public void setLocationsList(ArrayList<Location> locationsList) {
		this.locationsList = locationsList;
	}
	
	public ArrayList<Activity_> getActivityList() {
		return ActivityList;
	}

	public void setActivityList(ArrayList<Activity_> activityList) {
		ActivityList = activityList;
	}
	
	public ArrayList<User> getUsersList() {
		return UsersList;
	}

	public void setUsersList(ArrayList<User> usersList) {
		UsersList = usersList;
	}
	
	public ArrayList<Channel> getChannelsList() {
		return ChannelsList;
	}

	public void setChannelsList(ArrayList<Channel> channelsList) {
		ChannelsList = channelsList;
	}
	
	public boolean isSetPrgData() {
		return isSetPrgData;
	}

	public void setSetPrgData(boolean isSetPrgData) {
		this.isSetPrgData = isSetPrgData;
	}
	
	public RecommendedProgram getSelectedRecomProgram() {
		return selectedRecomProgram;
	}

	public void setSelectedRecomProgram(RecommendedProgram selectedRecomProgram) {
		this.selectedRecomProgram = selectedRecomProgram;
	}
	
	public ArrayList<Program> getMyProgramsList() {
		return myProgramsList;
	}

	public void setMyProgramsList(ArrayList<Program> myProgramsList) {
		this.myProgramsList = myProgramsList;
	}
}
