/**********************************************
This class store the data that is required to 
built an 'item' in the 'Task_Activity'
***********************************************/
package org.eup4iot.remindme.model;

public class Task {
	String taskID;
	
	String taskName;
	
	String taskDescription;
	
	String taskIconURL;
	
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public String getTaskIconURL() {
		return taskIconURL;
	}
	public void setTaskIconURL(String taskIconURL) {
		this.taskIconURL = taskIconURL;
	}

	
}
