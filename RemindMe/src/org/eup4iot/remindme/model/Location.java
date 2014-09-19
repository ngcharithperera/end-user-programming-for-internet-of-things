/**********************************************
This class store the data that is required to 
built an 'item' in the 'Location_Activity'
***********************************************/

package org.eup4iot.remindme.model;

public class Location {
	
	String locationID;
	
	String locationName;
	
	String locationDescription;
	
	String locationIconURL;
	
	String locationCategory;
	
	boolean isSelected;
	
	public String getLocationID() {
		return locationID;
	}
	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLocationDescription() {
		return locationDescription;
	}
	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}
	public String getLocationIconURL() {
		return locationIconURL;
	}
	public void setLocationIconURL(String locationIconURL) {
		this.locationIconURL = locationIconURL;
	}
	public String getLocationCategory() {
		return locationCategory;
	}
	public void setLocationCategory(String locationCategory) {
		this.locationCategory = locationCategory;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}
