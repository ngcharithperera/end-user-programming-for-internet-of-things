/**********************************************
This class store the data that is required to 
built an 'item' in the 'User_Activity'
***********************************************/
package org.eup4iot.remindme.model;

public class User {
	
	String userID;
	
	String userFullName;
	
	String usershortName;
	
	String usertelephoneNo;
	
	String userEmail;
	
	String userTweeterAccount;
	
	String userFacebookAccount;
	
	String userIconURL;
	
	boolean isSelected;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	public String getUsershortName() {
		return usershortName;
	}
	public void setUsershortName(String usershortName) {
		this.usershortName = usershortName;
	}
	public String getUsertelephoneNo() {
		return usertelephoneNo;
	}
	public void setUsertelephoneNo(String usertelephoneNo) {
		this.usertelephoneNo = usertelephoneNo;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserTweeterAccount() {
		return userTweeterAccount;
	}
	public void setUserTweeterAccount(String userTweeterAccount) {
		this.userTweeterAccount = userTweeterAccount;
	}
	public String getUserFacebookAccount() {
		return userFacebookAccount;
	}
	public void setUserFacebookAccount(String userFacebookAccount) {
		this.userFacebookAccount = userFacebookAccount;
	}
	public String getUserIconURL() {
		return userIconURL;
	}
	public void setUserIconURL(String userIconURL) {
		this.userIconURL = userIconURL;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}	
}
