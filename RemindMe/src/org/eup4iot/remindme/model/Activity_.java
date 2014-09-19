/**********************************************
This class store the data that is required to 
built an 'item' in the 'Activity_Activity'
***********************************************/
package org.eup4iot.remindme.model;

public class Activity_ {
	
	String activityID;
	
	String activityName;
	
	String activityDescription;
	
	String activityIconURL;
	
	boolean isSelected;
	
	public String getActivityID() {
		return activityID;
	}
	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityDescription() {
		return activityDescription;
	}
	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}
	public String getActivityIconURL() {
		return activityIconURL;
	}
	public void setActivityIconURL(String activityIconURL) {
		this.activityIconURL = activityIconURL;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}
